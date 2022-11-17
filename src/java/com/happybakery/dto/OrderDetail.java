/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.happybakery.dto;

/**
 *
 * @author thinh
 */
public class OrderDetail {
    private int detailId;
    private int ingredientId;
    private String ingredientName;
    private String ingredientImg;
    private String ingredientCategory;
    private int orderId;
    private int quantity;
    private int finalPrice;

    public OrderDetail() {
    }

    public OrderDetail(int detailId, int ingredientId, String ingredientName, String ingredientImg, String ingredientCategory, int orderId, int quantity, int finalPrice) {
        this.detailId = detailId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientImg = ingredientImg;
        this.ingredientCategory = ingredientCategory;
        this.orderId = orderId;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }

    public String getIngredientCategory() {
        return ingredientCategory;
    }

    public String getIngredientImg() {
        return ingredientImg;
    }

    public void setIngredientCategory(String ingredientCategory) {
        this.ingredientCategory = ingredientCategory;
    }

    public void setIngredientImg(String ingredientImg) {
        this.ingredientImg = ingredientImg;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
