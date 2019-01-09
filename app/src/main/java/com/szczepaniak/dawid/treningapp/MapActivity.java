package com.szczepaniak.dawid.treningapp;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "MapActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean locationPermissionsGranted = false;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeListner(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();


        if(isServices()){
            init();
        }
    }


//    private void getDeviceLocation() {
//
//        if (map != null) {
//
//
//            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//
//                @Override
//                public void onMyLocationChange(Location arg0) {
//                    // TODO Auto-generated method stub
//
//                    map.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("You"));
//                }
//            });
//
//        }
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        try {
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51, 17)));

    }

    private void init(){

        FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        getLocationPermission();
    }

    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
        //getDeviceLocation();

    }

    public  boolean isServices(){

        int avalible = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapActivity.this);

        if(avalible == ConnectionResult.SUCCESS){
            return  true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(avalible)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapActivity.this, avalible, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
        }
        return false;

    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationPermissionsGranted = true;
                initMap();
            }else {

                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionsGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{

                if(grantResults.length > 0){

                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            locationPermissionsGranted = false;
                            return;
                        }
                    }
                    locationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }


}
