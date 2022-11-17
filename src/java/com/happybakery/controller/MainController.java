/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.happybakery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// chao mung den voi binh nguyen vo tan

/**
 *
 * @author thinh
 */
public class MainController extends HttpServlet {

    private String url = "Error.jsp";

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
            String action = request.getParameter("action");

            switch (action) {
                case "Login":
                    url = "Login";
                    break;
                case "logout":
                    url = "Logout";
                    break;
                case "Register":
                    url = "Register";
                    break;
                case "Addreview":
                    url = "AddRecipeCommentServlet";
                    break;
                case "Add":
                    url = "AddBlogCommentServlet";
                    break;
                case "CreateIngredient":
                    url = "CreateIngredient";
                    break;
                case "UpdateIngredient":
                    url = "UpdateIngredient";
                    break;
                case "ProfileSearchIngre":
                    url = "ProfileFilterIngredient";
                    break;
                case "CreateRecipe":
                    url = "CreateRecipe";
                    break;
                case "UpdateRecipe":
                    url = "UpdateRecipe";
                    break;
                case "ProfileSearchRecipe":
                    url = "FilterRecipe";
                    break;
                case "manageAccount":
                    url = "manageAccountServlet";
                    break;
                case "manageBlog":
                    url = "manageBlogServlet";
                    break;
                case "manageRecipe":
                    url = "manageRecipeServlet";
                    break;
                case "manageComment":
                    url = "manageCommentServlet";
                    break;
                case "changeUserStatus":
                    url = "changeUserStatusServlet";
                    break;
                case "createBlog":
                    url = "CreateBlog";
                    break;
                case "UpdateBlog":
                    url = "UpdateBlog";
                    break;
                case "addtocart":
                    url = "AddToCart";
                    break;
                case "getReportedRecipe":
                    url = "GetReportedRecipe";
                    break;
                case "getRequestRecipe":
                    url = "GetRequestRecipe";
                    break;
                case "changeStatusRecipe":
                    url = "ChangeStatusRecipe";
                    break;
                case "changeStatusBlog":
                    url = "ChangeStatusBlog";
                    break;
                case "getAllComments":
                    url = "manageCommentServlet";
                    break;
                case "ChangeStatusCommentBlog":
                    url = "ChangeStatusCommentBlog";
                    break;
                case "ChangeStatusCommentRecipe":
                    url = "ChangeStatusCommentRecipe";
                    break;
                case "getReportedBlog":
                    url = "GetReportedBlog";
                    break;
                case "AdminLogin":
                    url = "AdminLogin";
                    break;
                case "getReportedComment":
                    url = "GetReportedComment";
                    break;
                case "ReportRecipe":
                    url = "ReportRecipe";
                    break;

            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
