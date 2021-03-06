package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Address;
import com.future333.chefzin.model.FormRegister;
import com.future333.chefzin.model.Order;
import com.future333.chefzin.model.User;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by manuel on 5/10/16.
 */
public class CtrUser {

    private User user;

    public User getUser() {
        return user;
    }

    //-------------------------------- public methods--------------------------------------------------------------

    public void logIn(Activity ctx, String email, String password, ToolsApi.OnApiListenerError logInListener){
        String validationLogin = validationLogin(email, password);
        if(validationLogin==null){
            apiLogin(ctx,email,password,logInListener);
        }else {
            logInListener.onError(validationLogin);
        }
    }

    public void logOut(Activity ctx, ToolsApi.OnApiListener logOutListener){
        if(this.user == null){
            Log.e("ErrorChefIn","NO hay una sesion activa");
            return;
        }
        saveLocalUser(ctx,null);
        logOutListener.onSuccessful();
    }

    public void register(Activity ctx, FormRegister formRegister, ToolsApi.OnApiListenerError logInListener){
        String validationRegister = validationRegister(formRegister);
        if(validationRegister==null){
            try {
                apiRegister(ctx,formRegister,logInListener );
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("errorChefzin","error registro");
            }
        }else {
            logInListener.onError(validationRegister);
        }
    }

    public void registerFacebook(Activity ctx, User _user, ToolsApi.OnApiListenerError logInListener){
        String validationRegister = validationRegisterFacebook(_user);
        if(validationRegister==null){
            try {
                apiRegisterFacebook(ctx,_user,logInListener );
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("errorChefzin","error registro");
            }
        }
    else {
            logInListener.onError(validationRegister);
        }
    }

    public void registerGoogle(Activity ctx, User _user, ToolsApi.OnApiListenerError logInListener){
        String validationRegister = validationRegisterGoogle(_user);
        if(validationRegister==null){
            try {
                apiRegisterGoogle(ctx,_user,logInListener );
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("errorChefzin","error registro");
            }
        }
        else {
            logInListener.onError(validationRegister);
        }
    }

    public void getUserLocal(Activity ctx){
        restoreLocalUser(ctx);
    }

    //----------------------------------------------------------------------------------------------
    private void apiLogin(final Activity ctx, String email, String password, final ToolsApi.OnApiListenerError logInListener){
            HashMap<String, String> parametros = new HashMap();
            parametros.put("email", email);
            parametros.put("password", ToolsFormat.string_to_md5(password));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_LOGIN, new JSONObject(parametros),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("response")){
                                    getApiInfoUser(ctx, response.getJSONObject("data").getString("token"),logInListener);
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

    private void apiRegister(final Activity ctx, FormRegister formRegister, final ToolsApi.OnApiListenerError logInListener) throws JSONException {
        String parametros = new Gson().toJson(formRegister);
        JSONObject jsonParam = new JSONObject(parametros);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_REGISTER, jsonParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                getApiInfoUser(ctx, response.getJSONObject("data").getString("token"),logInListener);
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

    private void apiRegisterFacebook(final Activity ctx, User _user, final ToolsApi.OnApiListenerError logInListener) throws JSONException {
        String parametros = new Gson().toJson(_user);
        JSONObject jsonParam = new JSONObject(parametros);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_REGISTER_FACEBOOK, jsonParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                getApiInfoUser(ctx, response.getJSONObject("data").getString("token"),logInListener);
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

    private void apiRegisterGoogle(final Activity ctx, User _user, final ToolsApi.OnApiListenerError logInListener) throws JSONException {
        String parametros = new Gson().toJson(_user);
        JSONObject jsonParam = new JSONObject(parametros);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_REGISTER_GOOGLE, jsonParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                getApiInfoUser(ctx, response.getJSONObject("data").getString("token"),logInListener);
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

    private void getApiInfoUser(final Activity ctx, String token, final ToolsApi.OnApiListenerError logInListener){
        JsonObjectRequest jsArrayRequest_2 = new JsonObjectRequest(Request.Method.GET, ToolsApi.URL_INFO_USER + token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                saveLocalUser(ctx, new Gson().fromJson(response.getJSONObject("data").toString(), User.class));
                                logInListener.onSuccessful();
                            }else{
                                logInListener.onError("Error de conexion");
                                Log.e("errorChefzin", "Error api token");
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

    //----------------------------------- Validation -----------------------------------------------
    private String validationLogin(String email, String password){
        if(email.equals(""))
            return "El campo correo electrónico esta vacío.";
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Correo electrónico invalido.";
        if(password.equals(""))
            return "El campo contraseña esta vacío.";
        if(password.length()<6)
            return "La contraseña debe tener mínimo 6 caracteres.";
        return null;
    }

    private String validationRegister(FormRegister formRegister){
        if(formRegister.getNombres().equals(""))
            return "El campo nombres esta vacío.";
        if(formRegister.getApellidos().equals(""))
            return "El campo apellidos esta vacío.";
        if(formRegister.getEmail().equals(""))
            return "El campo correo electrónico esta vacío.";
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(formRegister.getEmail()).matches())
            return "Correo electrónico invalido.";
        if(formRegister.getPassword().equals(""))
            return "El campo contraseña esta vacío.";
        if(formRegister.getPassword().length()<6)
            return "La contraseña debe tener mínimo 6 caracteres.";
        if(!formRegister.getPassword().equals(formRegister.getPassword_confirmation()))
            return "Las contraseñas no coinciden.";
        if(!formRegister.isCheckTerm())
            return "No has aceptado términos y condiciones.";
        return null;
    }

    private String validationRegisterFacebook(User _user){
        if(_user.getId_facebook() == null)
            return "Error de la aplicacion de facebook.";
        if(_user.getEmail() == null)
            return "Su cuenta de Facebook no tiene habilitado los permisos para acceder al correo.";
        if(_user.getNombres() == null)
            return "Su cuenta de Facebook no tiene habilitado los permisos para acceder al nombre.";
        if(_user.getApellidos() == null)
            return "Su cuenta de Facebook no tiene habilitado los permisos para acceder al apellido.";
        return null;
    }

    private String validationRegisterGoogle(User _user){
        if(_user.getId_google() == null)
            return "Error de la aplicacion de google.";
        if(_user.getEmail() == null)
            return "Su cuenta de Facebook no tiene habilitado los permisos para acceder al correo.";
        if(_user.getNombres() == null)
            return "Su cuenta de Facebook no tiene habilitado los permisos para acceder al nombre.";
        if(_user.getApellidos() == null)
            return "Su cuenta de Facebook no tiene habilitado los permisos para acceder al apellido.";
        return null;
    }

    //-------------------------------------- Save user local ---------------------------------------
    private void saveLocalUser(Activity ctx, User _user){
        this.user = _user;
        Gson gson = new Gson();
        String json = gson.toJson(user);

        SharedPreferences sharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", json);

        editor.commit();
    }

    private void restoreLocalUser(Activity ctx){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE);
        String savedSession = sharedPreferences.getString("session", null);

        if(savedSession != null)
            user = gson.fromJson(savedSession,User.class);
    }

    //----------------------------------------------------------------------------------------------

    public void apiRecord(final Activity ctx, final ToolsApi.OnApiListenerError logInListener){
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.GET, ToolsApi.URL_USER_RECORD + user.getApi_token(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                user.setOrderRecord((ArrayList<Order>) new Gson().fromJson(response.getJSONObject("data").getJSONArray("orden").toString(), new TypeToken<List<Order>>() {}.getType()));
                                logInListener.onSuccessful();
                            }else{
                                logInListener.onError("Error de conexion");
                                Log.e("errorChefzin", "Error api token");
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
        SingletonVolley.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    public void apiAddress(final Activity ctx, final ToolsApi.OnApiListenerError logInListener){
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.GET, ToolsApi.URL_ADDRESS_GET + user.getApi_token(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("response")){
                                user.setAddresses((ArrayList<Address>) new Gson().fromJson(response.getJSONArray("data").toString(), new TypeToken<List<Address>>() {}.getType()));
                                logInListener.onSuccessful();
                            }else{
                                logInListener.onError("Error de conexion");
                                Log.e("errorChefzin", "Error api token");
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
        SingletonVolley.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

}
