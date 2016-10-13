package com.future333.chefzin.model.Controller;

import com.future333.chefzin.model.Horary;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ViewTools;

import java.util.ArrayList;

/**
 * Created by manuel on 11/10/16.
 */
public class ShopCart {

    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    //----------------------------------------------------------------------------------------------

    public void addProduct(Product product){
        products.add(product);
    }

    public String quantityProducts(){
        return String.valueOf(products.size());
    }

    public void clear(){
        products.clear();
    }


}
