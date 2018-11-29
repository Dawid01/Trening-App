package com.szczepaniak.dawid.treningapp;

public class Workout {

    private int series;
    private float kgs;

    public Workout(int series, float kgs) {
        this.series = series;
        this.kgs = kgs;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public float getKgs() {
        return kgs;
    }

    public void setKgs(float kgs) {
        this.kgs = kgs;
    }
}
