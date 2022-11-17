/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.recipe;

import com.happybakery.dao.IngredientDAO;
import com.happybakery.dao.RecipeDAO;
import com.happybakery.dao.StepDAO;
import com.happybakery.dto.Category;
import com.happybakery.dto.Country;
import com.happybakery.dto.Ingredient;
import com.happybakery.dto.Recipe;
import com.happybakery.dto.Step;
import com.happybakery.dto.User;
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

/**
 *
 * @author thinh
 */
public class ManageRecipe extends HttpServlet {

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
            String CreateOrUpdate = request.getParameter("CreateOrUpdate");

            if (CreateOrUpdate.equalsIgnoreCase("1")) {
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    response.sendRedirect("Login.jsp");
                }

                ArrayList<Category> listCate = new ArrayList<>();
                ArrayList<Country> listCountry = new ArrayList<>();

                try {
                    listCate = RecipeDAO.getAllRecipeCates();
                    listCountry = RecipeDAO.getAllRecipeCountries();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                session.setAttribute("addRepCates", listCate);
                session.setAttribute("addRepCountry", listCountry);
                response.sendRedirect("RecipeCreate.jsp");
            } else {
                int recipeId = Integer.parseInt(request.getParameter("recipeId"));
                Recipe rep = null;
                ArrayList<Ingredient> listIngre = new ArrayList<>();
                ArrayList<Step> listStep = new ArrayList<>();
                try {
                    rep = RecipeDAO.getRecipesById(recipeId);
                    listIngre = IngredientDAO.getAllIngredientsById(recipeId);
                    listStep = StepDAO.getAllStepsById(recipeId);
                } catch (Exception ex) {
                    Logger.getLogger(ManageRecipe.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                session.setAttribute("repUpdateListStep", listStep);
                session.setAttribute("repUpdateListIngre", listIngre);
                session.setAttribute("repUpdate", rep);
                session.setAttribute("repUpdateId", rep.getRecipeId());
                session.setAttribute("repUpdateName", rep.getRecipeName());
                session.setAttribute("repUpdateCate", rep.getCategoryName());
                session.setAttribute("repUpdateDes", rep.getRecipeDesciption());
                session.setAttribute("repUpdateCountry", rep.getCountryName());
                response.sendRedirect("RecipeUpdate.jsp");
            }
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
