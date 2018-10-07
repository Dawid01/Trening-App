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
import java.util.Calendar;
import java.util.Date;

public class WorkoutActivity extends AppCompatActivity {


    private Dialog dialog;
    private LinearLayout workoutDays;
    private int savedSeriesValue = 0;
    private float savedKgValue = 0f;

    private int deltaX;
    private  int deltaY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        workoutDays =  findViewById(R.id.WorkoutDays);

        dialog =  new Dialog(this);

        if(extras != null) {

            getSupportActionBar().setTitle(extras.getString("TreningName"));
        }

        CreateDayList();
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

    private void CreateDayList(){

        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.workoutday, null, false);
        FloatingActionButton addWork = layout.findViewById(R.id.AddWork);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy ");
        String strDate = "" + mdformat.format(calendar.getTime());
        TextView dateText = layout.findViewById(R.id.Date);
        TextView indexText = layout.findViewById(R.id.Index);
        int number = workoutDays.getChildCount() + 1;
        indexText.setText(String.valueOf(number));
        dateText.setText(strDate);
        final LinearLayout notesLayout = layout.findViewById(R.id.NotesLayout);
        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNotePopup(notesLayout);
            }
        });
        workoutDays.addView(layout);

    }

    private void ShowNotePopup(final LinearLayout notesLayout) {
        dialog.setContentView(R.layout.create_note);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText seriesText = dialog.findViewById(R.id.Series);
        seriesText.setText("" + savedSeriesValue);
        final EditText kgText = dialog.findViewById(R.id.Kg);
        kgText.setText("" + savedKgValue);


        seriesText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{
                    savedSeriesValue = Integer.parseInt(seriesText.getText().toString());
                }catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kgText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    savedKgValue = Float.parseFloat(kgText.getText().toString());
                }catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ImageView seriesUp = dialog.findViewById(R.id.SeriesUp);
        seriesUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int seriesCount =  Integer.parseInt(seriesText.getText().toString());
                int newValue = seriesCount + 1;
                seriesText.setText("" + newValue);
                savedSeriesValue = newValue;
            }
        });
        ImageView seriesDown = dialog.findViewById(R.id.SeriesDown);
        seriesDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int seriesCount =  Integer.parseInt(seriesText.getText().toString());
                if(seriesCount != 0) {
                    int newValue = seriesCount - 1;
                    seriesText.setText("" + newValue);
                    savedSeriesValue = newValue;
                }

            }
        });

        ImageView kgUp = dialog.findViewById(R.id.KgUp);
        kgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float kgCount =  Float.parseFloat(kgText.getText().toString());
                int i = (int) kgCount;
                float afterNumbers = kgCount - i;
                float newValue;
                if(afterNumbers == 0f || afterNumbers == 0.25f || afterNumbers == 0.5f || afterNumbers == 0.75f){
                    newValue = kgCount + 0.25f;
                }else {
                    newValue = Math.round(kgCount * 4)/4f;
                }
                kgText.setText("" + newValue);
                savedKgValue = newValue;
            }
        });
        ImageView kgDown = dialog.findViewById(R.id.KgDown);
        kgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float kgCount = Float.parseFloat(kgText.getText().toString());
                if (kgCount != 0f) {
                    int i = (int) kgCount;
                    float afterNumbers = kgCount - i;
                    float newValue;
                    if (afterNumbers == 0f || afterNumbers == 0.25f || afterNumbers == 0.5f || afterNumbers == 0.75f) {
                        newValue = kgCount - 0.25f;
                    } else {
                        newValue = Math.round(kgCount * 4) / 4f;
                    }
                    kgText.setText("" + newValue);
                    savedKgValue = newValue;
                }
            }
        });

        final Button create =  dialog.findViewById(R.id.Create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(WorkoutActivity.this);
                final LinearLayout workOutSerie = (LinearLayout) inflater.inflate(R.layout.workout_serie, null, false);
                TextView sereisKgText = workOutSerie.findViewById(R.id.SeriesKg);
                TextView serieIndex = workOutSerie.findViewById(R.id.Index);
                int index = notesLayout.getChildCount() + 1;
                serieIndex.setText("" + index);
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

                        notesLayout.removeView(workOutSerie);
                        ResetSeriesList(notesLayout);
                    }
                });

                sereisKgText.setText("" + savedSeriesValue + " X " + kg);
                notesLayout.addView(workOutSerie);
                dialog.dismiss();

            }
        });

        dialog.show();
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
