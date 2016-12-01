package com.future333.chefzin.model;

/**
 * Created by manuel on 15/09/16.
 */
public class Address implements Cloneable {

    String  id;
    String  id_usuario;
    String  telefono;
    String  coordenada;
    String  descripcion;
    String  comentarios;

    public String getId() {
        return id;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getComentarios() {
        return comentarios;
    }
}
