package com.szczepaniak.dawid.treningapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkoutActivity extends AppCompatActivity {


    private Dialog dialog;
    private LinearLayout workoutDays;

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
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.workoutday, null, false);
        FloatingActionButton addWork = layout.findViewById(R.id.AddWork);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy ");
        String strDate = "" + mdformat.format(calendar.getTime());
        TextView dateText = layout.findViewById(R.id.Date);
        TextView indexText = layout.findViewById(R.id.Index);
        int number = workoutDays.getChildCount() + 1;
        indexText.setText(String.valueOf(number));
        dateText.setText(strDate);
        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNotePopup();
            }
        });
        workoutDays.addView(layout);

    }

    private void ShowNotePopup() {
        dialog.setContentView(R.layout.create_note);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
