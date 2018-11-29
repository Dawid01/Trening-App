package com.szczepaniak.dawid.treningapp;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class WorkoutDay {

    private String date;
    private ArrayList<Workout> workouts;
    private LinearLayout notesLayout;

    public WorkoutDay(String date) {
        this.date = date;

    }

    public String getDateText() {
        return date;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LinearLayout getNotesLayout() {
        return notesLayout;
    }

    public void setNotesLayout(LinearLayout notesLayout) {
        this.notesLayout = notesLayout;
    }


}
