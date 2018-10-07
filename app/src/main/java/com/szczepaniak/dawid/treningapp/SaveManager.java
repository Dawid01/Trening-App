package com.szczepaniak.dawid.treningapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SaveManager {


    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public SaveManager(Context context) {

        sharedPref = context.getSharedPreferences("WorkoutDaysList", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

    }

    public ArrayList<WorkoutDay> getWorkoutDays(String key){

        String savedDays = sharedPref.getString("user_id","");
        ArrayList<WorkoutDay> workoutDays = new ArrayList<>();
        return workoutDays;
    }

    public void SaveWorkoutDaysList(String key, ArrayList<WorkoutDay> workoutDays){

        String savedDays = workoutDays.toString();
        editor.putString(key, savedDays);
        editor.commit();

    }
}
