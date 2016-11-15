package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class Product implements Cloneable {

    String id_plato;
    String imagen;
    String nombre;
    String info_adicional;
    int precio;
    int impuesto;
    int unidad_cocina;
    int tiempo_preparacion;
    ArrayList<Ingredient> ingredientes;

    public Object clone(){
        Product obj=null;
        try{
            obj=(Product)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        if(ingredientes != null) obj.ingredientes = (ArrayList<Ingredient>) obj.ingredientes.clone();
        return obj;
    }

    public Product(Product product){
        id_plato        = product.getId_plato();
        imagen          = product.getImagen();
        nombre          = product.getNombre();
        info_adicional  = product.getInfo_adicional();
        precio          = product.getPrecio();
        impuesto        = product.getImpuesto();
        unidad_cocina   = product.getUnidad_cocina();
        tiempo_preparacion  = product.getTiempo_preparacion();
        ingredientes        = product.getIngredientes();
    }

    public String getId_plato() {
        return id_plato;
    }

    public String getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getInfo_adicional() {
        return info_adicional;
    }

    public int getPrecio() {
        return precio;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public int getUnidad_cocina() {
        return unidad_cocina;
    }

    public int getTiempo_preparacion() {
        return tiempo_preparacion;
    }

    public ArrayList<Ingredient> getIngredientes() {
        return ingredientes;
    }

    public void deleteIngredient(Ingredient ingre){

        for(int i=0; i<ingredientes.size(); i++){
            if(ingre.getId_ingrediente().equals(ingredientes.get(i).getId_ingrediente())){
                ingredientes.remove(i);
            }
        }
    }
}
