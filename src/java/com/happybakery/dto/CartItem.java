/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happybakery.dto;

/**
 *
 * @author thinh
 */
public class CartItem {
    private SpecIngredient item;
    private int quantity;
    private int price;
    private double salePrice;
    private int availId;

    public CartItem() {
    }

    public CartItem(SpecIngredient item, int quantity, int price, double salePrice, int availId) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.salePrice = salePrice;
        this.availId = availId;
    }

    public int getAvailId() {
        return availId;
    }

    public void setAvailId(int availId) {
        this.availId = availId;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }    

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
        
    public SpecIngredient getItem() {
        return item;
    }

    public void setItem(SpecIngredient item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
