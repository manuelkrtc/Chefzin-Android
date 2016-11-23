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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.future333.chefzin.AppHandler;
import com.future333.chefzin.R;
import com.future333.chefzin.tools.ToolsPermissions;
import com.future333.chefzin.tools.ToolsView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by manuel on 12/09/16.
 */
public class FragmentMap extends Fragment {

    Activity    ctx;
    AppHandler  app;
    Fragment    ctxFrag;

    MapView mapView;
    GoogleMap map;


//    PlaceAutocompleteFragment autocompleteFragment;
    Marker now;

    private GoogleApiClient mGoogleApiClient;

//    Button      button;
//    EditText    editText;


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


            PlaceAutocompleteFragment autocompleteFragment  = (PlaceAutocompleteFragment) getMapFragment().findFragmentById(R.id.autocomplete_fragment);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                                                                     //            @Override
            public void onPlaceSelected(Place place) {

//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 14.5F));

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 14.5F));
                System.out.println(place.getAddress());
            }

            @Override
            public void onError(Status status) {
            }
        });

            int x =10;
            x = 20;

//        }



//        SupportPlaceAutocompleteFragment placeAutocompleteFragment = (SupportPlaceAutocompleteFragment) getActivity().getFragmentManager().
//                findFragmentById(R.id.autocomplete_fragment);
//
//        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                System.out.println(place.getAddress());
//            }
//
//            @Override
//            public void onError(Status status) {
//            }
//        });


//        autocompleteFragment  = (PlaceAutocompleteFragment)ctx.getFragmentManager().findFragmentById(R.id.autocomplete_fragment);



//        autocompleteFragment = (PlaceAutocompleteFragment) ctxFrag.getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//        autocompleteFragment = (PlaceAutocompleteFragment) v.findViewById(R.id.autocomplete_fragment);






//        autocompleteFragment.setHint("Search a Location");
//        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);




//        button      = (Button)      v.findViewById(R.id.button);
//        editText    = (EditText)    v.findViewById(R.id.editText);




        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializateMap(savedInstanceState);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                final String string = editText.getText().toString();
//
//                final LatLngBounds positionCam = new LatLngBounds(
//                        new LatLng(4.686768, -74.059861),
//                        new LatLng(4.702076, -74.028608)
//                );
//
////                PendingResult result = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, string, null, null);
//
//                Query query = new Query.Builder().build();
//
////                PendingResult<AutocompletePredictionBuffer> autocompletePredictions =
//
//                Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, string, null, null).setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
//                    @Override
//                    public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {
//
////                        AutocompletePrediction autocompletePrediction = autocompletePredictions.;
//
////                        String vcasa = autocompletePrediction.getMa();
//
//                        int x = 10;
//                        x=20;
//                    }
//                });
//
//
//            }
//        });




//        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
//                .enableAutoManage(this, 0 /* clientId */, this)
//                .addApi(Places.GEO_DATA_API)
//                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(ctx)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .build();















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





//        autocompleteFragment.setOnPlaceSelectedListener(this);
//        autocompleteFragment.setHint("Search a Location");
//        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);

    }

    public void secondConfMap(){
        //noinspection MissingPermission
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

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

//                Geocoder geocoder;
//                List<Address> addresses;
//                geocoder = new Geocoder(ctx, Locale.getDefault());


//                String addressString;
//
//                try {
//                    Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
//                    List<Address> addresses = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);
//                    StringBuilder sb = new StringBuilder();
//                    if (addresses.size() > 0) {
//                        Address address = addresses.get(0);
//
//                        sb.append(address.getLocality()).append("\n");
//                        sb.append(address.getCountryName());
//                    }
//
//                    addressString = sb.toString();
//
//                    Log.e("Address from lat,long ;", addressString);
//                } catch (IOException e) {}


//                try {
//                    addresses = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude , 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//                    if(addresses.size() > 0){
//                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                        String city = addresses.get(0).getLocality().;
//                        String state = addresses.get(0).getAdminArea();
//                        String country = addresses.get(0).getCountryName();
//                        String postalCode = addresses.get(0).getPostalCode();
//                        String knownName = addresses.get(0).getFeatureName();
//
//                        Log.d("Datos", address + " _ " + city);
//                    }
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


            }
        });

    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void onPickButtonClick(View v) {
        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(ctx);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, 111);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == 111
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, ctx);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }

//            mViewName.setText(name);
//            mViewAddress.setText(address);
//            mViewAttributions.setText(Html.fromHtml(attributions));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
















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
        mGoogleApiClient.connect();

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
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }






    private FragmentManager getMapFragment() {

        FragmentManager fm = getFragmentManager();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
            fm = getChildFragmentManager();

        return fm;
    }



}
