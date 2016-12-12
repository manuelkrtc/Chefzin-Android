package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Coordinate;
import com.future333.chefzin.model.Horary;
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

public class OtherApi {

    public static void getVersion(final Activity ctx, final OnApiListenerError logInListener){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ToolsApi.URL_GET_VERSION,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                String version = response.getJSONObject("data").getString("version");
                                logInListener.onSuccessful(version);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseLog", error.toString());
            }
        });
        SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public interface OnApiListenerError {
        public void onSuccessful(String version);
    }

}
