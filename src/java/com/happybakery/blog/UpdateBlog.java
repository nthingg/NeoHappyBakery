/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.blog;

import com.happybakery.dao.BlogDAO;
import com.happybakery.dto.Blog;
import com.happybakery.dto.User;
import com.happybakery.mylib.ImageUploader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author thinh
 */
public class UpdateBlog extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);

            User user = (User) session.getAttribute("user");
            String blogtitle = request.getParameter("blogtitle");
            String blognote = request.getParameter("blognote");
            String description = request.getParameter("description");
            String title = request.getParameter("title");
            int blogId = Integer.parseInt(request.getParameter("blogId"));

            try {
                BlogDAO.updateBlog(blogId, blognote, title, description);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //ADD IMAGE
            Part imgPart = request.getPart("img");
            if (imgPart != null) {
                int addedId = blogId;
                String imgName = "blogUpdate" + addedId;
                String path = ImageUploader.getImgPath();
                imgName = ImageUploader.upload(imgName, imgPart, path);
                try {
                    BlogDAO.updateImgPath(addedId, "img" + "/" + imgName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            //REDIRECT
            ArrayList<Blog> blogList = new ArrayList<>();
            try {
                blogList = BlogDAO.getBlogByUserId(user.getUserId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            session.setAttribute("pfBlogList", blogList);
            response.sendRedirect("StoreDetail.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
