package com.szczepaniak.dawid.treningapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowNotePopup {

    private WorkoutActivity workoutActivity;
    private Dialog dialog;

    public ShowNotePopup(WorkoutActivity workoutActivity, Dialog dialog) {
        this.workoutActivity = workoutActivity;
        this.dialog = dialog;
    }


    public void CreateNotePopup(final LinearLayout notesLayout) {



        dialog.setContentView(R.layout.create_note);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText seriesText = dialog.findViewById(R.id.Series);
        seriesText.setText("" + workoutActivity.savedSeriesValue);
        final EditText kgText = dialog.findViewById(R.id.Kg);
        kgText.setText("" + workoutActivity.savedKgValue);


        seriesText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{
                    workoutActivity.savedSeriesValue = Integer.parseInt(seriesText.getText().toString());
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
                    workoutActivity.savedKgValue = Float.parseFloat(kgText.getText().toString());
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
                workoutActivity.savedSeriesValue = newValue;
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
                    workoutActivity.savedSeriesValue = newValue;
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
                workoutActivity.savedKgValue = newValue;
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
                    workoutActivity.savedKgValue = newValue;
                }
            }
        });

        final Button create =  dialog.findViewById(R.id.Create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workoutActivity.CreateWorkoutNote(notesLayout, workoutActivity.savedSeriesValue, workoutActivity.savedKgValue);
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
