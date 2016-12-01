package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.MainActivity;
import com.future333.chefzin.R;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Address;
import com.future333.chefzin.model.Controller.CtrCart;
import com.future333.chefzin.model.Ingredient;
import com.future333.chefzin.model.Product;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsFormat;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.tools.ToolsView;
import com.future333.chefzin.view.CheckProductPrice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentCheckout extends Fragment {

    public static final String  NAME = "FragmentCheckout";


    Activity    ctx;
    AppHandler  app;
    ToolsNotif  toolsNotif;

    RecyclerView rvProduct;
    ViewPager    viewPager;

    CheckoutPageAdapter     checkoutAdapter;
    RecyclerView.Adapter    adapaterRecicler;

    Button viewController1;
    Button viewController2;
    Button viewController3;

    public static FragmentCheckout newInstance() {
        return new FragmentCheckout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

        toolsNotif = new ToolsNotif(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_checkout, container, false);

        rvProduct   = (RecyclerView)v.findViewById(R.id.rvProduct);
        viewPager   = (ViewPager)v.findViewById(R.id.viewPager);

        viewController1 = (Button) v.findViewById(R.id.viewController1);
        viewController2 = (Button) v.findViewById(R.id.viewController2);
        viewController3 = (Button) v.findViewById(R.id.viewController3);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ViewPager
        checkoutAdapter = new CheckoutPageAdapter(ctx, app);
        viewPager.setAdapter(checkoutAdapter);

        //RecyclerView
        rvProduct.setHasFixedSize(true);
        adapaterRecicler = new AdaptadorProducts(ctx, app, checkoutAdapter);
        rvProduct.setAdapter(adapaterRecicler);
        rvProduct.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateViewController(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        checkoutAdapter.notifyDataSetChanged();
    }

    private void updateViewController(int position){

        viewController1.setBackgroundResource(R.drawable.shape_circle_stroke_blue);
        viewController2.setBackgroundResource(R.drawable.shape_circle_stroke_blue);
        viewController3.setBackgroundResource(R.drawable.shape_circle_stroke_blue);

        switch (position){
            case 0: viewController1.setBackgroundResource(R.drawable.shape_circle_blue);
                break;
            case 1: viewController2.setBackgroundResource(R.drawable.shape_circle_blue);
                break;
            case 2: viewController3.setBackgroundResource(R.drawable.shape_circle_blue);
                break;
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------- Adapter --------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static class AdaptadorProducts extends RecyclerView.Adapter<AdaptadorProducts.ProductsViewHolder> {

        private Activity    ctxAdap;
        private AppHandler  app;
        private ArrayList<Product> products;
        private AdaptadorProducts thisAdatpter;
        private CheckoutPageAdapter checkoutAdapter;

        public AdaptadorProducts(Activity ctx, AppHandler app, CheckoutPageAdapter checkoutAdapter) {
            this.ctxAdap    = ctx;
            this.app        = app;
            this.products   = app.ctrCart.getProducts();
            this.thisAdatpter = this;
            this.checkoutAdapter = checkoutAdapter;
        }

        @Override
        public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_checkout, parent, false);
            ProductsViewHolder productsViewHolder = new ProductsViewHolder(itemView);
            return productsViewHolder;
        }

        @Override
        public void onBindViewHolder(ProductsViewHolder holder, int position) {
            final Product product = products.get(position);

            holder.tvName.setText(product.getNombre());
            holder.tvPrice.setText(ToolsFormat.int_to_price(product.getPrecio()));

            holder.lyAdditions.removeAllViews();
            for(Ingredient ingredient: product.getIngredientes()){
                CheckProductPrice viewIngredient = new CheckProductPrice(ctxAdap, ingredient);
                viewIngredient.setName(" - " + ingredient.getNombre());
                viewIngredient.getCheck().setVisibility(View.GONE);
                viewIngredient.setPadding(0,0, ToolsView.dpToPx(ctxAdap,20),0);

                holder.lyAdditions.addView(viewIngredient);
            }

            if(product.getIngredientes().size()==0) holder.zoneAdditions.setVisibility(View.GONE);

            holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppHandler)ctxAdap.getApplication()).ctrCart.deleteProduct(ctxAdap, app, product, new ToolsApi.OnApiListenerError() {
                        @Override
                        public void onSuccessful() {
                            thisAdatpter.notifyDataSetChanged();
                            checkoutAdapter.updatePriceView();
                        }

                        @Override
                        public void onError(String error) {
                            ToolsView.msj(ctxAdap, error);
                        }
                    });

                    thisAdatpter.notifyDataSetChanged();
                    checkoutAdapter.updatePriceView();

                    if(app.ctrCart.quantityProducts()==0) ctxAdap.onBackPressed();
                }
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public static class ProductsViewHolder extends RecyclerView.ViewHolder {

            private TextView tvName;
            private TextView tvPrice;

            private ViewGroup   lyAdditions;
            private ViewGroup   zoneAdditions;
            private ImageButton btnDeleteProduct;

            public ProductsViewHolder(View itemView) {
                super(itemView);

                tvName              = (TextView)    itemView.findViewById(R.id.tvName);
                tvPrice             = (TextView)    itemView.findViewById(R.id.tvPrice);
                lyAdditions         = (ViewGroup)   itemView.findViewById(R.id.lyAdditions);
                zoneAdditions       = (ViewGroup)   itemView.findViewById(R.id.zoneAdditions);
                btnDeleteProduct    = (ImageButton) itemView.findViewById(R.id.btnDeleteProduct);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------- Adapter --------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static class CheckoutPageAdapter extends PagerAdapter{

        Activity ctx;
        AppHandler app;

        View dataView   = null;
        View priceView  = null;
        View paymentView= null;

        CtrCart _shopCart;

        TextView tvIva;
        TextView tvTotal;
        TextView tvDomicile;
        TextView tvSubtotal;

        EditText    etDocument;
        TextView    tvPhone;
        TextView    tvAddress;
        TextView    tvIndications;
        ImageButton btnOpenMap;

        public CheckoutPageAdapter(Activity ctx, AppHandler app){
            this.ctx = ctx;
            this.app = app;
            this._shopCart = app.ctrCart;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(position==0 && priceView==null){
                priceView = LayoutInflater.from(ctx).inflate(R.layout.row_checkout_price, container,false);
                updatePriceView();
                container.addView(priceView);
                return priceView;
            }

            if(position==1 && dataView==null){
                dataView = LayoutInflater.from(ctx).inflate(R.layout.row_checkout_data, container,false);
                setDataView();
                container.addView(dataView);
                return dataView;
            }

            if(position==2 && paymentView==null){
                paymentView = LayoutInflater.from(ctx).inflate(R.layout.row_checkout_payment, container,false);
                container.addView(paymentView);
                updatePaymentView();
                return paymentView;
            }

            return null;
        }

        // Removes the page from the container for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
        }

        public void updatePriceView(){
            tvIva      = (TextView)priceView.findViewById(R.id.tvIva);
            tvTotal    = (TextView)priceView.findViewById(R.id.tvTotal);
            tvDomicile = (TextView)priceView.findViewById(R.id.tvDomicile);
            tvSubtotal = (TextView)priceView.findViewById(R.id.tvSubtotal);

            tvIva       .setText(_shopCart.getIva());
            tvTotal     .setText(_shopCart.getTotal());
            tvDomicile  .setText(_shopCart.getDomicile());
            tvSubtotal  .setText(_shopCart.getSubTotal());
        }

        public void updatePaymentView(){
            Button  btnConfirm  = (Button) paymentView.findViewById(R.id.btnConfirm);
            Spinner spinner     = (Spinner) paymentView.findViewById(R.id.spinner);

            ArrayAdapter<CharSequence> adapter =
                    ArrayAdapter.createFromResource(ctx,
                            R.array.payment_method,
                            android.R.layout.simple_list_item_1);

            spinner.setAdapter(adapter);


            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {


                        if(etDocument.getText().toString().equals("")){
                            ToolsView.msj(ctx,"El campo cedula esta vacio");
                            return;
                        }

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("api_token",     app.ctrUser.getUser().getApi_token());
//                        jsonObject.put("telefono",      etPhone.getText().toString());
//                        jsonObject.put("coordenada",    app.ctrCart.getCoordinates());
                        jsonObject.put("descripcion",   app.ctrCart.getAddressSelect());
//                        if(!etIndications.getText().toString().equals(""))
//                            jsonObject.put("comentarios",   etIndications.getText().toString());

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ADDRESS_CREATE, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if(response.getBoolean("response")){

                                                String id_direccion = response.getJSONObject("data").getString("id");


                                                try {

                                                    JSONObject jsonObject2 = new JSONObject();
                                                    jsonObject2.put("api_token",     app.ctrUser.getUser().getApi_token());
                                                    jsonObject2.put("id_orden",      app.ctrCart.getId_orden());
                                                    jsonObject2.put("id_direccion",  id_direccion);
                                                    jsonObject2.put("id_metodo_pago","1");

                                                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ORDEN_CHECKOUT, jsonObject2,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    try {
                                                                        if(response.getBoolean("response")){

                                                                            ToolsView.msj(ctx,"Pedido exitoso.");
                                                                            ((MainActivity)ctx).homeFragment();

                                                                        }else {
                                                                            ToolsView.msj(ctx,"Error de conexión.");
                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.i("responseLog", error.toString());
                                                            ToolsView.msj(ctx,"Error de conexión.");
                                                        }
                                                    });

                                                    SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest2);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }else {

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("responseLog", error.toString());
                                ToolsView.msj(ctx,error.toString());
                            }
                        });

                        SingletonVolley.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void setDataView(){
            etDocument      = (EditText)    dataView.findViewById(R.id.etDocument);
            tvPhone         = (TextView)    dataView.findViewById(R.id.tvPhone);
            tvAddress       = (TextView)    dataView.findViewById(R.id.tvAddress);
            tvIndications   = (TextView)    dataView.findViewById(R.id.tvIndications);
            btnOpenMap      = (ImageButton) dataView.findViewById(R.id.btnOpenMap);

            etDocument.setText(app.ctrUser.getUser().getDocumento());

            btnOpenMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)ctx).goFragmentMap();
                }
            });

            ctx.getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    Address address = app.ctrCart.getAddressSelect();
                    if(address != null)
                        paintViewAddress(address);
                }
            });

            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final ArrayList<Address> addresses = app.ctrUser.getUser().getAddresses();
                    String[] items = new String[addresses.size()];

                    for (int i=0; i<addresses.size(); i++){
                        items[i] = addresses.get(i).getDescripcion();
                    }



                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setTitle("Direccion")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    Address address = addresses.get(item);
                                    paintViewAddress(address);
                                    app.ctrCart.setAddressSelect(address);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            });
        }

        private void paintViewAddress(Address address){
            tvAddress.setText(address.getDescripcion());
            tvPhone.setText(address.getTelefono());
            tvIndications.setText(address.getComentarios());
        }

    }


}
