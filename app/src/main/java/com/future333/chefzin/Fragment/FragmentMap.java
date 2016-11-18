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
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.tools.ToolsPermissions;
import com.future333.chefzin.tools.ToolsView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentMap extends Fragment {

    Activity    ctx;
    AppHandler  app;

    MapView mapView;
    GoogleMap map;

   static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    public static FragmentMap newInstance() {
        return new FragmentMap();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
        app = ((AppHandler)getActivity().getApplication());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps, container, false);


        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);












//        LocationManager lm = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
//        boolean gps_enabled = false;
//        boolean network_enabled = false;
//
//        try {
//            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch(Exception ex) {}
//
//        if(!gps_enabled && !network_enabled) {
//            // notify user
//            AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
//            dialog.setMessage("gps_network_not_enabled");
//            dialog.setPositiveButton("open_location_settings", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                    // TODO Auto-generated method stub
//                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    ctx.startActivity(myIntent);
//                    //get gps
//                }
//            });
//            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                    // TODO Auto-generated method stub
//
//                }
//            });
//            dialog.show();
//        }


















        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;


                ToolsPermissions.checkPermissionsLocation(ctx);

//                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                        ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_FINE_LOCATION);
//
//                    } else {
//
//                        ToolsView.msj(ctx,"La cago tiene q activar esta mierda manualkmenet");
////                        ActivityCompat.requestPermissions(ctx,
////                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
////                                MY_PERMISSIONS_REQUEST_FINE_LOCATION);
//
//                    }
//                }else{
//                    map.setMyLocationEnabled(true);
//                }

//                if (ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                    } else {
//
//                        ActivityCompat.requestPermissions(this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//
//                    }
//                }












//                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                map.setMyLocationEnabled(true);

            }
        });


        // Gets to GoogleMap from the MapView and does initialization stuff
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
////                map = googleMap;
//
////                map.getUiSettings().setMyLocationButtonEnabled(true);
////                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////                    // TODO: Consider calling
////                    //    ActivityCompat#requestPermissions
////                    // here to request the missing permissions, and then overriding
////                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                    //                                          int[] grantResults)
////                    // to handle the case where the user grants the permission. See the documentation
////                    // for ActivityCompat#requestPermissions for more details.
////                    return;
////                }
////                map.setMyLocationEnabled(true);
//
//
////                MapsInitializer.initialize(getActivity());
//
//                // Updates the location and zoom of the MapView
////                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
////                map.animateCamera(cameraUpdate);
//            }
//        });




        return v;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToolsView.msj(ctx,"Cago1");
                } else {
                    ToolsView.msj(ctx,"cago2");
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

    private MapFragment getMapFragment() {
        FragmentManager fm = null;

//        Log.d("sdk: ",  Build.VERSION.SDK_INT);
//        Log.d("release: ",  Build.VERSION.RELEASE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Log.d(TAG, "using getFragmentManager");
            fm = getFragmentManager();
        } else {
//            Log.d(TAG, "using getChildFragmentManager");
            fm = getChildFragmentManager();
        }

        return (MapFragment) fm.findFragmentById(R.id.map);
    }
}
