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
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class WorkoutActivity extends AppCompatActivity {


    private Dialog dialog;
    private LinearLayout workoutDays;
    public int savedSeriesValue = 0;
    public float savedKgValue = 0f;

   // private SaveManager saveManager;
    private ShowNotePopup showNotePopup;
    private TreningClass trening;
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    private ArrayList<WorkoutDay> workoutDaysList;

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
        //saveManager = new SaveManager(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CreateDayList(String date,  Map<String, Object> series) {

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
        //workoutDaysList.add(new WorkoutDay(TODAY_TIME));

        if (series != null) {

            for (Map.Entry<String, Object> serie : series.entrySet()) {

                ArrayList<Long> workData = (ArrayList<Long>) serie.getValue();
                if(workData != null) {
                    long r = (long)workData.get(0);
                    long kg = (long)workData.get(1);
                    LoadWorkoutNote(notesLayout, (int)r, (float)kg);
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

                    HashMap<String, Object> workouts = (HashMap<String, Object>) documentSnapshot.get(WORKOUT_NAME);


                    if (workouts != null) {


                        ArrayList<Map.Entry<String, Object>> workoutsList = new ArrayList<>();

                        for (Map.Entry<String, Object> entry : workouts.entrySet()) {

                            workoutsList.add(entry);
                        }

                        for(int i = 0; i < workouts.size(); i++){

                            Map.Entry<String, Object> entry = workoutsList.get((workouts.size()-1) - i);
                            String key = entry.getKey();
                            Map<String, Object> series = (Map<String, Object>) entry.getValue();
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

    void LoadWorkoutNote(final LinearLayout notes, final int series, final float kgs){

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
    }

    void CreateWorkoutNote(final LinearLayout notes, final int series, final float kgs){

        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

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

                    HashMap<String, Object> workouts = (HashMap<String, Object>) documentSnapshot.get(workOutDate);

                    if (workouts == null) {

                        workouts = new HashMap<>();
                    }

                    HashMap<String, Object> workoutNotes = (HashMap<String, Object>)workouts.get(workOutDate);


                    if(workoutNotes == null){

                        workoutNotes =  new HashMap<>();
                    }

                    ArrayList<Long> newWorkNote = new ArrayList<>();
                    newWorkNote.add((long)savedSeriesValue);
                    newWorkNote.add((long)savedKgValue);
                    workoutNotes.put("" + (notes.getChildCount() - 1), newWorkNote);
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



    public ArrayList<WorkoutDay> StringToArray( String string){

        ArrayList<WorkoutDay> list;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<WorkoutDay>>() {}.getType();
        list = gson.fromJson(string, type);
        return list;
    }


    public String ArrayToString(ArrayList<WorkoutDay> list){

        Gson gson = new Gson();
        return gson.toJson(list);
    }
}




