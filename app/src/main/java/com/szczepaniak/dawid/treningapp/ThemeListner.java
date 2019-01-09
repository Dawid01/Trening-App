package com.szczepaniak.dawid.treningapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;

public class ThemeListner {

    private Activity activity;
    private SharedPreferences pereferences;

    public ThemeListner(Activity activity) {

        this.activity = activity;

//        pereferences = activity.getPreferences(activity.MODE_PRIVATE);
//
//        String theme = pereferences.getString("Theme", "Orange");
//


        //int themeMode = AppCompatDelegate.getDefaultNightMode();
        Singleton singleton = Singleton.getInstance();
        int themeMode = singleton.getTheme();

        switch (themeMode){

            case 0:
                activity.setTheme(R.style.AppTheme);
                break;
            case 1:
                activity.setTheme(R.style.Blue);
                break;
            case 2:
                activity.setTheme(R.style.Red);
                break;
            case 3:
                activity.setTheme(R.style.Pink);
                break;
            case 4:
                activity.setTheme(R.style.Purple);
                break;
            case 5:
                activity.setTheme(R.style.Indigo);
                break;
            case 6:
                activity.setTheme(R.style.Teal);
                break;
            case 7:
                activity.setTheme(R.style.Green);
                break;
            case 8:
                activity.setTheme(R.style.Yellow);
                break;
            case 9:
                activity.setTheme(R.style.Brown);
                break;
            case 10:
                activity.setTheme(R.style.Gray);
                break;
            case 11:
                activity.setTheme(R.style.Black);
                break;
        }

    }

    public int getThemeColor() {

        int color = Color.WHITE;

        Singleton singleton = Singleton.getInstance();
        int themeMode = singleton.getTheme();

        switch (themeMode) {

            case 0:
                color = activity.getResources().getColor(R.color.colorPrimary);
                break;
            case 1:
                color = activity.getResources().getColor(R.color.blue);
                break;
            case 2:
                color = activity.getResources().getColor(R.color.red);
                break;
            case 3:
                color = activity.getResources().getColor(R.color.pink);
                break;
            case 4:
                color = activity.getResources().getColor(R.color.purple);
                break;
            case 5:
                color = activity.getResources().getColor(R.color.indigo);
                break;
            case 6:
                color = activity.getResources().getColor(R.color.teal);
                break;
            case 7:
                color = activity.getResources().getColor(R.color.green);
                break;
            case 8:
                color = activity.getResources().getColor(R.color.yellow);
                break;
            case 9:
                color = activity.getResources().getColor(R.color.brown);
                break;
            case 10:
                color = activity.getResources().getColor(R.color.gray);
                break;
            case 11:
                color = activity.getResources().getColor(R.color.black);
                break;

        }

        return color;
    }

}
