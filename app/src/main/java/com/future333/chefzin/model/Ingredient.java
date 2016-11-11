package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class Ingredient implements Cloneable {

    String  nombre;
    String  id_ingrediente;
    int     precio;

    public Object clone(){
        Ingredient obj=null;
        try{
            obj=(Ingredient)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId_ingrediente() {
        return id_ingrediente;
    }

    public int getPrecio() {
        return precio;
    }
}
