package com.future333.chefzin.model.Controller;

import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.FormatTools;

import java.util.ArrayList;

/**
 * Created by manuel on 11/10/16.
 */
public class ShopCart {

    private int percentIva      = 16;
    private int priceDomicile   = 5000;
    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    //----------------------------------------------------------------------------------------------

    public void addProduct(Product product){
        products.add(product);
    }

    public void deleteProduct(Product product){
        products.remove(product);
    }

    public String quantityProducts(){
        return String.valueOf(products.size());
    }

    public void clear(){
        products.clear();
    }

    //----------------------------------------------------------------------------------------------
    private int priceProducts(){
        int total = 0;
        for(Product product:products){
            total = total + product.precio;
        }
        return total;
    }

    private int priceIva(){
        return priceProducts()*percentIva/100;
    }

    //----------------------------------------------------------------------------------------------
    public String getSubTotal(){
        return "$" + FormatTools.int_to_price(priceProducts());
    }

    public String getIva(){
        return "$" + FormatTools.int_to_price(priceIva());
    }

    public String getDomicile(){
        return "$" + FormatTools.int_to_price(priceDomicile);
    }

    public String getTotal(){
        return "$" + FormatTools.int_to_price( priceProducts() + priceIva() + priceDomicile);
    }

}
