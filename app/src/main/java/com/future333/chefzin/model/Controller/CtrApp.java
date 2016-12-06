package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Chef;
import com.future333.chefzin.model.Coordinate;
import com.future333.chefzin.tools.ToolsApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 12/5/16.
 */

public class CtrApp {

    ArrayList<Coordinate> coordinates;

    public void getCoordinates(Activity ctx, final OnApiListenerError logInListener) {
        if(coordinates != null) logInListener.onSuccessful(coordinates);
        else apiGetCoordinate(ctx, logInListener);
    }

    public void getCoordinates(Activity ctx) {
        if(coordinates == null)
            apiGetCoordinate(ctx, null);
    }

    //----------------------------------------------------------------------------------------------
    private void apiGetCoordinate(Activity ctx, final OnApiListenerError logInListener){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ToolsApi.URL_GET_COBERTURA ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                coordinates = new Gson().fromJson(response.getJSONArray("data").toString(), new TypeToken<List<Coordinate>>() {}.getType());
                                if(logInListener != null) logInListener.onSuccessful(coordinates);
                            }else{
                                if(logInListener != null) logInListener.onError(response.getJSONObject("data").getString("mensaje"));
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
                        if(logInListener != null) logInListener.onError("Error de conexion");
                    }
                }
        );
        SingletonVolley.getInstance(ctx).addToRequestQueue(objectRequest);
    }

    public interface OnApiListenerError {
        public void onSuccessful(ArrayList<Coordinate> coordinates);
        public void onError(String error);
    }
}
