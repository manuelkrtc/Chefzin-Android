package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Horary;
import com.future333.chefzin.tools.ApiTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 5/10/16.
 */
public class ChefCtr {

    private ArrayList<Chef> chefs = new ArrayList<>();

    public ArrayList<Chef> getChefs() {
        return chefs;
    }

    //-------------------------------- public methods--------------------------------------------------------------
    public void getApiChefs(Activity ctx, String idHorary, final ApiTools.OnLogInListener logInListener){
        JsonObjectRequest jsArrayRequest_2 = new JsonObjectRequest(Request.Method.GET, ApiTools.URL_GET_CHEFZ + idHorary ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                chefs = new Gson().fromJson(response.getJSONObject("data").getJSONArray("chef").toString(), new TypeToken<List<Chef>>() {}.getType());
                                logInListener.onSuccessful();
                            }else{
                                logInListener.onError(response.getJSONObject("data").getString("mensaje"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("", "Error Respuesta en JSON: " + error.getMessage());
                        logInListener.onError("Error de conexion");
                    }
                }
        );
        SingletonVolley.getInstance(ctx).addToRequestQueue(jsArrayRequest_2);
    }
}
