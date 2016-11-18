package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Horary;
import com.future333.chefzin.tools.ToolsApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 5/10/16.
 */
public class HoraryCtr {

    private ArrayList<Horary> horaries = new ArrayList<>();

    public ArrayList<Horary> getHoraries() {
        return horaries;
    }

    public void setHoraries(ArrayList<Horary> horaries) {
        this.horaries = horaries;
    }

    //-------------------------------- public methods--------------------------------------------------------------
    public void getApiHorary(final Activity ctx, final ToolsApi.OnLogInListener logInListener){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ToolsApi.URL_HORARIOS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                horaries = new Gson().fromJson(response.getJSONArray("data").toString(), new TypeToken<List<Horary>>() {}.getType());
                                logInListener.onSuccessful();
                            }else {
                                logInListener.onError(response.getJSONObject("data").getString("mensaje"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseLog", error.toString());
                logInListener.onError("Error de conexión.");
            }
        });
        SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

}
