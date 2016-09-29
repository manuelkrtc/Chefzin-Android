package com.future333.chefzin.model;

/**
 * Created by manuel on 15/09/16.
 */
public class Product {

    public String name;
    public int price;
    public int image;
    public String description;

    public Product(String name, int price, int image, String description) {
        this.name   = name;
        this.price  = price;
        this.image  = image;
        this.description = description;
    }
}
