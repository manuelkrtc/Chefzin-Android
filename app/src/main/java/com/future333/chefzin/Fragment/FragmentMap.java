package com.future333.chefzin.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
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

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                map.setMyLocationEnabled(true);

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
