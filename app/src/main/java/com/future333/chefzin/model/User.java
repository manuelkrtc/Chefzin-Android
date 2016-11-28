package com.future333.chefzin.model;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

/**
 * Created by manuel on 3/10/16.
 */
public class User {

    private String id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String tipo_documento;
    private String telefono;
    private String foto;
    private String id_facebook;
    private String id_google;
    private String api_token;
    private String email;

    private ArrayList<Order> orderRecord = new ArrayList<>();

    public void setUserFacebook(UserFacebook userFacebook){
        email       = userFacebook.getEmail();
        id_facebook = userFacebook.getId();
        apellidos   = userFacebook.getLast_name();
        nombres     = userFacebook.getFirst_name();
        foto        = "https://graph.facebook.com/v2.6/"+userFacebook.getId()+"/picture?width=200";
    }

    public void setUserGoogle(GoogleSignInAccount acct){
        email       = acct.getEmail();
        id_google   = acct.getId();
        apellidos   = acct.getFamilyName();
        nombres     = acct.getGivenName();

        if(acct.getPhotoUrl() != null)
            foto = acct.getPhotoUrl().toString();
    }
    //----------------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFoto() {
        return foto;
    }

    public String getId_facebook() {
        return id_facebook;
    }

    public String getId_google() {
        return id_google;
    }

    public String getApi_token() {
        return api_token;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Order> getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(ArrayList<Order> orderRecord) {
        this.orderRecord = orderRecord;
    }


//----------------------------------------------------------------------------------------------

}
