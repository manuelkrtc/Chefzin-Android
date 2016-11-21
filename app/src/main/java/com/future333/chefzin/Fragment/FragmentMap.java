package com.future333.chefzin.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.tools.ToolsPermissions;
import com.future333.chefzin.tools.ToolsView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentMap extends Fragment {

    Activity    ctx;
    AppHandler  app;
    Fragment    ctxFrag;

    MapView mapView;
    GoogleMap map;

    Marker now;

    public static FragmentMap newInstance() {
        return new FragmentMap();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx     = getActivity();
        ctxFrag = this;
        app     = ((AppHandler)getActivity().getApplication());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView = (MapView) v.findViewById(R.id.map);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializateMap(savedInstanceState);
    }

    private void inicializateMap(Bundle savedInstanceState){
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                if(ToolsPermissions.enabledPermissionsLocation(ctx,ctxFrag)){
                    secondConfMap();
                }
            }
        });
    }

    public void secondConfMap(){
        map.setMyLocationEnabled(true);
        ToolsPermissions.enableLocation(ctx);

        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptions = new PolygonOptions()
                .strokeColor(Color.RED)
                .fillColor(R.color.colorPrimaryBlur)
                .add(new LatLng(4.706559, -74.053683),
                        new LatLng(4.686884, -74.056966),
                        new LatLng(4.685077, -74.048054),
                        new LatLng(4.679826, -74.038126),
                        new LatLng(4.702046, -74.028577));

        // Get back the mutable Polygon
        map.addPolygon(rectOptions);


        final LatLngBounds positionCam = new LatLngBounds(
                new LatLng(4.686768, -74.059861),
                new LatLng(4.702076, -74.028608)
        );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(positionCam.getCenter(), 14.5F));


    }





    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case ToolsPermissions.MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    secondConfMap();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
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
}
