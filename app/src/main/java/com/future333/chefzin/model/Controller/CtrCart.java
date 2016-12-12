package com.future333.chefzin.model.Controller;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Address;
import com.future333.chefzin.model.Ingredient;
import com.future333.chefzin.model.Price;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsFormat;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by manuel on 11/10/16.
 */
public class CtrCart {


    private Price price;

    private Address addressSelect;

    private String id_orden;
    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getId_orden() {
        return id_orden;
    }

    //----------------------------------------------------------------------------------------------


    public Address getAddressSelect() {
        return addressSelect;
    }

    public void setAddressSelect(Address addressSelect) {
        this.addressSelect = addressSelect;
    }

    public void addProduct(Activity ctx, AppHandler app, Product product, ToolsApi.OnApiListenerError apiListener){

        if(products.size() == 0)
            apiOrderCreate(ctx, app, product, apiListener);
        else
            apiOrderProductAdd(ctx, app, product, apiListener);
    }

    public void deleteProduct(Activity ctx, AppHandler app, Product product, ToolsApi.OnApiListenerError apiListener){
        apiOrderProductDelete(ctx, app, product, apiListener);
    }

    public int quantityProducts(){
        return products.size();
    }

    public void clear(){
        products.clear();
        id_orden    = null;
        addressSelect = null;
    }

    //----------------------------------------------------------------------------------------------
    private int priceProductsIngredients(){
        int total = 0;
        for(Product product:products){
            total = total + product.getPrecio();
            if(product.getIngredientes() != null)
                for(Ingredient ingredient:product.getIngredientes()){
                    total = total + ingredient.getPrecio();
                }
        }
        return total;
    }

    //----------------------------------------------------------------------------------------------
    public String getSubTotal(){
//        return ToolsFormat.int_to_price(priceProductsIngredients());
        return ToolsFormat.int_to_price(price.getSubtotal());
    }

    public String getIva(){
//        return ToolsFormat.int_to_price(priceIva());
        return ToolsFormat.int_to_price(price.getImpuesto());
    }

    public String getDomicile(){
//        return ToolsFormat.int_to_price(priceDomicile);
        return ToolsFormat.int_to_price(price.getDomicilio());
    }

    public String getTotal(){
//        return ToolsFormat.int_to_price( priceProductsIngredients() + priceIva() + priceDomicile);
        return ToolsFormat.int_to_price(price.getTotal());
    }

    //----------------------------------------- methods Api ----------------------------------------
    public void apiOrderCreate(final Activity ctx, AppHandler app, final Product product, final ToolsApi.OnApiListenerError apiListener){

        try {
            JSONObject jsonObject = createJsonAddProduct(app, product);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ORDEN_CREATE, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("response")){
                                    product.setId_orden_plato(response.getJSONObject("data").getString("id_orden_plato"));
                                    products.add(product);
                                    id_orden = response.getJSONObject("data").getString("id_orden");
                                    price = new Gson().fromJson(response.getJSONObject("data").toString(), Price.class);
                                    apiListener.onSuccessful();
                                }else {
                                    apiListener.onError(response.getJSONObject("mensaje").getString("error"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("responseLog", error.toString());
                    apiListener.onError("Error de conexión.");
                }
            });

            SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void apiOrderProductAdd(final Activity ctx, AppHandler app, final Product product, final ToolsApi.OnApiListenerError apiListener){

        try {

            JSONObject jsonObject = createJsonAddProduct(app, product);
            jsonObject.put("id_orden", id_orden);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ORDEN_PRODUCT_ADD, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("response")){
                                    product.setId_orden_plato(response.getJSONObject("data").getString("id_orden_plato"));
                                    products.add(product);
                                    price = new Gson().fromJson(response.getJSONObject("data").toString(), Price.class);
                                    apiListener.onSuccessful();
                                }else {
                                    apiListener.onError(response.getJSONObject("mensaje").getString("error"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("responseLog", error.toString());
                    apiListener.onError("Error de conexión.");
                }
            });

            SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void apiOrderProductDelete(final Activity ctx, AppHandler app, final Product product, final ToolsApi.OnApiListenerError apiListener){

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("api_token",         app.ctrUser.getUser().getApi_token());
            jsonObject.put("id_orden_plato",    product.getId_orden_plato());
            jsonObject.put("id_orden_plato",    product.getId_orden_plato());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ORDEN_PRODUCT_DELETE, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("response")){
                                    products.remove(product);
                                    price = new Gson().fromJson(response.getJSONObject("data").toString(), Price.class);
                                    apiListener.onSuccessful();
                                }else {
                                    apiListener.onError(response.getJSONObject("mensaje").getString("error"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("responseLog", error.toString());
                    apiListener.onError("Error de conexión.");
                }
            });

            SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void apiOrderCheckout(final Activity ctx, AppHandler app, final Product product, final ToolsApi.OnApiListenerError apiListener){
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_orden",      id_orden);
            jsonObject.put("id_direccion",  id_orden);
            jsonObject.put("id_metodo_pago",id_orden);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ORDEN_CHECKOUT, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("response")){

                                    String id_direccion = response.getJSONObject("data").getString("id");


                                }else {
                                    apiListener.onError(response.getJSONObject("mensaje").getString("error"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("responseLog", error.toString());
                    apiListener.onError("Error de conexión.");
                }
            });

            SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private JSONObject createJsonAddProduct(AppHandler app, Product product) throws JSONException {
        JSONArray arrayIngredient = new JSONArray();
        if(product.getIngredientes() != null){
            for(Ingredient ingredient: product.getIngredientes()){
                arrayIngredient.put(new JSONObject().put("id_ingrediente",ingredient.getId_ingrediente()));
            }
        }

        JSONObject jsonProduct = new JSONObject();
        jsonProduct.put("id_plato", product.getId_plato());
        jsonProduct.put("ingredientes", arrayIngredient);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api_token", app.ctrUser.getUser().getApi_token());
        jsonObject.put("id_chef",   app.chefSelect.getId_chef());
        jsonObject.put("productos", new JSONArray().put(jsonProduct));

        return jsonObject;
    }

}
