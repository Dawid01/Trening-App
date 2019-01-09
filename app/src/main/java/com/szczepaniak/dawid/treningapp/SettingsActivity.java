package com.szczepaniak.dawid.treningapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ImageView blueBtm;
    private ImageView redBtm;
    private ImageView pinkBtm;
    private ImageView purpleBtm;
    private ImageView indiegoBtm;
    private ImageView tealBtm;
    private ImageView greenBtm;
    private ImageView yellowBtm;
    private ImageView orangeBtm;
    private ImageView brownBtm;
    private ImageView greyBtm;
    private ImageView blackBtm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeListner(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        blueBtm = findViewById(R.id.blue);
        redBtm = findViewById(R.id.red);
        pinkBtm = findViewById(R.id.pink);
        purpleBtm = findViewById(R.id.purple);
        indiegoBtm = findViewById(R.id.indigo);
        tealBtm = findViewById(R.id.teal);
        greenBtm = findViewById(R.id.green);
        yellowBtm = findViewById(R.id.yellow);
        orangeBtm = findViewById(R.id.orange);
        brownBtm = findViewById(R.id.brown);
        greyBtm = findViewById(R.id.grey);
        blackBtm = findViewById(R.id.black);

        ArrayList<ImageView> btms =  new ArrayList<>();

        btms.add(orangeBtm);
        btms.add(blueBtm);
        btms.add(redBtm);
        btms.add(pinkBtm);
        btms.add(purpleBtm);
        btms.add(indiegoBtm);
        btms.add(tealBtm);
        btms.add(greenBtm);
        btms.add(yellowBtm);
        btms.add(brownBtm);
        btms.add(greyBtm);
        btms.add(blackBtm);


        for(int i = 0; i < btms.size(); i++){

            ImageView view =  btms.get(i);
            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //AppCompatDelegate.setDefaultNightMode(index);
                    Singleton singleton = Singleton.getInstance();
                    singleton.setTheme(index);
                    restartApp();
                }
            });
        }

//        for (ImageView view : btms){
//
//            final int i = btms.indexOf(view);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AppCompatDelegate.setDefaultNightMode(i);
//                    restartApp();
//                }
//            });
//        }
    }

    void restartApp(){

        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
        return;
    }
}
