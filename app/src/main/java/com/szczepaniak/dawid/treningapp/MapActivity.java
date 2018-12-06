package com.szczepaniak.dawid.treningapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MapActivity extends AppCompatActivity {

    private  static  final String TAG = "MapActivity";
    private  static  final  int ERROR_DIALOG_REQUEST =  9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(isServices()){
            init();
        }
    }


    private  void  init(){


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
}
