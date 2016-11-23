package com.future333.chefzin.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentMap extends Fragment {

    Activity    ctx;
    AppHandler  app;
    Fragment    ctxFrag;

    MapView         mapView;
    GoogleMap       map;
    TouchableWrapper            touchableWrapper;
    PlaceAutocompleteFragment   autocompleteFragment;

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

        mapView                 = (MapView) v.findViewById(R.id.map);
        touchableWrapper        = (TouchableWrapper)v.findViewById(R.id.touchableWrapper);
        autocompleteFragment    = (PlaceAutocompleteFragment) ToolsView.getMapFragment(ctxFrag).findFragmentById(R.id.autocomplete_fragment);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeMap(savedInstanceState);
        initializeAutocomplete();
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
        PolygonOptions rectOptions = new PolygonOptions()
                .strokeColor(Color.RED)
                .fillColor(R.color.colorPrimaryBlur)
                .add(new LatLng(4.706559, -74.053683),
                        new LatLng(4.686884, -74.056966),
                        new LatLng(4.685077, -74.048054),
                        new LatLng(4.679826, -74.038126),
                        new LatLng(4.702046, -74.028577));
        map.addPolygon(rectOptions);
    }

    private void setOnTextLocation(){
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                CameraPosition cameraPosition = map.getCameraPosition();

                if(!touchableWrapper.ismMapIsTouched()){
                    try {
                        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);
                        StringBuilder sb = new StringBuilder();
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            autocompleteFragment.setText(address.getAddressLine(0));
                        }
                    } catch (IOException e) {}
                }
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
        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(ToolsView.dpToPx(ctx,5));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
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

}
