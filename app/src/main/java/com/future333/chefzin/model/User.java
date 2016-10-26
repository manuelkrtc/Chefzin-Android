package com.future333.chefzin.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.tools.ApiTools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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

    public void setUserFacebook(UserFacebook userFacebook){
        email       = userFacebook.getEmail();
        id_facebook = userFacebook.getId();
        apellidos   = userFacebook.getLast_name();
        nombres     = userFacebook.getFirst_name();
        foto        = "https://graph.facebook.com/v2.6/"+userFacebook.getId()+"/picture?width=200";
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

    //----------------------------------------------------------------------------------------------

}
