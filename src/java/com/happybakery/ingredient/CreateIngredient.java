/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.ingredient;

import com.happybakery.dao.IngredientDAO;
import com.happybakery.dao.SpecIngredientDAO;
import com.happybakery.dao.UserDAO;
import com.happybakery.dto.Ingredient;
import com.happybakery.dto.IngredientCate;
import com.happybakery.dto.SpecIngredient;
import com.happybakery.dto.User;
import com.happybakery.mylib.ImageUploader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CreateIngredient extends HttpServlet {

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
            
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            String ingredientName = request.getParameter("name");
            String ingredientCate = request.getParameter("category");
            int ingredientQuant = Integer.parseInt(request.getParameter("quantity"));
            int ingredientPrice = Integer.parseInt(request.getParameter("price"));
            int ingredientSale = Integer.parseInt(request.getParameter("sale"));

            ArrayList<Ingredient> listIngre = null;

            try {
                listIngre = IngredientDAO.getAllIngredients();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            ArrayList<IngredientCate> listIngreCate = null;
            try {
                listIngreCate = IngredientDAO.getAllIngredientsCate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            boolean checkNameContain = false;
            boolean checkCateContain = false;
            int ingreId = 0;
            int cateId = 0;

            for (Ingredient ingredient : listIngre) {
                if (ingredientName.equalsIgnoreCase(ingredient.getIngredientName())) {
                    checkNameContain = true;
                    ingreId = ingredient.getIngredientId();
                    break;
                }
            }

            for (IngredientCate ingredientCategory : listIngreCate) {
                if (ingredientCate.equalsIgnoreCase(ingredientCategory.getCategoryName())) {
                    checkCateContain = true;
                    cateId = ingredientCategory.getCategoryId();
                    break;
                }
            }

            if (!checkNameContain) {
                try {
                    IngredientDAO.insertIngredientName(ingredientName);
                    Ingredient tmpIngre = IngredientDAO.getNewestIngredient();
                    ingreId = tmpIngre.getIngredientId();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (!checkCateContain) {
                try {
                    IngredientDAO.insertIngredientCate(ingredientCate);
                    IngredientCate tmpIngre = IngredientDAO.getNewestIngredientCate();
                    cateId = tmpIngre.getCategoryId();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            try {
                IngredientDAO.insertIngredient(storeId, ingreId, ingredientQuant, ingredientPrice, ingredientSale, cateId, ingredientName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SpecIngredient newIngredient = null;
            try {
                newIngredient = IngredientDAO.getNewestSpecIngredient();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            int addedId = newIngredient.getAvailId();
            String imgName = "ingredient" + addedId;
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
                specIngredient = SpecIngredientDAO.getIngredientsById(storeId);
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
