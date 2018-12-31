package com.szczepaniak.dawid.treningapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutActivity extends AppCompatActivity {


    private Dialog dialog;
    private LinearLayout workoutDays;
    public int savedSeriesValue = 0;
    public float savedKgValue = 0f;

    private ShowNotePopup showNotePopup;
    private TreningClass trening;
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;

    private String WORKOUT_NAME = "";
    private String TODAY_TIME = "";
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        workoutDays =  findViewById(R.id.WorkoutDays);
        dialog =  new Dialog(this);
        showNotePopup =  new ShowNotePopup(WorkoutActivity.this, dialog);
        mAuth = FirebaseAuth.getInstance();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy");
        TODAY_TIME = "" + mdformat.format(calendar.getTime());

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        AccountDrawer accountDrawer =  new AccountDrawer(drawerLayout, navigationView, WorkoutActivity.this, WorkoutActivity.this);
        NavigationView mussclesNav = findViewById(R.id.nav_view2);
        MusclesDrawer musclesDrawer =  new MusclesDrawer(mussclesNav, WorkoutActivity.this);

        if(extras != null) {

            getSupportActionBar().setTitle(extras.getString("TreningName"));
            TreningsDataBase treningsDataBase =  new TreningsDataBase();
            int index = extras.getInt("TreningIndex");
            trening = treningsDataBase.getTreningClassList().get(index);
            WORKOUT_NAME = extras.getString("TreningName");
        }

        LoadWorkouts();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graph_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        if (id == R.id.ShowGraph) {

            ShowWorkoutsGraph();
        }

        return super.onOptionsItemSelected(item);
    }

    private void CreateDayList(String date,  ArrayList<HashMap<String, Object>> series) {

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

        if (series != null) {

            for (HashMap<String, Object> serie : series) {

                if(serie != null) {
                    String r = (String) serie.get("r");
                    String kg = (String) serie.get("kgs");
                    LoadWorkoutNote(notesLayout, r, kg);
                }
            }
        }
    }


    void LoadWorkouts(){

        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("Users").document(user.getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    Map<String, Object> workouts = (Map<String, Object>) documentSnapshot.get(WORKOUT_NAME);


                    if (workouts != null) {


                        ArrayList<Map.Entry<String, Object>> workoutsList = new ArrayList<>();

                        for (Map.Entry<String, Object> entry : workouts.entrySet()) {

                            workoutsList.add(entry);

                        }

                        for(int i = 0; i < workoutsList.size(); i++){
                            Map.Entry<String, Object> entry = workoutsList.get((workoutsList.size() - i)- 1);
                            String key = entry.getKey();
                            ArrayList<HashMap<String, Object>> series = (ArrayList<HashMap<String, Object>>) entry.getValue();
                            CreateDayList(key, series);
                        }


                        if (!workouts.containsKey(TODAY_TIME)) {

                            CreateDayList(TODAY_TIME, null);
                        }

                    } else {

                        CreateDayList(TODAY_TIME, null);
                    }


                }else {

                    CreateDayList(TODAY_TIME, null);
                }
            }

        });
    }

    void LoadWorkoutNote(final LinearLayout notes, final String series, final String kgs){

        LayoutInflater inflater = LayoutInflater.from(WorkoutActivity.this);
        final LinearLayout workOutSerie = (LinearLayout) inflater.inflate(R.layout.workout_serie, null, false);
        TextView sereisKgText = workOutSerie.findViewById(R.id.SeriesKg);
        TextView serieIndex = workOutSerie.findViewById(R.id.Index);
        int index = notes.getChildCount() + 1;
        serieIndex.setText("" + index);
        savedKgValue = Float.parseFloat(kgs);
        savedSeriesValue = Integer.parseInt(series);
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
            public boolean onLongClick(final View v) {
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
                        DeleteWorkoutNote(notes, workOutSerie, v);


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

    void DeleteWorkoutNote(final LinearLayout notes, final LinearLayout workOutSerie, final View deletedView){

        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        documentReference = db.collection("Users").document(user.getUid());


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                View parent = (View) notes.getParent().getParent();
                TextView dateText = parent.findViewById(R.id.Date);
                String workOutDate = dateText.getText().toString();
                TextView indexText = deletedView.findViewById(R.id.Index);
                int index = Integer.parseInt(indexText.getText().toString()) - 1;

                if (documentSnapshot.exists()) {

                    HashMap<String, Object> workouts = (HashMap<String, Object>) documentSnapshot.get(WORKOUT_NAME);
                    ArrayList<HashMap<String, Object>> workoutNotes = (ArrayList<HashMap<String, Object>>)workouts.get(workOutDate);

                    workoutNotes.remove(index);
                    workouts.put(workOutDate, workoutNotes);

                    HashMap<String, Object> finishMap = new HashMap<>();
                    finishMap.put(WORKOUT_NAME, workouts);

                    documentReference.set(finishMap, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                }
            }
        });

    }

    void CreateWorkoutNote(final LinearLayout notes, final int series, final float kgs){

        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        LayoutInflater inflater = LayoutInflater.from(WorkoutActivity.this);
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

        documentReference = db.collection("Users").document(user.getUid());


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                View parent = (View) notes.getParent().getParent();
                TextView dateText = parent.findViewById(R.id.Date);
                String workOutDate = dateText.getText().toString();

                if (documentSnapshot.exists()) {


                    HashMap<String, Object> workouts = (HashMap<String, Object>) documentSnapshot.get(WORKOUT_NAME);

                    if (workouts == null) {

                        workouts = new HashMap<>();
                    }

                    ArrayList<HashMap<String, Object>> workoutNotes = (ArrayList<HashMap<String, Object>>)workouts.get(workOutDate);


                    if(workoutNotes == null){

                        workoutNotes =  new ArrayList<>();
                    }

                    HashMap<String, Object> newWorkNote = new HashMap<>();
                    newWorkNote.put("r", "" + savedSeriesValue);
                    newWorkNote.put("kgs", "" + savedKgValue);
                    workoutNotes.add(newWorkNote);
                    workouts.put(workOutDate, workoutNotes);

                    HashMap<String, Object> finishMap = new HashMap<>();
                    finishMap.put(WORKOUT_NAME, workouts);

                    documentReference.set(finishMap, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                }
            }
        });


    }

    void ResetSeriesList(LinearLayout layout){

        for(int i = 0; i < layout.getChildCount(); i++){

            View workOutSerie = layout.getChildAt(i);
            TextView serieIndex = workOutSerie.findViewById(R.id.Index);
            int index = i + 1;
            serieIndex.setText("" + index);
        }
    }

   void ShowWorkoutsGraph(){

       View popUpView = getLayoutInflater().inflate(R.layout.graph_layout,
               null);
       PopupWindow graphPopup = new PopupWindow(popUpView, LinearLayout.LayoutParams.FILL_PARENT,
               LinearLayout.LayoutParams.FILL_PARENT, true);
       graphPopup.setAnimationStyle(android.R.style.Animation_Dialog);
       graphPopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

       GraphView graph = popUpView.findViewById(R.id.Graph);
       LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
               new DataPoint(0, 1),
               new DataPoint(1, 5),
               new DataPoint(2, 3),
               new DataPoint(3, 2),
               new DataPoint(4, 7),
               new DataPoint(5, 1),
               new DataPoint(6, 5),
               new DataPoint(7, 3),
               new DataPoint(8, 2),
               new DataPoint(9, 6),

       });
       LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
               new DataPoint(0, 7),
               new DataPoint(1, 4),
               new DataPoint(2, 7),
               new DataPoint(3, 8),
               new DataPoint(4, 6),
               new DataPoint(5, 3),
               new DataPoint(6, 4),
               new DataPoint(7, 2),
               new DataPoint(8, 9),
               new DataPoint(9, 3),

       });

       LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
               new DataPoint(0, 2),
               new DataPoint(1, 6),
               new DataPoint(2, 1),
               new DataPoint(3, 2),
               new DataPoint(4, 5),
               new DataPoint(5, 7),
               new DataPoint(6, 3),
               new DataPoint(7, 4),
               new DataPoint(8, 1),
               new DataPoint(9, 8),

       });

       LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
               new DataPoint(0, 0),
               new DataPoint(1, 3),
               new DataPoint(2, 2),
               new DataPoint(3, 5),
               new DataPoint(4, 6),
               new DataPoint(5, 3),
               new DataPoint(6, 2),
               new DataPoint(7, 7),
               new DataPoint(8, 3),
               new DataPoint(9, 20),

       });
       series.setColor(Color.parseColor("#ffa000"));
       series.setTitle("28.12.2018");
       series.setDrawDataPoints(true);
       series.setDataPointsRadius(10);
       series.setThickness(8);

       series2.setColor(Color.parseColor("#c67100"));
       series2.setTitle("29.12.2018");
       series2.setDrawDataPoints(true);
       series2.setDataPointsRadius(10);
       series2.setThickness(8);

       series3.setColor(Color.parseColor("#a86000"));
       series3.setTitle("30.12.2018");
       series3.setDrawDataPoints(true);
       series3.setDataPointsRadius(10);
       series3.setThickness(8);

       series4.setColor(Color.parseColor("#724100"));
       series4.setTitle("01.01.2019");
       series4.setDrawDataPoints(true);
       series4.setDataPointsRadius(10);
       series4.setThickness(8);

       //graph.setCursorMode(true);
       graph.getViewport().setScrollable(true);
       graph.getViewport().setScrollableY(true);
       graph.getViewport().setScalable(true);
       graph.getViewport().setScalableY(true);

       graph.getViewport().setYAxisBoundsManual(true);
       graph.getViewport().setMinY(0);
       graph.getViewport().setMaxY(12);

       graph.getViewport().setXAxisBoundsManual(true);
       graph.getViewport().setMinX(0);
       graph.getViewport().setMaxX(8);

       graph.getLegendRenderer().setVisible(true);
       graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
       graph.addSeries(series);
       graph.addSeries(series2);
       graph.addSeries(series3);
       graph.addSeries(series4);

   }

}




