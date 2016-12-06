package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.SingletonVolley;
import com.future333.chefzin.model.Controller.CtrApp;
import com.future333.chefzin.model.Coordinate;
import com.future333.chefzin.tools.ToolsApi;
import com.future333.chefzin.tools.ToolsNotif;
import com.future333.chefzin.tools.ToolsPermissions;
import com.future333.chefzin.tools.ToolsView;
import com.future333.chefzin.view.TouchableWrapper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentMap extends Fragment {

    public static final String  NAME = "FragmentMap";

    View v;

    Activity    ctx;
    AppHandler  app;
    Fragment    ctxFrag;

    MapView         mapView;
    GoogleMap       map;
    TouchableWrapper            touchableWrapper;
    PlaceAutocompleteFragment   autocompleteFragment;

    ToolsNotif toolsNotif;

    RelativeLayout relative;
    Geocoder geocoder;
    ImageButton btnConfirm;
    boolean isPlaceSelect = false;

    String textCoordinates;

//    public static FragmentMap newInstance() {
//        return new FragmentMap();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx     = getActivity();
        ctxFrag = this;
        app     = ((AppHandler)getActivity().getApplication());

        toolsNotif = new ToolsNotif(ctx);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        v = inflater.inflate(R.layout.fragment_maps, container, false);

//        if (v != null) {
//            ViewGroup parent = (ViewGroup) v.getParent();
//            if (parent != null)
//                parent.removeView(v);
//        }

        v = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView                 = (MapView) v.findViewById(R.id.map);
        touchableWrapper        = (TouchableWrapper)v.findViewById(R.id.touchableWrapper);
//        autocompleteFragment    = (PlaceAutocompleteFragment) ToolsView.getMapFragment(ctxFrag).findFragmentById(R.id.autocomplete_fragment);
        relative =(RelativeLayout)v.findViewById(R.id.relative);
        btnConfirm = (ImageButton)v.findViewById(R.id.btnConfirm);





        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autocompleteFragment = new PlaceAutocompleteFragment();

//        FragmentManager fm = ToolsView.getMapFragment(ctxFrag);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.relative, autocompleteFragment);
        ft.commit();


        initializeMap(savedInstanceState);
        initializeAutocomplete();

        listen();
    }


    private void listen(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View vDialog = inflater.inflate(R.layout.dialog_address, null);
                TextView tvAddress = (TextView)vDialog.findViewById(R.id.tvAddress);
                final EditText etPhone = (EditText)vDialog.findViewById(R.id.etPhone);
                final EditText etIndications = (EditText)vDialog.findViewById(R.id.etIndications);
//                tvAddress.setText(textAddress);
//                tvAddress.setText(autocompleteFragment.getText(R.id.place_autocomplete_search_input).toString());
                EditText editText = (EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
                final String textAddress = editText.getText().toString();
                tvAddress.setText(textAddress);



                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setView(vDialog)
                        .setCancelable(false)
                        .setPositiveButton("Crear Direccion", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(etPhone.getText().toString().equals("")){
                                    ToolsView.msj(ctx, "Campo Telefono vacio");
                                    return;

                                }

                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("api_token",     app.ctrUser.getUser().getApi_token());
                                    jsonObject.put("telefono",      etPhone.getText().toString());
                                    jsonObject.put("coordenada",    textCoordinates);
                                    jsonObject.put("descripcion",   textAddress);
                                    if(!etIndications.getText().toString().equals(""))
                                        jsonObject.put("comentarios",   etIndications.getText().toString());

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ToolsApi.URL_ADDRESS_CREATE, jsonObject,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        if(response.getBoolean("response")){

                                                            com.future333.chefzin.model.Address address = new Gson().fromJson(response.getJSONObject("data").toString(), com.future333.chefzin.model.Address.class);
                                                            app.ctrUser.getUser().getAddresses().add(address);
                                                            app.ctrCart.setAddressSelect(address);

                                                            ctx.onBackPressed();

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
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------------- Map ---------------------------------------------------
    //----------------------------------------------------------------------------------------------


    private void initializeMap(Bundle savedInstanceState){
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                setCoverageAreas();

                setOnTextLocation();

                initializePositionCamera();

                if(ToolsPermissions.enabledPermissionsLocation(ctx,ctxFrag)){
                    enableMyLocation();
                }
            }
        });
    }

    private void setCoverageAreas(){
        // Instantiates a new Polygon object and adds points to define a rectangle
//        PolygonOptions rectOptions = new PolygonOptions()
//                .strokeColor(Color.RED)
//                .fillColor(R.color.colorPrimaryBlur)
//                .add(new LatLng(4.706559, -74.053683),
//                        new LatLng(4.686884, -74.056966),
//                        new LatLng(4.685077, -74.048054),
//                        new LatLng(4.679826, -74.038126),
//                        new LatLng(4.702046, -74.028577));
//        map.addPolygon(rectOptions);
        app.ctrApp.getCoordinates(ctx, new CtrApp.OnApiListenerError() {
            @Override
            public void onSuccessful(ArrayList<Coordinate> coordinates) {
                PolygonOptions rectOptions = new PolygonOptions()
                .strokeColor(Color.RED)
                .fillColor(R.color.colorPrimaryBlur);

                for(Coordinate coordinate: coordinates){
                    rectOptions.add(new LatLng(coordinate.getLatitud(),coordinate.getLongitud()));
                }
                map.addPolygon(rectOptions);
            }

            @Override
            public void onError(String error) {
                ToolsView.msj(ctx,error);
            }
        });
    }

    private void setOnTextLocation(){

        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = map.getCameraPosition();

                if(!isPlaceSelect){
                    try {
                        geocoder = new Geocoder(ctx, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            textCoordinates = "("+cameraPosition.target.latitude+","+cameraPosition.target.longitude+")";
                            autocompleteFragment.setText(address.getAddressLine(0));
                        }
                    } catch (IOException e) {}
                }

                isPlaceSelect = false;

            }
        });


    }

    private void enableMyLocation(){
        //noinspection MissingPermission
        map.setMyLocationEnabled(true);
        ToolsPermissions.enableLocation(ctx);
    }

    private void initializePositionCamera(){
        final LatLngBounds positionCam = new LatLngBounds(
                new LatLng(4.686768, -74.059861),
                new LatLng(4.702076, -74.028608));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(positionCam.getCenter(), 14.5F));
    }

    //----------------------------------------------------------------------------------------------
    //------------------------------------------ Autocomplete --------------------------------------
    //----------------------------------------------------------------------------------------------

    private void initializeAutocomplete(){
//        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(ToolsView.dpToPx(ctx,5));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                isPlaceSelect = true;
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 14.5F));
            }

            @Override
            public void onError(Status status) {

            }
        });
    }


    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case ToolsPermissions.MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //----------------------------------------------------------------------------------------------
//    public class DialogoPersonalizado extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//
//            builder.setView(inflater.inflate(R.layout.dialog_address, null))
//                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//
//            return builder.create();
//        }
//    }

}
