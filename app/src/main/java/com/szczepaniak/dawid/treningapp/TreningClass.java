package com.szczepaniak.dawid.treningapp;

import android.graphics.drawable.Drawable;

public class TreningClass {

    private String type;
    private String name;
    private String description;
    private Drawable icon;

    public TreningClass(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
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
}
