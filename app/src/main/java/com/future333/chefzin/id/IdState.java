package com.future333.chefzin.id;

/**
 * Created by manuel on 11/25/16.
 */

public class IdState {
    public static final int ID_CHEF_INACTIVO     = 1;
    public static final int ID_CHEF_ACTIVO       = 2;
    public static final int ID_CHEF_OCUPADO      = 3;
    public static final int ID_CHEF_PANICO       = 4;

    public static final int ID_CARRITO           = 11;
    public static final int ID_CHECKOUT          = 12;
    public static final int ID_REC_Y_COC         = 13;
    public static final int ID_LISTO_ENTREGAR    = 14;
    public static final int ID_EN_CAMINO         = 15;
    public static final int ID_ENTREGADO         = 16;

    public static final int ID_DOMI_INACTIVO              = 21;
    public static final int ID_DOMI_DISPONIBLE            = 22;
    public static final int ID_DOMI_EN_CAMINO_RECOGER     = 23;
    public static final int ID_DOMI_EN_CAMINO_ENTREGAR    = 24;

    public static final int ID_FRANJA_ACTIVA    = 1;
    public static final int ID_FRANJA_INACTIVA  = 0;

    public static String nameSteps(int idState){
        if(idState == ID_REC_Y_COC)         return "Recibido y cocinando";
        if(idState == ID_LISTO_ENTREGAR)    return "Listo para entregar";
        if(idState == ID_EN_CAMINO)         return "En camino";
        if(idState == ID_ENTREGADO)         return "Entregado";
        return "";
    }
}
