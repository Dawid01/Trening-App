package com.szczepaniak.dawid.treningapp;

import java.util.ArrayList;

public class WorkoutDay {

    private String date;
    private ArrayList<Workout> workouts;

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
}
