package com.szczepaniak.dawid.treningapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity{

    private LinearLayout trenings;
    private TreningsDataBase treningsDataBase;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeListner(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Workouts");
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.drawer);

        trenings = findViewById(R.id.TreningsList);
        treningsDataBase = new TreningsDataBase();
        tabLayout = findViewById(R.id.tabs);
        loadTrenings("All");

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        AccountDrawer accountDrawer =  new AccountDrawer(drawerLayout, navigationView, MainActivity.this, MainActivity.this);


        NavigationView mussclesNav = findViewById(R.id.nav_view2);
        MusclesDrawer musclesDrawer =  new MusclesDrawer(mussclesNav, MainActivity.this);




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

//        @Override
//        public boolean onCreateOptionsMenu (Menu menu){
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.treningtype, menu);
//            return true;
//        }


    private void loadTrenings(String type){

        trenings.removeAllViews();
       // trenings.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        final ArrayList<TreningClass> treningClasses = treningsDataBase.getTreningClassList();

        if(type == "All") {
            for (final TreningClass trening : treningClasses) {

                LayoutInflater inflater = LayoutInflater.from(this);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.treninglistelement, null, false);
                TextView title = layout.findViewById(R.id.Title);
                title.setText(trening.getName());
                ImageView icon = layout.findViewById(R.id.icon);
                setImageIcon(icon, trening.getType());

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent treningIntent = new Intent(MainActivity.this, WorkoutActivity.class);
                        treningIntent.putExtra("TreningName", trening.getName());
                        MainActivity.this.startActivity(treningIntent);
                        MainActivity.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    }
                });

                trenings.addView(layout);
            }
        }else {

            for (TreningClass trening : treningClasses) {

                final TreningClass t = trening;
                if(trening.getType()== type) {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.treninglistelement, null, false);
                    TextView title = layout.findViewById(R.id.Title);
                    title.setText(trening.getName());
                    ImageView icon = layout.findViewById(R.id.icon);
                    setImageIcon(icon, trening.getType());

                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent treningIntent = new Intent(MainActivity.this, WorkoutActivity.class);
                            treningIntent.putExtra("TreningName", t.getName());
                            treningIntent.putExtra("TreningIndex", treningClasses.indexOf(t));
                            MainActivity.this.startActivity(treningIntent);
                            MainActivity.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);

                        }
                    });

                    trenings.addView(layout);
                }
            }
        }
    }

    void setImageIcon(ImageView img, String type){

        switch (type){

            case "Abdomen":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.abdomen));
                break;
            case "Back":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.back));
                break;
            case "Biceps":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.biceps));
                break;
            case "Chest":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.chest));
                break;
            case "Shoulders":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.shoulders));
                break;
            case "Legs":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.leg));
                break;
            case "Triceps":
                img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.triceps));
                break;
            default:
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
