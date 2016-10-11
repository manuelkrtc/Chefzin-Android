package com.future333.chefzin.model;

/**
 * Created by manuel on 15/09/16.
 */
public class Horary {

    public String   id;
    public String   nombre;
    public String   hora_ini;
    public String   hora_fin;
    public String   foto_web;
    public String   foto_movil;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(String hora_ini) {
        this.hora_ini = hora_ini;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getFoto_web() {
        return foto_web;
    }

    public void setFoto_web(String foto_web) {
        this.foto_web = foto_web;
    }

    public String getFoto_movil() {
        return foto_movil;
    }

    public void setFoto_movil(String foto_movil) {
        this.foto_movil = foto_movil;
    }
}
