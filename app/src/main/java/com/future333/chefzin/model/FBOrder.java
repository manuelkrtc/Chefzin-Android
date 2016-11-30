package com.future333.chefzin.model;

/**
 * Created by manuel on 15/09/16.
 */
public class FBOrder {

    int     id;
    int     id_orden;
    String  comentario;
    int     id_estado;
    String  fecha_checkout;


    public int getId() {
        return id;
    }

    public int getId_orden() {
        return id_orden;
    }

    public String getComentario() {
        return comentario;
    }

    public int getId_estado() {
        return id_estado;
    }

    public String getFecha_checkout() {
        return fecha_checkout;
    }
}
