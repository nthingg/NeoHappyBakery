/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.happybakery.dao;

import com.happybakery.dto.Category;
import com.happybakery.dto.Country;
import com.happybakery.dto.Ingredient;
import com.happybakery.dto.Order;
import com.happybakery.dto.Recipe;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.happybakery.mylib.DBUtils;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author thinh
 */
public class RecipeDAO {

    public static ArrayList<Recipe> filterRecipe(int storeId, String textSearch, String searchby) throws Exception {
        ArrayList<Recipe> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            
            cn.setAutoCommit(false);
            
            String sql = "select RecipeID, Users.UserID as 'UserID', Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                    + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                    + "from Users \n"
                    + "join Recipe on Users.UserID = Recipe.UserID\n"
                    + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                    + "join Country on Country.CountryID = Recipe.CountryID\n";
            if (searchby.equalsIgnoreCase("byCate")) {
                sql += "where Users.UserID = ? and Recipe.RecipeStatus != 0 and Category.CategoryName like ?";
            } else if (searchby.equalsIgnoreCase("byName")) {
                sql += "where Users.UserID = ? and Recipe.RecipeStatus != 0 and Recipe.RecipeName like ?";
            } else if (searchby.equalsIgnoreCase("byCount")) {
                sql += "where Users.UserID = ? and Recipe.RecipeStatus != 0 and Country.CountryName like ?";
            }
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, storeId);
            pst.setString(2, "%" + textSearch + "%");
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int RecipeID = table.getInt("RecipeID");
                    int UserID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String RecipeName = table.getString("RecipeName");
                    String RecipeDescription = table.getString("RecipeDescription");
                    String RecipeAddedDay = table.getString("RecipeAddedDay");
                    int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                    int PrepTime = table.getInt("PrepTime");
                    int CookTime = table.getInt("CookTime");
                    int Yields = table.getInt("Yields");
                    String RecipeImg = table.getString("RecipeImg");
                    String CategoryName = table.getString("CategoryName");
                    String CountryName = table.getString("CountryName");
                    Recipe recipe = new Recipe(RecipeID, UserID, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                    list.add(recipe);
                }
            }
            
            for (Recipe recipe : list) {
                sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                        + "from Recipe\n"
                        + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                        + "where RatedRecipeID = ?\n"
                        + "group by RatedRecipeID";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, recipe.getRecipeId());
                table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int AvgRatingPoint = table.getInt("AvgRatingPoint");
                        int NumberOfRating = table.getInt("NumberOfRating");
                        recipe.setRecipeRating(AvgRatingPoint);
                        recipe.setNumberOfRatings(NumberOfRating);
                        sql = "update Recipe\n"
                                + "set RecipeRatingPoint = ?\n"
                                + "where RecipeID = ?";
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, AvgRatingPoint);
                        pst.setInt(2, recipe.getRecipeId());
                        pst.executeUpdate();
                    }
                }
            }
            cn.setAutoCommit(true);
            cn.close();
        }
        return list;
    }

    public static boolean removeRecipe(int recipeId, int status) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE Recipe\n"
                    + "set RecipeStatus = ?\n"
                    + "where RecipeID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, recipeId);
            result = pst.executeUpdate();
        }

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean updateRecipe(int recipeId, String preptime, String cooktime, String yields, String description, ArrayList<String> listStep) {
        boolean result = false;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);


                if (!preptime.isEmpty()) {
                    int prep = Integer.parseInt(preptime);
                    String sql = "update Recipe\n"
                            + "set PrepTime = ?\n"
                            + "where RecipeID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, prep);
                    pst.setInt(2, recipeId);
                    pst.executeUpdate();
                }

                if (!cooktime.isEmpty()) {
                    int cook = Integer.parseInt(cooktime);
                    String sql = "update Recipe\n"
                            + "set CookTime = ?\n"
                            + "where RecipeID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, cook);
                    pst.setInt(2, recipeId);
                    pst.executeUpdate();
                }

                if (!yields.isEmpty()) {
                    int y = Integer.parseInt(yields);
                    String sql = "update Recipe\n"
                            + "set Yields = ?\n"
                            + "where RecipeID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, y);
                    pst.setInt(2, recipeId);
                    pst.executeUpdate();
                }

                if (!description.isEmpty()) {
                    String sql = "update Recipe\n"
                            + "set RecipeDescription = ?\n"
                            + "where RecipeID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, description);
                    pst.setInt(2, recipeId);
                    pst.executeUpdate();
                }

                for (String string : listStep) {
                    String sql = "insert into Step (StepDescription, RecipeID, StepStatus)\n"
                            + "values (?, ?, 1)";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, string);
                    pst.setInt(2, recipeId);
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

    public static boolean insertRecipe(int userId, String repName, int prepTime, int cookTime,
            int yield, int cateId, int countryId, String description, ArrayList<String> listStep, ArrayList<Ingredient> listIngre) throws Exception {
        int result = 0;
        int recipeId = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "insert into Recipe (UserID, RecipeName, RecipeDescription, PrepTime, CookTime, Yields, CategoryID, CountryID, RecipeAddedDay, RecipeStatus)\n"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setString(2, repName);
            pst.setString(3, description);
            pst.setInt(4, prepTime);
            pst.setInt(5, cookTime);
            pst.setInt(6, yield);
            pst.setInt(7, cateId);
            pst.setInt(8, countryId);
            pst.setString(9, dt);
            result = pst.executeUpdate();

            sql = "select top 1 RecipeID\n"
                    + "from Recipe\n"
                    + "order by RecipeID desc";
            pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    recipeId = table.getInt("RecipeID");
                }
            }

            for (String string : listStep) {
                sql = "insert into Step (StepDescription, RecipeID, StepStatus)\n"
                        + "values (?, ?, 1)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, string);
                pst.setInt(2, recipeId);
                result = pst.executeUpdate();
            }

            for (Ingredient ingredient : listIngre) {
                sql = "insert into IngredientUsed (IngredientID, RecipeID)\n"
                        + "values (?, ?)";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, ingredient.getIngredientId());
                pst.setInt(2, recipeId);
                result = pst.executeUpdate();
            }

            cn.setAutoCommit(true);
            cn.close();
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean updateImgPath(int id, String imgPath) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "update Recipe\n"
                    + "set RecipeImg = ?\n"
                    + "where RecipeID = ?";
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

    public static int getNewestRecipeId() throws Exception {
        Connection cn = DBUtils.makeConnection();
        int RecipeID = 0;
        if (cn != null) {
            String sql = "select top 1 RecipeID\n"
                    + "from Recipe\n"
                    + "order by RecipeID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    RecipeID = table.getInt("RecipeID");
                }
            }
        }
        return RecipeID;
    }

    public static ArrayList<Category> getAllRecipeCates() throws Exception {
        ArrayList<Category> list = new ArrayList<>();
        //step 1: tạo connection 
        Connection cn = com.happybakery.mylib.DBUtils.makeConnection();
        if (cn != null) {
            //Step 2: viết sql 
            String sql = "select CategoryID, CategoryName\n"
                    + "from Category";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            //Step 3: xử lý kết quả step2 
            if (rs != null) {
                while (rs.next()) {
                    int CategoryID = rs.getInt("CategoryID");
                    String CategoryName = rs.getString("CategoryName");
                    Category category = new Category(CategoryID, CategoryName);
                    list.add(category);
                }// hết while 
            }//hết if
            cn.close();
        }
        return list;
    }

    public static ArrayList<Country> getAllRecipeCountries() throws Exception {
        ArrayList<Country> list = new ArrayList<>();
        //step 1: tạo connection 
        Connection cn = com.happybakery.mylib.DBUtils.makeConnection();
        if (cn != null) {
            //Step 2: viết sql 
            String sql = "select CountryID, CountryName\n"
                    + "from Country";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            //Step 3: xử lý kết quả step2 
            if (rs != null) {
                while (rs.next()) {
                    int CountryID = rs.getInt("CountryID");
                    String CountryName = rs.getString("CountryName");
                    Country country = new Country(CountryID, CountryName);
                    list.add(country);
                }// hết while 
            }//hết if
            cn.close();
        }
        return list;
    }

    public static ArrayList<Recipe> getRecipes(String email) throws Exception {
        ArrayList<Recipe> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {

            cn.setAutoCommit(false);

            String sql = "select RecipeID, Users.UserID as 'UserID', Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                    + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                    + "from Users \n"
                    + "join Recipe on Users.UserID = Recipe.UserID\n"
                    + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                    + "join Country on Country.CountryID = Recipe.CountryID\n"
                    + "where Users.UserMail = ? and Recipe.RecipeStatus != 0";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, "%" + email + "%");
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int RecipeID = table.getInt("RecipeID");
                    int UserID = table.getInt("UserID");
                    String RecipeName = table.getString("RecipeName");
                    String UserName = table.getString("UserName");
                    String RecipeDescription = table.getString("RecipeDescription");
                    String RecipeAddedDay = table.getString("RecipeAddedDay");
                    int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                    int PrepTime = table.getInt("PrepTime");
                    int CookTime = table.getInt("CookTime");
                    int Yields = table.getInt("Yields");
                    String RecipeImg = table.getString("RecipeImg");
                    String CategoryName = table.getString("CategoryName");
                    String CountryName = table.getString("CountryName");
                    Recipe recipe = new Recipe(RecipeID, UserID, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                    list.add(recipe);
                }
            }

            for (Recipe recipe : list) {
                sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                        + "from Recipe\n"
                        + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                        + "where RatedRecipeID = ?\n"
                        + "group by RatedRecipeID";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, recipe.getRecipeId());
                table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int AvgRatingPoint = table.getInt("AvgRatingPoint");
                        int NumberOfRating = table.getInt("NumberOfRating");
                        recipe.setRecipeRating(AvgRatingPoint);
                        recipe.setNumberOfRatings(NumberOfRating);
                        sql = "update Recipe\n"
                                + "set RecipeRatingPoint = ?\n"
                                + "where RecipeID = ?";
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, AvgRatingPoint);
                        pst.setInt(2, recipe.getRecipeId());
                        pst.executeUpdate();
                    }
                }
            }
            cn.setAutoCommit(true);
            cn.close();
        }
        return list;
    }

    public static Recipe getRecipesById(int recipeId) throws Exception {
        Recipe recipe = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {

            cn.setAutoCommit(false);

            String sql = "select Users.UserID as 'UserID', Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                    + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                    + "from Users\n"
                    + "join Recipe on Users.UserID = Recipe.UserID\n"
                    + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                    + "join Country on Country.CountryID = Recipe.CountryID\n"
                    + "where Recipe.RecipeID = ? and Recipe.RecipeStatus != 0";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, recipeId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int UserID = table.getInt("UserID");
                    String RecipeName = table.getString("RecipeName");
                    String UserName = table.getString("UserName");
                    String RecipeDescription = table.getString("RecipeDescription");
                    String RecipeAddedDay = table.getString("RecipeAddedDay");
                    int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                    int PrepTime = table.getInt("PrepTime");
                    int CookTime = table.getInt("CookTime");
                    int Yields = table.getInt("Yields");
                    String RecipeImg = table.getString("RecipeImg");
                    String CategoryName = table.getString("CategoryName");
                    String CountryName = table.getString("CountryName");
                    recipe = new Recipe(recipeId, UserID, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                }
            }

            sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                    + "from Recipe\n"
                    + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                    + "where RatedRecipeID = ?\n"
                    + "group by RatedRecipeID";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, recipe.getRecipeId());
            table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int AvgRatingPoint = table.getInt("AvgRatingPoint");
                    int NumberOfRating = table.getInt("NumberOfRating");
                    recipe.setRecipeRating(AvgRatingPoint);
                    recipe.setNumberOfRatings(NumberOfRating);
                    sql = "update Recipe\n"
                            + "set RecipeRatingPoint = ?\n"
                            + "where RecipeID = ?";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, AvgRatingPoint);
                    pst.setInt(2, recipe.getRecipeId());
                    pst.executeUpdate();
                }
            }
            cn.setAutoCommit(true);
            cn.close();
        }
        return recipe;
    }

    public static ArrayList<Recipe> getAllRecipes() throws Exception {
        ArrayList<Recipe> list = new ArrayList<>();
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                String sql = "select RecipeID, Users.UserID as 'UserID', Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                        + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                        + "from Users \n"
                        + "join Recipe on Users.UserID = Recipe.UserID\n"
                        + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                        + "join Country on Country.CountryID = Recipe.CountryID\n"
                        + "where Recipe.RecipeStatus != 0";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int RecipeID = table.getInt("RecipeID");
                        int UserID = table.getInt("UserID");
                        String UserName = table.getString("UserName");
                        String RecipeName = table.getString("RecipeName");
                        String RecipeDescription = table.getString("RecipeDescription");
                        String RecipeAddedDay = table.getString("RecipeAddedDay");
                        int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                        int PrepTime = table.getInt("PrepTime");
                        int CookTime = table.getInt("CookTime");
                        int Yields = table.getInt("Yields");
                        String RecipeImg = table.getString("RecipeImg");
                        String CategoryName = table.getString("CategoryName");
                        String CountryName = table.getString("CountryName");
                        Recipe recipe = new Recipe(RecipeID, UserID, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                        list.add(recipe);
                    }
                }

                for (Recipe recipe : list) {
                    sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                            + "from Recipe\n"
                            + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                            + "where RatedRecipeID = ?\n"
                            + "group by RatedRecipeID";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, recipe.getRecipeId());
                    table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int AvgRatingPoint = table.getInt("AvgRatingPoint");
                            int NumberOfRating = table.getInt("NumberOfRating");
                            recipe.setRecipeRating(AvgRatingPoint);
                            recipe.setNumberOfRatings(NumberOfRating);
                            sql = "update Recipe\n"
                                    + "set RecipeRatingPoint = ?\n"
                                    + "where RecipeID = ?";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, AvgRatingPoint);
                            pst.setInt(2, recipe.getRecipeId());
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

    public static ArrayList<Recipe> get5NewestRecipe() throws Exception {
        ArrayList<Recipe> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            cn.setAutoCommit(false);
            String sql = "select top 5 RecipeID, Users.UserID, Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, PrepTime, \n"
                    + "CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                    + "from Recipe\n"
                    + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                    + "join Country on Country.CountryID = Recipe.CountryID\n"
                    + "join Users on Users.UserID = Recipe.UserID\n"
                    + "where Recipe.RecipeStatus != 0\n"
                    + "order by RecipeID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int RecipeID = table.getInt("RecipeID");
                    int UserID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String RecipeName = table.getString("RecipeName");
                    String RecipeDescription = table.getString("RecipeDescription");
                    String RecipeAddedDay = table.getString("RecipeAddedDay");
                    int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                    int PrepTime = table.getInt("PrepTime");
                    int CookTime = table.getInt("CookTime");
                    int Yields = table.getInt("Yields");
                    String RecipeImg = table.getString("RecipeImg");
                    String CategoryName = table.getString("CategoryName");
                    String CountryName = table.getString("CountryName");
                    Recipe recipe = new Recipe(RecipeID, UserID, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                    list.add(recipe);
                }
            }

            for (Recipe recipe : list) {
                sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                        + "from Recipe\n"
                        + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                        + "where RatedRecipeID = ?\n"
                        + "group by RatedRecipeID";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, recipe.getRecipeId());
                table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int AvgRatingPoint = table.getInt("AvgRatingPoint");
                        int NumberOfRating = table.getInt("NumberOfRating");
                        recipe.setRecipeRating(AvgRatingPoint);
                        recipe.setNumberOfRatings(NumberOfRating);
                        sql = "update Recipe\n"
                                + "set RecipeRatingPoint = ?\n"
                                + "where RecipeID = ?";
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, AvgRatingPoint);
                        pst.setInt(2, recipe.getRecipeId());
                        pst.executeUpdate();
                    }
                }
            }
            cn.setAutoCommit(true);
            cn.close();
        }
        return list;
    }

    public static ArrayList<Recipe> getRecipeByUserId(int userId) throws Exception {
        ArrayList<Recipe> list = new ArrayList<>();
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                String sql = "select RecipeID, Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                        + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                        + "from Users \n"
                        + "join Recipe on Users.UserID = Recipe.UserID\n"
                        + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                        + "join Country on Country.CountryID = Recipe.CountryID\n"
                        + "where Users.UserID = ? and Recipe.RecipeStatus != 0";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int RecipeID = table.getInt("RecipeID");
                        String UserName = table.getString("UserName");
                        String RecipeName = table.getString("RecipeName");
                        String RecipeDescription = table.getString("RecipeDescription");
                        String RecipeAddedDay = table.getString("RecipeAddedDay");
                        int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                        int PrepTime = table.getInt("PrepTime");
                        int CookTime = table.getInt("CookTime");
                        int Yields = table.getInt("Yields");
                        String RecipeImg = table.getString("RecipeImg");
                        String CategoryName = table.getString("CategoryName");
                        String CountryName = table.getString("CountryName");
                        Recipe recipe = new Recipe(RecipeID, userId, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                        list.add(recipe);
                    }
                }

                for (Recipe recipe : list) {
                    sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                            + "from Recipe\n"
                            + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                            + "where RatedRecipeID = ?\n"
                            + "group by RatedRecipeID";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, recipe.getRecipeId());
                    table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int AvgRatingPoint = table.getInt("AvgRatingPoint");
                            int NumberOfRating = table.getInt("NumberOfRating");
                            recipe.setRecipeRating(AvgRatingPoint);
                            recipe.setNumberOfRatings(NumberOfRating);
                            sql = "update Recipe\n"
                                    + "set RecipeRatingPoint = ?\n"
                                    + "where RecipeID = ?";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, AvgRatingPoint);
                            pst.setInt(2, recipe.getRecipeId());
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
    public static ArrayList<Recipe> getRecipesByStatus(String status) throws Exception {
        ArrayList<Recipe> list = new ArrayList<>();
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "";
                switch (status) {
                    case "reported":
                        sql = "select RecipeID, Users.UserID as 'UserID', Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                                + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                                + "from Users \n"
                                + "join Recipe on Users.UserID = Recipe.UserID\n"
                                + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                                + "join Country on Country.CountryID = Recipe.CountryID\n"
                                + "where Recipe.RecipeStatus = 2";
                        break;
                    case "request":
                        sql = "select RecipeID, Users.UserID as 'UserID', Users.UserName as 'UserName', RecipeName, RecipeDescription, RecipeAddedDay, RecipeRatingPoint, \n"
                                + "PrepTime, CookTime, Yields, RecipeImg, Category.CategoryName as 'CategoryName', Country.CountryName as 'CountryName'\n"
                                + "from Users \n"
                                + "join Recipe on Users.UserID = Recipe.UserID\n"
                                + "join Category on Recipe.CategoryID = Category.CategoryID\n"
                                + "join Country on Country.CountryID = Recipe.CountryID\n"
                                + "where Recipe.RecipeStatus = 3";
                        break;
                }
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int RecipeID = table.getInt("RecipeID");
                        int UserID = table.getInt("UserID");
                        String UserName = table.getString("UserName");
                        String RecipeName = table.getString("RecipeName");
                        String RecipeDescription = table.getString("RecipeDescription");
                        String RecipeAddedDay = table.getString("RecipeAddedDay");
                        int RecipeRatingPoint = table.getInt("RecipeRatingPoint");
                        int PrepTime = table.getInt("PrepTime");
                        int CookTime = table.getInt("CookTime");
                        int Yields = table.getInt("Yields");
                        String RecipeImg = table.getString("RecipeImg");
                        String CategoryName = table.getString("CategoryName");
                        String CountryName = table.getString("CountryName");
                        Recipe recipe = new Recipe(RecipeID, UserID, UserName, RecipeName, RecipeDescription, RecipeAddedDay, 0, PrepTime, CookTime, Yields, RecipeImg, CategoryName, CountryName, 0);
                        list.add(recipe);
                    }
                }

                for (Recipe recipe : list) {
                    sql = "select RatedRecipeID, AVG(RatingPoint) as 'AvgRatingPoint', COUNT(CommentID) as 'NumberOfRating'\n"
                            + "from Recipe\n"
                            + "join CommentRecipe on CommentRecipe.RatedRecipeID = Recipe.RecipeID\n"
                            + "where RatedRecipeID = ?\n"
                            + "group by RatedRecipeID";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, recipe.getRecipeId());
                    table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            int AvgRatingPoint = table.getInt("AvgRatingPoint");
                            int NumberOfRating = table.getInt("NumberOfRating");
                            recipe.setRecipeRating(AvgRatingPoint);
                            recipe.setNumberOfRatings(NumberOfRating);
                            sql = "update Recipe\n"
                                    + "set RecipeRatingPoint = ?\n"
                                    + "where RecipeID = ?";
                            pst = cn.prepareStatement(sql);
                            pst.setInt(1, AvgRatingPoint);
                            pst.setInt(2, recipe.getRecipeId());
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

    public static void changeStatusRecipe(int recipeID, String status) {
        Connection cn = null;
        String sql ="";
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                switch(status){
                    case "report":
                        sql = "update [dbo].[Recipe]\n"
                        + "set RecipeStatus = 2\n"
                        + "where RecipeID = ?";
                        break;
                    case "delete":
                        sql = "update [dbo].[Recipe]\n"
                        + "set RecipeStatus = 0\n"
                        + "where RecipeID = ?";
                        break;
                }
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1,recipeID);
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
