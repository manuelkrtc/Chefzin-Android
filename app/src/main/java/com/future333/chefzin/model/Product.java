package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class Product {

    String id_plato;
    String imagen;
    String nombre;
    String info_adicional;
    int precio;
    int impuesto;
    int unidad_cocina;
    int tiempo_preparacion;

    public ArrayList<Ingredient> ingredientes = new ArrayList<>();

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
}
