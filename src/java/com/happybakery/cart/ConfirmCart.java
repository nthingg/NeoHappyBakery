/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.cart;

import com.happybakery.dao.CartDAO;
import com.happybakery.dao.IngredientDAO;
import com.happybakery.dao.OrderDAO;
import com.happybakery.dao.SpecIngredientDAO;
import com.happybakery.dao.UserDAO;
import com.happybakery.dto.CartItem;
import com.happybakery.dto.SpecIngredient;
import com.happybakery.dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
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
public class ConfirmCart extends HttpServlet {

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
            HashMap<Integer, ArrayList<CartItem>> cart = (HashMap<Integer, ArrayList<CartItem>>) session.getAttribute("cart");
            User user = (User) session.getAttribute("user");
            ArrayList<SpecIngredient> listAvail = new ArrayList<>();

            try {
                listAvail = IngredientDAO.getAllSpecs();
                CartDAO.removeSessionCart(user.getUserId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            boolean enable = true;
            String specName = "";
            User store = null;

            Set<Integer> storeIds = cart.keySet();
            for (Integer id : storeIds) {
                ArrayList<CartItem> listTmp = cart.get(id);
                for (CartItem cartItem : listTmp) {
                    for (SpecIngredient specIngredient : listAvail) {
                        if (specIngredient.getAvailId() == cartItem.getItem().getAvailId()) {
                            if (specIngredient.getQuantity() < cartItem.getQuantity()) {
                                enable = false;
                                specName = specIngredient.getIngredientName();
                                try {
                                    store = UserDAO.getUserById(specIngredient.getStoreId());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if (enable) {
                OrderDAO.insertOrder(user.getUserId(), cart);
                session.setAttribute("listStoreCart", null);
                cart.clear();
                session.setAttribute("totalCart", 0);
                session.setAttribute("countCart", 0);
                session.setAttribute("fee", (int) 0);
                session.setAttribute("cart", cart);
                request.setAttribute("addSuccess", "success");
            } else {
                request.setAttribute("addFail", "fail");
                request.setAttribute("failMsg", "The quantity of " + specName + " is over store limit");
            }

            request.getRequestDispatcher("Cart.jsp").forward(request, response);

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
