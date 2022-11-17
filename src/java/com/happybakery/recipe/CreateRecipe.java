/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.recipe;

import com.happybakery.dao.IngredientDAO;
import com.happybakery.dao.RecipeDAO;
import com.happybakery.dao.UserDAO;
import com.happybakery.dto.Ingredient;
import com.happybakery.dto.Recipe;
import com.happybakery.dto.User;
import com.happybakery.mylib.ImageUploader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
public class CreateRecipe extends HttpServlet {

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

            //GET ALL FILLED INFORMATION
            User user = (User) session.getAttribute("user");
            String recipeName = request.getParameter("repname");
            int prepTime = Integer.parseInt(request.getParameter("preptime"));
            int cookTime = Integer.parseInt(request.getParameter("cooktime"));
            int yield = Integer.parseInt(request.getParameter("yield"));
            int categoryId = Integer.parseInt(request.getParameter("category"));
            int countryId = Integer.parseInt(request.getParameter("country"));
            String description = request.getParameter("description");

            String[] listIngreName = request.getParameterValues("ingredients");
            String[] listStep = request.getParameterValues("step");
            ArrayList<String> listStepCv = new ArrayList<>();
            for (String string : listStep) {
                listStepCv.add(string);
            }
            
            // TAKE INGREDIENT ALREADY HAVE
            ArrayList<Ingredient> listIngre = new ArrayList<>();
            try {
                listIngre = IngredientDAO.getAllIngredients();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ArrayList<String> listIngredient = new ArrayList<>();
            for (String string : listIngreName) {
                listIngredient.add(string);
            }

            ArrayList<Ingredient> listIngreCv = new ArrayList<>();
            ArrayList<String> listIngredientRemain = new ArrayList<>();
            for (String string : listIngredient) {
                boolean checkIsAdd = false;
                for (Ingredient ingredient : listIngre) {
                    if (string.toLowerCase().contains(ingredient.getIngredientName().toLowerCase())) {
                        listIngreCv.add(ingredient);
                        checkIsAdd = true;
                    } 
                }
                if (checkIsAdd == false) {
                    listIngredientRemain.add(string);
                }
            }
            
            
            //ADD INGREDIENTS THAT DOESNT HAVE
            for (String string : listIngredientRemain) {
                try {
                    IngredientDAO.insertIngredientName(string);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            try {
                listIngre = IngredientDAO.getAllIngredients();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            for (String string : listIngredientRemain) {
                for (Ingredient ingredient : listIngre) {
                    if (string.toLowerCase().contains(ingredient.getIngredientName().toLowerCase())) {
                        listIngreCv.add(ingredient);
                    }
                }
            }
            
            

            try {
                RecipeDAO.insertRecipe(user.getUserId(), recipeName, prepTime, cookTime, yield, categoryId, countryId, description, listStepCv, listIngreCv);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //ADD IMAGE
            int newRep = 0;
            try {
                newRep = RecipeDAO.getNewestRecipeId();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            int addedId = newRep;
            String imgName = "recipe" + addedId;
            String path = ImageUploader.getImgPath();
            Part imgPart = request.getPart("img");
            imgName = ImageUploader.upload(imgName, imgPart, path);
            try {
                RecipeDAO.updateImgPath(addedId, "img" + "/" + imgName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            //REDIRECT
            ArrayList<Recipe> recipeList = new ArrayList<>();
            try {
                recipeList = RecipeDAO.getRecipeByUserId(user.getUserId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            session.setAttribute("pfRecipeList", recipeList);
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
