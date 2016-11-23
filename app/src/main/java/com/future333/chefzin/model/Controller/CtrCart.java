package com.future333.chefzin.model.Controller;

import com.future333.chefzin.model.Ingredient;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsFormat;

import java.util.ArrayList;

/**
 * Created by manuel on 11/10/16.
 */
public class CtrCart {

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
    private int priceProductsIngredients(){
        int total = 0;
        for(Product product:products){
            total = total + product.getPrecio();
            if(product.getIngredientes() != null)
                for(Ingredient ingredient:product.getIngredientes()){
                    total = total + ingredient.getPrecio();
                }
        }
        return total;
    }



    private int priceIva(){
        return priceProductsIngredients()*percentIva/100;
    }

    //----------------------------------------------------------------------------------------------
    public String getSubTotal(){
        return ToolsFormat.int_to_price(priceProductsIngredients());
    }

    public String getIva(){
        return ToolsFormat.int_to_price(priceIva());
    }

    public String getDomicile(){
        return ToolsFormat.int_to_price(priceDomicile);
    }

    public String getTotal(){
        return ToolsFormat.int_to_price( priceProductsIngredients() + priceIva() + priceDomicile);
    }

}
