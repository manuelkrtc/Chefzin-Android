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

    public void setId(String id) {
        this.id = id;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
