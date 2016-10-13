package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class Product {

    public String name;
    public int price;
    public int image;
    public String description;
    public ArrayList<Addition> additions = new ArrayList<>();


    public Product(String name, int price, int image, String description, ArrayList<Addition> additions) {
        this.name   = name;
        this.price  = price;
        this.image  = image;
        this.description = description;
        this.additions = additions;
    }
}
