/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.happybakery.cart;

import com.happybakery.dao.IngredientDAO;
import com.happybakery.dto.CartItem;
import com.happybakery.dto.SpecIngredient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
 *
 *
 */
public class AddToCart extends HttpServlet {

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
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int availId = Integer.parseInt(request.getParameter("availId"));
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            SpecIngredient item = null;
            try {
                item = IngredientDAO.getSpecByAvailId(availId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            ArrayList<CartItem> list = new ArrayList<>();
            int price = item.getPrice() * quantity;
            int salePrice = item.getPrice() * quantity * (item.getSalePercent() / 100);
            CartItem cartItem = new CartItem(item, quantity, price, salePrice, item.getAvailId());
            list.add(cartItem);

            HashMap<Integer, ArrayList<CartItem>> cart = (HashMap<Integer, ArrayList<CartItem>>) session.getAttribute("cart");
            if (cart == null) {
                cart = new HashMap<>();
                cart.put(storeId, list);
            } else {
                boolean check = true;
                ArrayList<CartItem> cartTmp = cart.get(storeId);
                if (cartTmp == null) {
                    cart.put(storeId, list);
                } else {
                    for (CartItem currCart : cartTmp) {
                        if (currCart.getItem().getAvailId() == availId) {
                            int newQuant = currCart.getQuantity() + quantity;
                            currCart.setQuantity(newQuant);
                            check = false;
                        }
                    }
                    if (check) {
                        cartTmp.add(cartItem);
                        cart.put(storeId, cartTmp);
                    }
                }
            }
            session.setAttribute("cart", cart);
            request.setAttribute("addSuccessfull", "ok");
            request.getRequestDispatcher("StoreDetail.jsp").forward(request, response);
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
