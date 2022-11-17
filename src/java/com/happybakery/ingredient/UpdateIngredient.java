/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.ingredient;

import com.happybakery.dao.IngredientDAO;
import com.happybakery.dao.SpecIngredientDAO;
import com.happybakery.dto.SpecIngredient;
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
public class UpdateIngredient extends HttpServlet {

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
            SpecIngredient ingredient = (SpecIngredient) session.getAttribute("updateSpecIngre");
            User user = (User) session.getAttribute("updateSpecUser");
            String quantity = request.getParameter("quantity");
            String price = request.getParameter("price");
            String sale = request.getParameter("sale");
            User userCheck = (User) session.getAttribute("user");
            
            IngredientDAO.updateIngredient(ingredient.getAvailId(), quantity, price, sale);
            
            int addedId = ingredient.getAvailId();
            String imgName = "ingredientUpdate" + addedId;
            String path = ImageUploader.getImgPath();
            Part imgPart = request.getPart("img");
            imgName = ImageUploader.upload(imgName, imgPart, path);
            try {
                IngredientDAO.updateImgPath(addedId, "img" + "/" + imgName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            ArrayList<SpecIngredient> specIngredient = new ArrayList<>();
            try {
                specIngredient = SpecIngredientDAO.getIngredientsById(user.getUserId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            session.setAttribute("specIngredient", specIngredient);
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
