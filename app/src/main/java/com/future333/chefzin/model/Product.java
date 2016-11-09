package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class Product {

    public String id_plato;
    public String imagen;
    public String nombre;
    public int precio;
    public int impuesto;
    public int unidad_cocina;
    public int tiempo_preparacion;

    public ArrayList<Ingredient> ingredientes = new ArrayList<>();
}
