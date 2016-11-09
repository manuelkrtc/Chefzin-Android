package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class Chef {

    String id_chef;
    String nombres;
    String apellidos;
    String descripcion;
    String capacidad_cocina;
    String id_especializacion;
    String especializacion;
    String foto;
    String frase_especialidad;
    ArrayList<Product> platos = new ArrayList<>();


    public String getId_chef() {
        return id_chef;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCapacidad_cocina() {
        return capacidad_cocina;
    }

    public String getId_especializacion() {
        return id_especializacion;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public String getFoto() {
        return foto;
    }

    public String getFrase_especialidad() {
        return frase_especialidad;
    }

    public ArrayList<Product> getPlatos() {
        return platos;
    }
}
