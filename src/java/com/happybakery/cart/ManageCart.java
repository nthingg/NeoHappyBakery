package com.happybakery.cart;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import com.happybakery.dao.CartDAO;
import com.happybakery.dao.IngredientDAO;
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
public class ManageCart extends HttpServlet {

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
            User user = null;
            User loginedUser = (User) session.getAttribute("user");
            ArrayList<User> listStoreCart = new ArrayList<>();
            ArrayList<SpecIngredient> listSpec = new ArrayList<>();
            
            try {
                listSpec = IngredientDAO.getAllSpecs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            int countItem = 0;
            double total = 0;

            if (cart == null) {
            }
            if (cart != null) {
                Set<Integer> storeIds = cart.keySet();
                
                for (Integer storeId : storeIds) {
                    ArrayList<CartItem> list = cart.get(storeId);
                    for (CartItem cartItem : list) {
                        for (SpecIngredient specIngredient : listSpec) {
                            if (specIngredient.getAvailId() == cartItem.getItem().getAvailId()) {
                                cartItem.setItem(specIngredient);
                            }
                        }
                    }
                }
                
                for (Integer storeId : storeIds) {
                    try {
                        user = UserDAO.getUserById(storeId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    listStoreCart.add(user);
                }
                session.setAttribute("listStoreCart", listStoreCart);

                for (Integer storeId : storeIds) {
                    ArrayList<CartItem> list = cart.get(storeId);
                    for (CartItem cartItem : list) {
                        countItem++;
                        int price = cartItem.getItem().getPrice() * cartItem.getQuantity();
                        total += price - (price * (double) cartItem.getItem().getSalePercent() / 100);
                        double salePrice = price - (price * (double) cartItem.getItem().getSalePercent() / 100);
                        cartItem.setPrice(price);
                        cartItem.setSalePrice(salePrice);
                    }
                }

                double fee = total * (0.07);

                session.setAttribute("totalCart", total);
                session.setAttribute("countCart", countItem);
                session.setAttribute("fee", (int) fee);
            }

            if (cart != null) {
                try {
                    CartDAO.insertSessionCart(cart, loginedUser.getUserId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
