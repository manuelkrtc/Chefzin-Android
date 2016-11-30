package com.future333.chefzin.model;


import java.util.ArrayList;

/**
 * Created by manuel on 3/10/16.
 */
public class Order {

    private int    id_orden;
    private String id_direccion;
    private String subtotal;
    private String impuesto;
    private String total;
    private String fecha;
    private String comentarios;
    private String id_chef;
    private int    id_estado;

    private ArrayList<Product> platos = new ArrayList<>();


    //----------------------------------------------------------------------------------------------

    public int getId_orden() {
        return id_orden;
    }

    public String getId_direccion() {
        return id_direccion;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public String getTotal() {
        return total;
    }

    public String getFecha() {
        return fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public String getId_chef() {
        return id_chef;
    }

    public int getId_estado() {
        return id_estado;
    }

    public ArrayList<Product> getPlatos() {
        return platos;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }


//----------------------------------------------------------------------------------------------

}
