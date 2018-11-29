package com.szczepaniak.dawid.treningapp;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class TreningClass {

    private String type;
    private String name;
    private String description;
    private Drawable icon;
    private ArrayList<WorkoutDay> workoutDays;

    public TreningClass(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;

        workoutDays =  new ArrayList<>();
        WorkoutDay testWokroutDay = new WorkoutDay("dd.mm.rrrr");
        ArrayList<Workout> workouts =  new ArrayList<Workout>();
        workouts.add(new Workout(10, 80));
        workouts.add(new Workout(10, 80));
        workouts.add(new Workout(10, 80));
        workouts.add(new Workout(10, 80));
        testWokroutDay.setWorkouts(workouts);
        workoutDays.add(testWokroutDay);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Drawable getIcon() {
        return icon;
    }

    public ArrayList<WorkoutDay> getWorkoutDays() {
        return workoutDays;
    }

    public void setWorkoutDays(ArrayList<WorkoutDay> workoutDays) {
        this.workoutDays = workoutDays;
    }
}
