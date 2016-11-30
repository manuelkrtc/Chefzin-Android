package com.future333.chefzin.model;

import java.util.ArrayList;

/**
 * Created by manuel on 15/09/16.
 */
public class FBData {

    ArrayList<FBOrder> estado_orden                 = new ArrayList<>();
    ArrayList<FBDomiciliary> estado_domiciliario    = new ArrayList<>();

    public ArrayList<FBOrder> getEstado_orden() {
        return estado_orden;
    }

    public ArrayList<FBDomiciliary> getEstado_domiciliario() {
        return estado_domiciliario;
    }
}
