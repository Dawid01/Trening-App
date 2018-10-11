package com.szczepaniak.dawid.treningapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorkoutActivity extends AppCompatActivity {


    private Dialog dialog;
    private LinearLayout workoutDays;
    public int savedSeriesValue = 0;
    public float savedKgValue = 0f;

    private SaveManager saveManager;
    private ShowNotePopup showNotePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        workoutDays =  findViewById(R.id.WorkoutDays);
        saveManager = new SaveManager(this);
        dialog =  new Dialog(this);
        showNotePopup =  new ShowNotePopup(WorkoutActivity.this, dialog);

        if(extras != null) {

            getSupportActionBar().setTitle(extras.getString("TreningName"));
        }

        LoadWorkouts();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CreateDayList(String date){

        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.workoutday, null, false);
        FloatingActionButton addWork = layout.findViewById(R.id.AddWork);
        TextView dateText = layout.findViewById(R.id.Date);
        TextView indexText = layout.findViewById(R.id.Index);
        int number = workoutDays.getChildCount() + 1;
        indexText.setText(String.valueOf(number));
        dateText.setText(date);
        final LinearLayout notesLayout = layout.findViewById(R.id.NotesLayout);
        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotePopup.CreateNotePopup(notesLayout);
            }
        });
        workoutDays.addView(layout);

    }


    void LoadWorkouts(){

        ArrayList<WorkoutDay> workoutDaysList = saveManager.getWorkoutDays(getSupportActionBar().getTitle().toString());
        ArrayList<String> dates =  new ArrayList<>();
        boolean createNewWorkoutDay = true;
        final String newData;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy ");
        newData = "" + mdformat.format(calendar.getTime());

        for(WorkoutDay workDay : workoutDaysList){

            CreateDayList(workDay.getDateText());
            dates.add(workDay.getDateText());
        }

        for(String date : dates) {

            if(newData == date){

                createNewWorkoutDay = false;
            }
        }

        if(createNewWorkoutDay){

            CreateDayList(newData);
        }
    }

    void CreateWorkoutNote(final LinearLayout notes, int series, float kgs){

        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout workOutSerie = (LinearLayout) inflater.inflate(R.layout.workout_serie, null, false);
        TextView sereisKgText = workOutSerie.findViewById(R.id.SeriesKg);
        TextView serieIndex = workOutSerie.findViewById(R.id.Index);
        int index = notes.getChildCount() + 1;
        serieIndex.setText("" + index);
        savedKgValue = kgs;
        savedSeriesValue = series;
        float afterNumber = savedKgValue  - (int)savedKgValue;
        String kg;
        if(afterNumber == 0f){
            kg = "" + (int) savedKgValue;
        }else {
            kg = "" + savedKgValue;

        }

        final Button deleteBtm = workOutSerie.findViewById(R.id.Delete);

        workOutSerie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteBtm.setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlphaAnimation exitAnim = new AlphaAnimation(1.0f, 0.0f);
                        exitAnim.setDuration(500);
                        exitAnim.setStartOffset(5000);
                        exitAnim.setFillAfter(true);
                        deleteBtm.startAnimation(exitAnim);
                        deleteBtm.setVisibility(View.GONE);

                    }
                }, 2000);
                return false;
            }
        });

        deleteBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notes.removeView(workOutSerie);
                ResetSeriesList(notes);
            }
        });

        sereisKgText.setText("" + savedSeriesValue + " X " + kg);
        notes.addView(workOutSerie);
    }

    void ResetSeriesList(LinearLayout layout){

        for(int i = 0; i < layout.getChildCount(); i++){

            View workOutSerie = layout.getChildAt(i);
            TextView serieIndex = workOutSerie.findViewById(R.id.Index);
            int index = i + 1;
            serieIndex.setText("" + index);
        }
    }
}
