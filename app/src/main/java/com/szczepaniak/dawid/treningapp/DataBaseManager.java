package com.szczepaniak.dawid.treningapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager extends SQLiteOpenHelper {


    public DataBaseManager(Context context) {
        super(context, "WorkoutsDataBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table workouts(" +
                "number intager primary key autoincrement," +
                "name text," +
                "class text)" +
                "");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addWorkout(){

    }
}
