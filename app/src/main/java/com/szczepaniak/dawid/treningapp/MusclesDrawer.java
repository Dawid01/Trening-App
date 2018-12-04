package com.szczepaniak.dawid.treningapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MusclesDrawer {

    private NavigationView navigationView;
    private Activity activity;
    private DatabaseReference mDatabase;
    private FirebaseFirestore db;
    private DocumentReference documentReference;


    public MusclesDrawer(final NavigationView navigationView, Activity activity) {
        this.navigationView = navigationView;
        this.activity =  activity;

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                DrawMuscles(documentSnapshot);

            }
        });

    }


    void DrawMuscles(DocumentSnapshot doc) {

        View muscleHeader = navigationView.getHeaderView(0);

        if (doc.getBoolean("Abdonem") != null) {

            boolean canAbdonem = doc.getBoolean("Abdonem");
            ImageView abdoneMuscle = muscleHeader.findViewById(R.id.AbdonemDrawer);
            if (canAbdonem) {
                abdoneMuscle.setColorFilter(Color.rgb(74, 239, 74));
            } else {
                abdoneMuscle.setColorFilter(Color.rgb(239, 14, 14));
            }

            boolean canArms = doc.getBoolean("Arms");
            ImageView armsMuscle = muscleHeader.findViewById(R.id.ArmsDrawer);
            if (canArms) {
                armsMuscle.setColorFilter(Color.rgb(74, 239, 74));
            } else {
                armsMuscle.setColorFilter(Color.rgb(239, 14, 14));
            }

            boolean canBack = doc.getBoolean("Back");
            ImageView BackMuscle = muscleHeader.findViewById(R.id.BackDrawer);
            if (canBack) {
                BackMuscle.setColorFilter(Color.rgb(74, 239, 74));
            } else {
                BackMuscle.setColorFilter(Color.rgb(239, 14, 14));
            }

            boolean canChest = doc.getBoolean("Chest");
            ImageView ChestMuscle = muscleHeader.findViewById(R.id.ChestDrawer);
            if (canChest) {
                ChestMuscle.setColorFilter(Color.rgb(74, 239, 74));
            } else {
                ChestMuscle.setColorFilter(Color.rgb(239, 14, 14));
            }

            boolean canLegs = doc.getBoolean("Legs");
            ImageView LegsMuscle = muscleHeader.findViewById(R.id.LegsDrawer);
            if (canLegs) {
                LegsMuscle.setColorFilter(Color.rgb(74, 239, 74));
            } else {
                LegsMuscle.setColorFilter(Color.rgb(239, 14, 14));
            }

            boolean canShoulders = doc.getBoolean("Shoulders");
            ImageView ShouldersMuscle = muscleHeader.findViewById(R.id.ShouldersDrawer);
            if (canShoulders) {
                ShouldersMuscle.setColorFilter(Color.rgb(74, 239, 74));
            } else {
                ShouldersMuscle.setColorFilter(Color.rgb(239, 14, 14));
            }

        }
    }
}
