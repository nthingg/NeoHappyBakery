/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.happybakery.dao;

import com.happybakery.dto.Blog;
import com.happybakery.dto.BlogCate;
import com.happybakery.dto.CommentBlog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.happybakery.mylib.DBUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author thinh
 */
public class BlogDAO {

    public static boolean removeBlog(int blogId, int status) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE Blog\n"
                    + "set BlogStatus = ?\n"
                    + "where BlogID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, blogId);
            result = pst.executeUpdate();
        }

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNewestBlogID() throws Exception {
        Connection cn = DBUtils.makeConnection();
        int RecipeID = 0;
        if (cn != null) {
            String sql = "select top 1 BlogID\n"
                    + "from Blog\n"
                    + "where BlogStatus != 0\n"
                    + "order by BlogID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    RecipeID = table.getInt("BlogID");
                }
            }
        }
        return RecipeID;
    }

    public static boolean updateImgPath(int id, String imgPath) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "update Blog\n"
                    + "set BlogImg = ?\n"
                    + "where BlogID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, imgPath);
            pst.setInt(2, id);
            result = pst.executeUpdate();
        }

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean updateBlog(int blogId, String note, String title, String description) {
        boolean result = false;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                //get acc by email

                if (!note.isEmpty()) {
                    String sql = "update Blog\n"
                            + "set BlogNote = ?\n"
                            + "where BlogID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, note);
                    pst.setInt(2, blogId);
                    pst.executeUpdate();
                }

                if (!title.isEmpty()) {
                    String sql = "update Blog\n"
                            + "set BlogTitle = ?\n"
                            + "where BlogID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, title);
                    pst.setInt(2, blogId);
                    pst.executeUpdate();
                }

                if (!description.isEmpty()) {
                    String sql = "update Blog\n"
                            + "set BlogDescription = ?\n"
                            + "where BlogID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, description);
                    pst.setInt(2, blogId);
                    pst.executeUpdate();
                }

                cn.commit();
                cn.setAutoCommit(true);
                return true;
            }
        } catch (Exception e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean insertBlog(int userId, String title, String note, String description, int categoryId) throws Exception {
        int result = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "insert into Blog (UserID, BlogAddedDay, BlogTitle, BlogNote, BlogDescription, BlogCategoryID, BlogStatus) "
                    + "values (?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setString(2, dt);
            pst.setString(3, title);
            pst.setString(4, note);
            pst.setString(5, description);
            pst.setInt(6, categoryId);
            result = pst.executeUpdate();
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<BlogCate> getAllBlogCates() throws Exception {
        ArrayList<BlogCate> list = new ArrayList<>();
        //step 1: tạo connection 
        Connection cn = com.happybakery.mylib.DBUtils.makeConnection();
        if (cn != null) {
            //Step 2: viết sql 
            String sql = "select BlogCategoryID, BlogCategoryName\n"
                    + "from BlogCategory";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            //Step 3: xử lý kết quả step2 
            if (rs != null) {
                while (rs.next()) {
                    int CategoryID = rs.getInt("BlogCategoryID");
                    String CategoryName = rs.getString("BlogCategoryName");
                    BlogCate category = new BlogCate(CategoryID, CategoryName);
                    list.add(category);
                }// hết while 
            }//hết if
            cn.close();
        }
        return list;
    }

    public static ArrayList<Blog> get3NewestBlog() throws Exception {
        ArrayList<Blog> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            cn.setAutoCommit(false);

            String sql = "select top 3 BlogID, Users.UserID as 'UserID', Users.UserName as 'UserName', BlogTitle, BlogImg, BlogAddedDay, BlogRatingPoint,\n"
                    + "BlogNote, BlogDescription, BlogCategory.BlogCategoryName as 'BlogCategory'\n"
                    + "from Users \n"
                    + "join Blog on Users.UserID = Blog.UserID\n"
                    + "join BlogCategory on Blog.BlogCategoryID = BlogCategory.BlogCategoryID\n"
                    + "where BlogStatus != 0\n"
                    + "order by BlogID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int BlogID = table.getInt("BlogID");
                    int UserID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String BlogTitle = table.getString("BlogTitle");
                    String BlogDescription = table.getString("BlogDescription");
                    String BlogAddedDay = table.getString("BlogAddedDay");
                    int BlogRatingPoint = table.getInt("BlogRatingPoint");
                    String BlogImg = table.getString("BlogImg");
                    String BlogCategory = table.getString("BlogCategory");
                    String BlogNote = table.getString("BlogNote");
                    Blog blog = new Blog(BlogID, UserID, UserName, BlogImg, BlogAddedDay, BlogTitle, BlogNote, BlogDescription, BlogCategory, 0, 0);
                    list.add(blog);
                }
            }

            for (Blog blog : list) {
                sql = "select RatedBlogID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                        + "from Blog\n"
                        + "join CommentBlog on CommentBlog.RatedBlogID = Blog.BlogID\n"
                        + "where RatedBlogID = ?\n"
                        + "group by RatedBlogID";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, blog.getBlogId());
                table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int AvgRatingPoint = table.getInt("AvgRatingPoint");
                        int NumberOfRating = table.getInt("NumberOfRating");
                        blog.setBlogRatingPoint(AvgRatingPoint);
                        blog.setBlogNumberOfRating(NumberOfRating);
                        sql = "update Blog\n"
                                + "set BlogRatingPoint = ?\n"
                                + "where BlogID = ?";
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, AvgRatingPoint);
                        pst.setInt(2, blog.getBlogId());
                        pst.executeUpdate();
                    }
                }
            }
            cn.setAutoCommit(true);
            cn.close();
        }
        return list;
    }

    public static Blog getBlogById(int blogId) throws Exception {
        Blog blog = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            cn.setAutoCommit(false);

            String sql = "select Users.UserID as 'UserID', Users.UserName as 'UserName', BlogTitle, BlogImg, BlogAddedDay, BlogRatingPoint,\n"
                    + "BlogNote, BlogDescription, BlogCategory.BlogCategoryName as 'BlogCategory'\n"
                    + " from Users \n"
                    + "join Blog on Users.UserID = Blog.UserID\n"
                    + "join BlogCategory on Blog.BlogCategoryID = BlogCategory.BlogCategoryID\n"
                    + "where Blog.BlogID = ? and Blog.BlogStatus != 0";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, blogId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int UserID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String BlogTitle = table.getString("BlogTitle");
                    String BlogDescription = table.getString("BlogDescription");
                    String BlogAddedDay = table.getString("BlogAddedDay");
                    int BlogRatingPoint = table.getInt("BlogRatingPoint");
                    String BlogImg = table.getString("BlogImg");
                    String BlogCategory = table.getString("BlogCategory");
                    String BlogNote = table.getString("BlogNote");
                    blog = new Blog(blogId, UserID, UserName, BlogImg, BlogAddedDay, BlogTitle, BlogNote, BlogDescription, BlogCategory, 0, 0);
                }
            }

            sql = "select RatedBlogID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                    + "from Blog\n"
                    + "join CommentBlog on CommentBlog.RatedBlogID = Blog.BlogID\n"
                    + "where RatedBlogID = ?\n"
                    + "group by RatedBlogID";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, blog.getBlogId());
            table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int AvgRatingPoint = table.getInt("AvgRatingPoint");
                    int NumberOfRating = table.getInt("NumberOfRating");
                    blog.setBlogRatingPoint(AvgRatingPoint);
                    blog.setBlogNumberOfRating(NumberOfRating);
                    sql = "update Blog\n"
                            + "set BlogRatingPoint = ?\n"
                            + "where BlogID = ?";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, AvgRatingPoint);
                    pst.setInt(2, blog.getBlogId());
                    pst.executeUpdate();
                }
            }
            cn.setAutoCommit(true);
            cn.close();
        }
        return blog;
    }

    public static ArrayList<Blog> getBlogByUserId(int userId) throws Exception {
        ArrayList<Blog> list = new ArrayList<>();
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                String sql = "select Blog.BlogID as 'BlogID', Users.UserName as 'UserName', BlogTitle, BlogImg, BlogAddedDay, BlogRatingPoint,\n"
                        + "BlogNote, BlogDescription, BlogCategory.BlogCategoryName as 'BlogCategory'\n"
                        + "from Users \n"
                        + "join Blog on Users.UserID = Blog.UserID\n"
                        + "join BlogCategory on Blog.BlogCategoryID = BlogCategory.BlogCategoryID\n"
                        + "where Users.UserID = ? and Blog.BlogStatus != 0";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int BlogID = table.getInt("BlogID");
                        String UserName = table.getString("UserName");
                        String BlogTitle = table.getString("BlogTitle");
                        String BlogDescription = table.getString("BlogDescription");
                        String BlogAddedDay = table.getString("BlogAddedDay");
                        int BlogRatingPoint = table.getInt("BlogRatingPoint");
                        String BlogImg = table.getString("BlogImg");
                        String BlogCategory = table.getString("BlogCategory");
                        String BlogNote = table.getString("BlogNote");
                        Blog blog = new Blog(BlogID, userId, UserName, BlogImg, BlogAddedDay, BlogTitle, BlogNote, BlogDescription, BlogCategory, 0, 0);
                        list.add(blog);
                    }
                }

                for (Blog blog : list) {
                    sql = "select RatedBlogID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                            + "from Blog\n"
                            + "join CommentBlog on CommentBlog.RatedBlogID = Blog.BlogID\n"
                            + "where RatedBlogID = ?\n"
                            + "group by RatedBlogID";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, blog.getBlogId());
                    table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int AvgRatingPoint = table.getInt("AvgRatingPoint");
                            int NumberOfRating = table.getInt("NumberOfRating");
                            blog.setBlogRatingPoint(AvgRatingPoint);
                            blog.setBlogNumberOfRating(NumberOfRating);
                            sql = "update Blog\n"
                                    + "set BlogRatingPoint = ?\n"
                                    + "where BlogID = ?";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, AvgRatingPoint);
                            pst.setInt(2, blog.getBlogId());
                            pst.executeUpdate();
                        }
                    }
                }
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (Exception e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<Blog> getAllBlogs() throws Exception {
        ArrayList<Blog> list = new ArrayList<>();
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                String sql = "select BlogID, Users.UserID as 'UserID', Users.UserName as 'UserName', BlogTitle, BlogImg, BlogAddedDay, BlogRatingPoint,\n"
                        + "BlogNote, BlogDescription, BlogCategory.BlogCategoryName as 'BlogCategory'\n"
                        + "from Users \n"
                        + "join Blog on Users.UserID = Blog.UserID\n"
                        + "join BlogCategory on Blog.BlogCategoryID = BlogCategory.BlogCategoryID\n"
                        + "where Blog.BlogStatus != 0";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int BlogID = table.getInt("BlogID");
                        int UserID = table.getInt("UserID");
                        String UserName = table.getString("UserName");
                        String BlogTitle = table.getString("BlogTitle");
                        String BlogDescription = table.getString("BlogDescription");
                        String BlogAddedDay = table.getString("BlogAddedDay");
                        int BlogRatingPoint = table.getInt("BlogRatingPoint");
                        String BlogImg = table.getString("BlogImg");
                        String BlogCategory = table.getString("BlogCategory");
                        String BlogNote = table.getString("BlogNote");
                        Blog blog = new Blog(BlogID, UserID, UserName, BlogImg, BlogAddedDay, BlogTitle, BlogNote, BlogDescription, BlogCategory, 0, 0);
                        list.add(blog);
                    }
                }

                for (Blog blog : list) {
                    sql = "select RatedBlogID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                            + "from Blog\n"
                            + "join CommentBlog on CommentBlog.RatedBlogID = Blog.BlogID\n"
                            + "where RatedBlogID = ?\n"
                            + "group by RatedBlogID";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, blog.getBlogId());
                    table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int AvgRatingPoint = table.getInt("AvgRatingPoint");
                            int NumberOfRating = table.getInt("NumberOfRating");
                            blog.setBlogRatingPoint(AvgRatingPoint);
                            blog.setBlogNumberOfRating(NumberOfRating);
                            sql = "update Blog\n"
                                    + "set BlogRatingPoint = ?\n"
                                    + "where BlogID = ?";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, AvgRatingPoint);
                            pst.setInt(2, blog.getBlogId());
                            pst.executeUpdate();
                        }
                    }
                }
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (Exception e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public static ArrayList<Blog> getReportedBlogs() throws Exception {
        ArrayList<Blog> list = new ArrayList<>();
        Connection cn = null;
        boolean result = false;
        String sql = "";
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                sql = "select BlogID, Users.UserID as 'UserID', Users.UserName as 'UserName', BlogTitle, BlogImg, BlogAddedDay, BlogRatingPoint,\n"
                        + "BlogNote, BlogDescription, BlogCategory.BlogCategoryName as 'BlogCategory'\n"
                        + "from Users \n"
                        + "join Blog on Users.UserID = Blog.UserID\n"
                        + "join BlogCategory on Blog.BlogCategoryID = BlogCategory.BlogCategoryID\n"
                        + "where Blog.BlogStatus = 2";

                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int BlogID = table.getInt("BlogID");
                        int UserID = table.getInt("UserID");
                        String UserName = table.getString("UserName");
                        String BlogTitle = table.getString("BlogTitle");
                        String BlogDescription = table.getString("BlogDescription");
                        String BlogAddedDay = table.getString("BlogAddedDay");
                        int BlogRatingPoint = table.getInt("BlogRatingPoint");
                        String BlogImg = table.getString("BlogImg");
                        String BlogCategory = table.getString("BlogCategory");
                        String BlogNote = table.getString("BlogNote");
                        Blog blog = new Blog(BlogID, UserID, UserName, BlogImg, BlogAddedDay, BlogTitle, BlogNote, BlogDescription, BlogCategory, 0, 0);
                        list.add(blog);
                    }
                }

                for (Blog blog : list) {
                    sql = "select RatedBlogID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                            + "from Blog\n"
                            + "join CommentBlog on CommentBlog.RatedBlogID = Blog.BlogID\n"
                            + "where RatedBlogID = ?\n"
                            + "group by RatedBlogID";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, blog.getBlogId());
                    table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int AvgRatingPoint = table.getInt("AvgRatingPoint");
                            int NumberOfRating = table.getInt("NumberOfRating");
                            blog.setBlogRatingPoint(AvgRatingPoint);
                            blog.setBlogNumberOfRating(NumberOfRating);
                            sql = "update Blog\n"
                                    + "set BlogRatingPoint = ?\n"
                                    + "where BlogID = ?";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, AvgRatingPoint);
                            pst.setInt(2, blog.getBlogId());
                            pst.executeUpdate();
                        }
                    }
                }
                cn.setAutoCommit(true);
                cn.close();
            }
        } catch (Exception e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public static void changeStatusBlog(int id, String status){
        Connection cn = null;
        String sql = "";
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                switch(status){
                    case "report":
                        sql="update Blog set BlogStatus = 2 where BlogID = ?";
                        break;
                    case "delete":
                        sql="update Blog set BlogStatus = 0 where BlogID = ?";
                        break;
                }
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
