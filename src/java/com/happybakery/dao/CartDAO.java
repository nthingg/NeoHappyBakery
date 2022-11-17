/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happybakery.dao;

import com.happybakery.dto.CartItem;
import com.happybakery.mylib.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author thinh
 */
public class CartDAO {

    public static boolean removeSessionCart(int userId) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "DELETE FROM SessionCart \n"
                    + "WHERE UserID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            result = pst.executeUpdate();
        }

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static HashMap<Integer, ArrayList<CartItem>> getSessionCart(int userId) throws Exception {
        HashMap<Integer, ArrayList<CartItem>> cart = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select UserID, StoreID, ItemID, Price, SalePrice, Quantity\n"
                    + "from SessionCart\n"
                    + "where UserID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int UserID = table.getInt("UserID");
                    int StoreID = table.getInt("StoreID");
                    int ItemID = table.getInt("ItemID");
                    int Price = table.getInt("Price");
                    int SalePrice = table.getInt("SalePrice");
                    int Quantity = table.getInt("Quantity");
                    CartItem item = new CartItem(null, Quantity, Price, SalePrice, ItemID);

                    if (cart == null) {
                        ArrayList<CartItem> listTmp = new ArrayList<>();
                        listTmp.add(item);
                        cart = new HashMap<>();
                        cart.put(StoreID, listTmp);
                    } else {
                        if (cart.containsKey(StoreID)) {
                            ArrayList<CartItem> list = cart.get(StoreID);
                            list.add(item);
                        } else {
                            ArrayList<CartItem> listTmp = new ArrayList<>();
                            listTmp.add(item);
                            cart.put(StoreID, listTmp);
                        }
                    }
                }
            }
            cn.close();
        }
        return cart;
    }

    public static boolean insertSessionCart(HashMap<Integer, ArrayList<CartItem>> cart, int userId) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {

            cn.setAutoCommit(false);

            String delete = "DELETE FROM SessionCart\n"
                    + "WHERE UserID = ?";
            PreparedStatement pstDel = cn.prepareStatement(delete);
            pstDel.setInt(1, userId);
            pstDel.executeUpdate();

            Set<Integer> storeIds = cart.keySet();
            for (Integer id : storeIds) {
                ArrayList<CartItem> listTmp = cart.get(id);
                for (CartItem cartItem : listTmp) {
                    String sql = "insert into SessionCart (UserID, StoreID, ItemID, Price, SalePrice, Quantity) \n"
                            + "values (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, userId);
                    pst.setInt(2, id);
                    pst.setInt(3, cartItem.getItem().getAvailId());
                    pst.setInt(4, cartItem.getPrice());
                    pst.setInt(5, (int) cartItem.getSalePrice());
                    pst.setInt(6, cartItem.getQuantity());
                    result = pst.executeUpdate();
                }
            }

            cn.commit();
            cn.setAutoCommit(true);
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}
