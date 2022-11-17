/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happybakery.dao;

import com.happybakery.dto.Ingredient;
import com.happybakery.dto.IngredientCate;
import com.happybakery.dto.SpecIngredient;
import com.happybakery.mylib.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author thinh
 */
public class IngredientDAO {

    public static ArrayList<Ingredient> getAllIngredientsById(int recipeId) throws Exception {
        ArrayList<Ingredient> list = new ArrayList<>();
        //step 1: tạo connection 
        Connection cn = com.happybakery.mylib.DBUtils.makeConnection();
        if (cn != null) {
            //Step 2: viết sql 
            String sql = "select Ingredient.IngredientID as 'IngredientID', IngredientName\n"
                    + "from Ingredient\n"
                    + "join IngredientUsed on Ingredient.IngredientID = IngredientUsed.IngredientID\n"
                    + "join Recipe on Recipe.RecipeID = IngredientUsed.RecipeID\n"
                    + "where Recipe.RecipeID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, recipeId);
            ResultSet rs = pst.executeQuery();
            //Step 3: xử lý kết quả step2 
            if (rs != null) {
                while (rs.next()) {
                    int IngredientID = rs.getInt("IngredientID");
                    String IngredientName = rs.getString("IngredientName");
                    Ingredient ingredient = new Ingredient(recipeId, IngredientID, IngredientName);
                    list.add(ingredient);
                }// hết while 
            }//hết if
            cn.close();
        }
        return list;
    }

    public static ArrayList<Ingredient> getAllIngredients() throws Exception {
        ArrayList<Ingredient> list = new ArrayList<>();
        //step 1: tạo connection 
        Connection cn = com.happybakery.mylib.DBUtils.makeConnection();
        if (cn != null) {
            //Step 2: viết sql 
            String sql = "select IngredientID, IngredientName\n"
                    + "from Ingredient";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            //Step 3: xử lý kết quả step2 
            if (rs != null) {
                while (rs.next()) {
                    int IngredientID = rs.getInt("IngredientID");
                    String IngredientName = rs.getString("IngredientName");
                    Ingredient ingredient = new Ingredient(0, IngredientID, IngredientName);
                    list.add(ingredient);
                }// hết while 
            }//hết if
            cn.close();
        }
        return list;
    }

    public static ArrayList<IngredientCate> getAllIngredientsCate() throws Exception {
        ArrayList<IngredientCate> list = new ArrayList<>();
        //step 1: tạo connection 
        Connection cn = com.happybakery.mylib.DBUtils.makeConnection();
        if (cn != null) {
            //Step 2: viết sql 
            String sql = "select IngredientCategoryID, IngredientCategoryName\n"
                    + "from IngredientCategory";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            //Step 3: xử lý kết quả step2 
            if (rs != null) {
                while (rs.next()) {
                    int IngredientID = rs.getInt("IngredientCategoryID");
                    String IngredientName = rs.getString("IngredientCategoryName");
                    IngredientCate ingredient = new IngredientCate(IngredientID, IngredientName);
                    list.add(ingredient);
                }// hết while 
            }//hết if
            cn.close();
        }
        return list;
    }

    public static boolean insertIngredient(int storeId, int ingredientId, int quantity, int price, int sale, int category, String ingredientName) throws Exception {
        int result = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "insert into StoreAvailabe (StoreID, IngredientID, Quantity, Price, SalePercentage, IngredientCategoryID, IngredientAddedDay, AvailStatus, SpecName) values (?, ?, ?, ?, ?, ?, ?, 1, ?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, storeId);
            pst.setInt(2, ingredientId);
            pst.setInt(3, quantity);
            pst.setInt(4, price);
            pst.setInt(5, sale);
            pst.setInt(6, category);
            pst.setString(7, dt);
            pst.setString(8, ingredientName);
            result = pst.executeUpdate();
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean insertIngredientName(String ingredientName) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "insert into Ingredient values (?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, ingredientName);
            result = pst.executeUpdate();
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static SpecIngredient getNewestSpecIngredient() throws Exception {
        SpecIngredient ingredient = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select top 1 SpecificAvailID, Ingredient.IngredientID as 'IngredientID', SpecName, StoreID, Quantity, IngredientImg, Price, SalePercentage, IngredientCategory.IngredientCategoryName as 'IngredientCategoryName', IngredientAddedDay\n"
                    + "from StoreAvailabe\n"
                    + "join Ingredient on Ingredient.IngredientID = StoreAvailabe.IngredientID\n"
                    + "join IngredientCategory on StoreAvailabe.IngredientCategoryID = IngredientCategory.IngredientCategoryID\n"
                    + "order by SpecificAvailID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null && table.next()) {
                int SpecificAvailID = table.getInt("SpecificAvailID");
                int IngredientID = table.getInt("IngredientID");
                String IngredientName = table.getString("SpecName");
                int StoreID = table.getInt("StoreID");
                int Quantity = table.getInt("Quantity");
                String IngredientImg = table.getString("IngredientImg");
                int SalePercentage = table.getInt("SalePercentage");
                int Price = table.getInt("Price");
                String IngredientCategoryName = table.getString("IngredientCategoryName");
                String IngredientAddedDay = table.getString("IngredientAddedDay");
                ingredient = new SpecIngredient(SpecificAvailID, IngredientID, IngredientName, StoreID, Quantity, IngredientImg, Price, SalePercentage, IngredientCategoryName, IngredientAddedDay);
            }
            cn.close();
        }
        return ingredient;
    }

    public static Ingredient getNewestIngredient() throws Exception {
        Ingredient ingredient = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select top 1 IngredientID, IngredientName\n"
                    + "from Ingredient\n"
                    + "order by IngredientID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null && table.next()) {
                int IngredientID = table.getInt("IngredientID");
                String IngredientName = table.getString("IngredientName");
                ingredient = new Ingredient(0, IngredientID, IngredientName);
            }
            cn.close();
        }
        return ingredient;
    }

    public static boolean insertIngredientCate(String ingredientCate) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "insert into IngredientCategory values (?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, ingredientCate);
            result = pst.executeUpdate();
        }
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static IngredientCate getNewestIngredientCate() throws Exception {
        IngredientCate ingredientCate = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select top 1 IngredientCategoryID, IngredientCategoryName\n"
                    + "from IngredientCategory\n"
                    + "order by IngredientCategoryID desc";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null && table.next()) {
                int IngredientCategoryID = table.getInt("IngredientCategoryID");
                String IngredientCategoryName = table.getString("IngredientCategoryName");
                ingredientCate = new IngredientCate(IngredientCategoryID, IngredientCategoryName);
            }
            cn.close();
        }
        return ingredientCate;
    }

    public static boolean updateImgPath(int id, String imgPath) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "update StoreAvailabe\n"
                    + "set IngredientImg = ?\n"
                    + "where SpecificAvailID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, imgPath);
            pst.setInt(2, id);
            result = pst.executeUpdate();
        }

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<SpecIngredient> getAllSpecs() throws Exception {
        ArrayList<SpecIngredient> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select SpecificAvailID, Ingredient.IngredientID as 'IngredientID', SpecName, StoreID, Quantity, IngredientImg, Price, SalePercentage, \n"
                    + "IngredientAddedDay, IngredientCategory.IngredientCategoryName as 'IngredientCategoryName'\n"
                    + "from StoreAvailabe\n"
                    + "join Ingredient on Ingredient.IngredientID = StoreAvailabe.IngredientID\n"
                    + "join IngredientCategory on IngredientCategory.IngredientCategoryID = StoreAvailabe.IngredientCategoryID";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    int SpecificAvailID = table.getInt("SpecificAvailID");
                    int IngredientID = table.getInt("IngredientID");
                    String IngredientName = table.getString("SpecName");
                    int StoreID = table.getInt("StoreID");
                    int Quantity = table.getInt("Quantity");
                    String IngredientImg = table.getString("IngredientImg");
                    int Price = table.getInt("Price");
                    int SalePercentage = table.getInt("SalePercentage");
                    String IngredientAddedDay = table.getString("IngredientAddedDay");
                    String IngredientCategoryName = table.getString("IngredientCategoryName");
                    SpecIngredient ingredient = new SpecIngredient(SpecificAvailID, IngredientID, IngredientName, StoreID, Quantity, IngredientImg, Price, SalePercentage, IngredientCategoryName, IngredientAddedDay);
                    list.add(ingredient);
                }
            }
            cn.close();
        }
        return list;
    }

    public static SpecIngredient getSpecByAvailId(int availId) throws Exception {
        SpecIngredient ingredient = null;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select SpecificAvailID, Ingredient.IngredientID as 'IngredientID', SpecName, StoreID, Quantity, IngredientImg, Price, SalePercentage, \n"
                    + "IngredientAddedDay, IngredientCategory.IngredientCategoryName as 'IngredientCategoryName'\n"
                    + "from StoreAvailabe\n"
                    + "join Ingredient on Ingredient.IngredientID = StoreAvailabe.IngredientID\n"
                    + "join IngredientCategory on IngredientCategory.IngredientCategoryID = StoreAvailabe.IngredientCategoryID\n"
                    + "where SpecificAvailID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, availId);
            ResultSet table = pst.executeQuery();
            if (table != null && table.next()) {
                int SpecificAvailID = table.getInt("SpecificAvailID");
                int IngredientID = table.getInt("IngredientID");
                String IngredientName = table.getString("SpecName");
                int StoreID = table.getInt("StoreID");
                int Quantity = table.getInt("Quantity");
                String IngredientImg = table.getString("IngredientImg");
                int Price = table.getInt("Price");
                int SalePercentage = table.getInt("SalePercentage");
                String IngredientAddedDay = table.getString("IngredientAddedDay");
                String IngredientCategoryName = table.getString("IngredientCategoryName");
                ingredient = new SpecIngredient(availId, IngredientID, IngredientName, StoreID, Quantity, IngredientImg, Price, SalePercentage, IngredientCategoryName, IngredientAddedDay);
            }
            cn.close();
        }
        return ingredient;
    }

    public static boolean updateIngredient(int availId, String quantity, String price, String sale) {
        boolean result = false;
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                //get acc by email

                if (!quantity.isEmpty()) {
                    int quant = Integer.parseInt(quantity);
                    String sql = "update StoreAvailabe\n"
                            + "set Quantity = ?\n"
                            + "where SpecificAvailID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, quant);
                    pst.setInt(2, availId);
                    pst.executeUpdate();
                }

                if (!price.isEmpty()) {
                    int priceInt = Integer.parseInt(price);
                    String sql = "update StoreAvailabe\n"
                            + "set Price = ?\n"
                            + "where SpecificAvailID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, priceInt);
                    pst.setInt(2, availId);
                    pst.executeUpdate();
                }

                if (!sale.isEmpty()) {
                    int saleInt = Integer.parseInt(sale);
                    String sql = "update StoreAvailabe\n"
                            + "set SalePercentage = ?\n"
                            + "where SpecificAvailID = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, saleInt);
                    pst.setInt(2, availId);
                    pst.executeUpdate();
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

    public static boolean removeStoreAvail(int availId, int status) throws Exception {
        int result = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE StoreAvailabe\n"
                    + "set AvailStatus = ?\n"
                    + "where SpecificAvailID = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, availId);
            result = pst.executeUpdate();
        }

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<SpecIngredient> filterIngredients(int storeId, String textSearch, String searchby) throws Exception {
        ArrayList<SpecIngredient> list = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "";
            if (searchby.equalsIgnoreCase("byCate")) {
                sql = "select SpecificAvailID, sa1.IngredientID as 'IngredientID', SpecName, sa1.StoreID as 'StoreID', sa1.Quantity as 'Quantity', sa1.IngredientImg as 'Image', sa1.Price as 'Price', \n"
                        + "sa1.SalePercentage as 'SalePercent', IngredientCategoryName as 'CategoryName', \n"
                        + "sa1.IngredientAddedDay as 'AddedDay'\n"
                        + "from StoreAvailabe sa1\n"
                        + "join Ingredient on sa1.IngredientID = Ingredient.IngredientID\n"
                        + "join IngredientCategory on sa1.IngredientCategoryID = IngredientCategory.IngredientCategoryID\n"
                        + "where sa1.StoreID = ? and sa1.AvailStatus = 1 and IngredientCategoryName like ?";
            } else if (searchby.equalsIgnoreCase("byName")) {
                sql = "select SpecificAvailID, sa1.IngredientID as 'IngredientID', SpecName, sa1.StoreID as 'StoreID', sa1.Quantity as 'Quantity', sa1.IngredientImg as 'Image', sa1.Price as 'Price', \n"
                        + "sa1.SalePercentage as 'SalePercent', IngredientCategoryName as 'CategoryName', \n"
                        + "sa1.IngredientAddedDay as 'AddedDay'\n"
                        + "from StoreAvailabe sa1\n"
                        + "join Ingredient on sa1.IngredientID = Ingredient.IngredientID\n"
                        + "join IngredientCategory on sa1.IngredientCategoryID = IngredientCategory.IngredientCategoryID\n"
                        + "where sa1.StoreID = ? and sa1.AvailStatus = 1 and Ingredient.IngredientName like ?";
            }
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, storeId);
            pst.setString(2, "%" + textSearch + "%");
            ResultSet rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    int SpecificAvailID = rs.getInt("SpecificAvailID");
                    int IngredientID = rs.getInt("IngredientID");
                    String IngredientName = rs.getString("SpecName");
                    int Quantity = rs.getInt("Quantity");
                    String Image = rs.getString("Image");
                    int Price = rs.getInt("Price");
                    int SalePercent = rs.getInt("SalePercent");
                    String CategoryName = rs.getString("CategoryName");
                    String AddedDay = rs.getString("AddedDay");
                    SpecIngredient specIngredient = new SpecIngredient(SpecificAvailID, IngredientID, IngredientName, storeId, Quantity, Image, Price, SalePercent, CategoryName, AddedDay);
                    list.add(specIngredient);
                }
            }
            cn.close();
        }
        return list;
    }
}
