package com.szczepaniak.dawid.treningapp;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LinearLayout trenings;
    TreningsDataBase treningsDataBase;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Workouts");
        trenings = findViewById(R.id.TreningsList);
        treningsDataBase = new TreningsDataBase();
        tabLayout = findViewById(R.id.tabs);
        loadTrenings("All");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String type = (String)tab.getText();

                switch (type){

                    case "All":
                        loadTrenings("All");
                        break;
                    case "Abdomen":
                        loadTrenings("Abdomen");
                        break;
                    case "Back":
                        loadTrenings("Back");
                        break;
                    case "Biceps":
                        loadTrenings("Biceps");
                        break;
                    case "Chest":
                        loadTrenings("Chest");
                        break;
                    case "Shoulders":
                        loadTrenings("Shoulders");
                        break;
                    case "Legs":
                        loadTrenings("Legs");
                        break;
                    case "Triceps":
                        loadTrenings("Triceps");
                        break;
                        default:
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.treningtype, menu);
            return true;
        }


    private void loadTrenings(String type){

        trenings.removeAllViews();

        ArrayList<TreningClass> treningClasses = treningsDataBase.getTreningClassList();

        if(type == "All") {
            for (TreningClass trening : treningClasses) {

                LayoutInflater inflater = LayoutInflater.from(this);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.treninglistelement, null, false);
                TextView title = layout.findViewById(R.id.Title);
                title.setText(trening.getName());
                trenings.addView(layout);
            }
        }else {

            for (TreningClass trening : treningClasses) {

                if(trening.getType()== type) {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.treninglistelement, null, false);
                    TextView title = layout.findViewById(R.id.Title);
                    title.setText(trening.getName());
                    trenings.addView(layout);
                }
            }
        }
    }
}
