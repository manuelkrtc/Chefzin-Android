package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.User;
import com.future333.chefzin.tools.ApiTools;
import com.future333.chefzin.tools.FormatTools;
import com.future333.chefzin.tools.ViewTools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by manuel on 5/10/16.
 */
public class UserCtrV2 {

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    public void apiLogin(final Activity ctx, String email, String password){
        if(validationLogin(ctx,email,password)){
            HashMap<String, String> parametros = new HashMap();
            parametros.put("email", email);
            parametros.put("password", FormatTools.string_to_md5(password));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiTools.URL_BASE + ApiTools.URL_LOGIN, new JSONObject(parametros),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("response")){
                                    getApiInfoUser(ctx, response.getJSONObject("data").getString("token"));
                                }else {
                                    Toast.makeText(ctx,"Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
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
    }

    private void getApiInfoUser(final Activity ctx, String token){
        JsonObjectRequest jsArrayRequest_2 = new JsonObjectRequest(
                Request.Method.GET,
                ApiTools.URL_BASE + ApiTools.URL_INFO_USER + token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getBoolean("response")){
                                logIn(ctx,response);
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
                    }
                }
        );
        SingletonVolley.getInstance(ctx).addToRequestQueue(jsArrayRequest_2);
    }

    //----------------------------------- Validation -----------------------------------------------
    private boolean validationLogin(Activity ctx, String email, String password){
        if(email.equals(""))
            ViewTools.msj(ctx,"El campo correo electrónico esta vacío.");
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            ViewTools.msj(ctx,"Correo electrónico invalido.");
        else if(password.equals(""))
            ViewTools.msj(ctx,"El campo contraseña esta vacío.");
        else if(password.length()<6)
            ViewTools.msj(ctx,"La contraseña debe tener mínimo 6 caracteres.");
        else return true;

        return false;
    }

    //----------------------------------- logIn - logOut -------------------------------------------
    public void logOut(Activity ctx){
        if(user == null){
            Log.e("ErrorChefIn","NO hay una sesion activa");
            return;
        }
        Toast.makeText(ctx, user.getNombres() + " vuelve pronto.",Toast.LENGTH_SHORT).show();
        user = null;
        saveLocalUser(ctx);
        ((MainActivity)ctx).onBackPressed();
    }

    private void logIn(Activity ctx, JSONObject response) throws JSONException {
        user = new Gson().fromJson(response.getJSONObject("data").toString(), User.class);
        Toast.makeText(ctx, "Bienvenido " + user.getNombres(),Toast.LENGTH_SHORT).show();

        saveLocalUser(ctx);
        ((MainActivity)ctx).onBackPressed();
    }

    //-------------------------------------- Save user local ---------------------------------------
    private void saveLocalUser(Activity ctx){
        Gson gson = new Gson();
        String json = gson.toJson(user);

        SharedPreferences sharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", json);

        editor.commit();
    }

    public void restoreLocalUser(Activity ctx){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE);
        String savedSession = sharedPreferences.getString("session", null);

        if(savedSession != null)
            user = gson.fromJson(savedSession,User.class);
    }
}
