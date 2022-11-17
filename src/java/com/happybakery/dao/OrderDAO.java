/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.happybakery.dao;

import com.happybakery.dto.CartItem;
import com.happybakery.dto.Order;
import com.happybakery.dto.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.happybakery.mylib.DBUtils;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author thinh
 */
public class OrderDAO {

    public static boolean updateStatus(int orderId, int status) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "update Orders\n"
                    + "set OrderStatus = ?\n"
                    + "where OrderID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, orderId);
            result = pst.executeUpdate();
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean insertOrder(int userId, HashMap<Integer, ArrayList<CartItem>> cart) {
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                int orderid = 0;
                cn.setAutoCommit(false);
                //get acc by email

                String sql = "";
                PreparedStatement pst = cn.prepareStatement(sql);

                //insert a new order
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String dt = dtf.format(now);
                sql = "insert Orders(OrderDate, OrderStatus, UserID) \n"
                        + "values (?, 1, ?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, dt);
                pst.setInt(2, userId);
                pst.executeUpdate();

                //get the newest added order = biggest orderid
                sql = "select top 1 OrderID \n"
                        + "from Orders \n"
                        + "order by OrderID desc";
                pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    orderid = rs.getInt("OrderID");
                }

                Set<Integer> storeIds = cart.keySet();
                for (Integer id : storeIds) {
                    ArrayList<CartItem> listTmp = cart.get(id);
                    for (CartItem cartItem : listTmp) {
                        sql = "insert into OrderDetail (SpecificAvailID, OrderID, Quantity, FinalPrice)\n"
                                + "values (?, ?, ?, ?)";
                        int price = cartItem.getItem().getPrice() * cartItem.getQuantity();
                        double salePrice = price - (price * (double) cartItem.getItem().getSalePercent() / 100);
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, cartItem.getItem().getAvailId());
                        pst.setInt(2, orderid);
                        pst.setInt(3, cartItem.getQuantity());
                        pst.setInt(4, (int) salePrice);
                        pst.executeUpdate();

                    }
                }

                Set<Integer> storesIds = cart.keySet();
                for (Integer id : storesIds) {
                    ArrayList<CartItem> listTmp = cart.get(id);
                    for (CartItem cartItem : listTmp) {

                        int quantity = 0;
                        sql = "select Quantity\n"
                                + "from StoreAvailabe\n"
                                + "where SpecificAvailID = ?";
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, cartItem.getItem().getAvailId());
                        rs = pst.executeQuery();
                        if (rs != null && rs.next()) {
                            quantity = rs.getInt("Quantity");
                        }

                        sql = "UPDATE StoreAvailabe\n"
                                + "set Quantity = ?\n"
                                + "where SpecificAvailID = ?";
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, quantity - cartItem.getQuantity());
                        pst.setInt(2, cartItem.getItem().getAvailId());
                        pst.executeUpdate();
                    }
                }

                cn.commit();
                cn.setAutoCommit(true);
                return true;
            }
        } catch (Exception e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static ArrayList<Order> getAllOrdersUser(int userId) throws Exception {
        ArrayList<Order> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID as 'UserID', orderer.UserName as 'UserName', \n"
                    + "orderer.UserType as 'UserType', store.UserID as 'StoreID', store.UserName as 'StoreName', COUNT(orderer.UserID)\n"
                    + "from Users orderer\n"
                    + "join Orders on Orders.UserID = orderer.UserID\n"
                    + "join OrderDetail on Orders.OrderID = OrderDetail.OrderID\n"
                    + "join StoreAvailabe on OrderDetail.SpecificAvailID = StoreAvailabe.SpecificAvailID\n"
                    + "join Users store on StoreAvailabe.StoreID = store.UserID\n"
                    + "where orderer.UserID = ? \n"
                    + "group by Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID, orderer.UserName, \n"
                    + "orderer.UserType, store.UserID, store.UserName";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int orderID = table.getInt("OrderID");
                    String orderDate = table.getString("OrderDate");
                    String shipDate = table.getString("ShipDate");
                    int orderStatus = table.getInt("OrderStatus");
                    int userID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String UserType = table.getString("UserType");
                    int StoreID = table.getInt("StoreID");
                    String StoreName = table.getString("StoreName");
                    Order order = new Order("", orderID, orderDate, shipDate, orderStatus, userID, UserName, UserType, StoreID, StoreName);
                    list.add(order);
                }
            }
            cn.close();
        }
        return list;
    }

    public static ArrayList<Order> getAllOrdersStore(int storeId) throws Exception { //done
        ArrayList<Order> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID as 'UserID', orderer.UserName, \n"
                    + "orderer.UserType, COUNT(orderer.UserID), store.UserID as 'StoreID', store.UserName as 'StoreName'\n"
                    + "from Orders  \n"
                    + "join OrderDetail on Orders.OrderID = OrderDetail.OrderID \n"
                    + "join StoreAvailabe on OrderDetail.SpecificAvailID = StoreAvailabe.SpecificAvailID\n"
                    + "join Users orderer on orderer.UserID = Orders.UserID\n"
                    + "join Users store on store.UserID = StoreAvailabe.StoreID\n"
                    + "where StoreAvailabe.StoreID = ?\n"
                    + "group by Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID, orderer.UserName, orderer.UserType, store.UserID, store.UserName";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, storeId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int orderID = table.getInt("OrderID");
                    String orderDate = table.getString("OrderDate");
                    String shipDate = table.getString("ShipDate");
                    int orderStatus = table.getInt("OrderStatus");
                    int userID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String UserType = table.getString("UserType");
                    int StoreID = table.getInt("StoreID");
                    String StoreName = table.getString("StoreName");
                    Order order = new Order("", orderID, orderDate, shipDate, orderStatus, userID, UserName, UserType, StoreID, StoreName);
                    list.add(order);
                }
            }
            cn.close();
        }
        return list;
    }

    public static ArrayList<Order> getOrdersStoreByStatus(int storeId, int orderStatus) throws Exception { //Done
        ArrayList<Order> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID as 'UserID', orderer.UserName, \n"
                    + "orderer.UserType, COUNT(orderer.UserID), store.UserID as 'StoreID', store.UserName as 'StoreName'\n"
                    + "from Orders  \n"
                    + "join OrderDetail on Orders.OrderID = OrderDetail.OrderID \n"
                    + "join StoreAvailabe on OrderDetail.SpecificAvailID = StoreAvailabe.SpecificAvailID\n"
                    + "join Users orderer on orderer.UserID = Orders.UserID\n"
                    + "join Users store on store.UserID = StoreAvailabe.StoreID\n"
                    + "where StoreAvailabe.StoreID = ? and Orders.OrderStatus = ?\n"
                    + "group by Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID, orderer.UserName, orderer.UserType, store.UserID, store.UserName";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, storeId);
            pst.setInt(2, orderStatus);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int orderID = table.getInt("OrderID");
                    String orderDate = table.getString("OrderDate");
                    String shipDate = table.getString("ShipDate");
                    int userID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String UserType = table.getString("UserType");
                    int StoreID = table.getInt("StoreID");
                    String StoreName = table.getString("StoreName");
                    Order order = new Order("", orderID, orderDate, shipDate, orderStatus, userID, UserName, UserType, StoreID, StoreName);
                    list.add(order);
                }
            }
            cn.close();
        }
        return list;
    }

    public static ArrayList<Order> getOrdersUserByStatus(int userId, int orderStatus) throws Exception {
        ArrayList<Order> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID as 'UserID', orderer.UserName as 'UserName', \n"
                    + "orderer.UserType as 'UserType', store.UserID as 'StoreID', store.UserName as 'StoreName', COUNT(orderer.UserID)\n"
                    + "from Users orderer\n"
                    + "join Orders on Orders.UserID = orderer.UserID\n"
                    + "join OrderDetail on Orders.OrderID = OrderDetail.OrderID\n"
                    + "join StoreAvailabe on OrderDetail.SpecificAvailID = StoreAvailabe.SpecificAvailID\n"
                    + "join Users store on StoreAvailabe.StoreID = store.UserID\n"
                    + "where orderer.UserID = ? and Orders.OrderStatus = ?\n"
                    + "group by Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID, orderer.UserName, \n"
                    + "orderer.UserType, store.UserID, store.UserName";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setInt(2, orderStatus);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int orderID = table.getInt("OrderID");
                    String orderDate = table.getString("OrderDate");
                    String shipDate = table.getString("ShipDate");
                    int userID = table.getInt("UserID");
                    String UserName = table.getString("UserName");
                    String UserType = table.getString("UserType");
                    int StoreID = table.getInt("StoreID");
                    String StoreName = table.getString("StoreName");
                    Order order = new Order("", orderID, orderDate, shipDate, orderStatus, userID, UserName, UserType, StoreID, StoreName);
                    list.add(order);
                }
            }
            cn.close();
        }
        return list;
    }

    public static Order getOrderById(int orderId) throws Exception {
        Order order = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select top 1 Orders.OrderID, OrderDate, ShipDate, OrderStatus, orderer.UserID as 'UserID', orderer.UserName as 'UserName', \n"
                    + "orderer.UserType as 'UserType', store.UserID as 'StoreID', store.UserName as 'StoreName'\n"
                    + "from Users orderer\n"
                    + "join Orders on Orders.UserID = orderer.UserID\n"
                    + "join OrderDetail on Orders.OrderID = OrderDetail.OrderID\n"
                    + "join StoreAvailabe on OrderDetail.SpecificAvailID = StoreAvailabe.SpecificAvailID\n"
                    + "join Users store on StoreAvailabe.StoreID = store.UserID\n"
                    + "where Orders.OrderID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet table = pst.executeQuery();
            if (table != null && table.next()) {
                int orderID = table.getInt("OrderID");
                String orderDate = table.getString("OrderDate");
                String shipDate = table.getString("ShipDate");
                int OrderStatus = table.getInt("OrderStatus");
                int userID = table.getInt("UserID");
                String UserName = table.getString("UserName");
                String UserType = table.getString("UserType");
                int StoreID = table.getInt("StoreID");
                String StoreName = table.getString("StoreName");
                order = new Order("", orderID, orderDate, shipDate, OrderStatus, userID, UserName, UserType, StoreID, StoreName);
            }
            cn.close();
        }
        return order;
    }

    public static ArrayList<OrderDetail> getDetailsById(int orderId) throws Exception {
        ArrayList<OrderDetail> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select DetailID, StoreAvailabe.SpecificAvailID as 'SpecificAvailID', Ingredient.IngredientName as 'IngredientName', \n"
                    + "StoreAvailabe.IngredientImg as 'IngredientImg',\n"
                    + "IngredientCategory.IngredientCategoryName as 'IngredientCategoryName', OrderID, \n"
                    + "OrderDetail.Quantity as 'Quantity', OrderDetail.FinalPrice as 'FinalPrice'\n"
                    + "from OrderDetail\n"
                    + "join StoreAvailabe on StoreAvailabe.SpecificAvailID = OrderDetail.SpecificAvailID\n"
                    + "join Ingredient on StoreAvailabe.IngredientID = Ingredient.IngredientID\n"
                    + "join IngredientCategory on StoreAvailabe.IngredientCategoryID = IngredientCategory.IngredientCategoryID\n"
                    + "where OrderID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int DetailID = table.getInt("DetailID");
                    int SpecificAvailID = table.getInt("SpecificAvailID");
                    String IngredientName = table.getString("IngredientName");
                    String IngredientImg = table.getString("IngredientImg");
                    String IngredientCategoryName = table.getString("IngredientCategoryName");
                    int OrderID = table.getInt("OrderID");
                    int Quantity = table.getInt("Quantity");
                    int FinalPrice = table.getInt("FinalPrice");
                    OrderDetail order = new OrderDetail(DetailID, SpecificAvailID, IngredientName, IngredientImg, IngredientCategoryName, OrderID, Quantity, FinalPrice);
                    list.add(order);
                }
            }
            cn.close();
        }
        return list;
    }
}
