package com.szczepaniak.dawid.treningapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountDrawer implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String userName;
    private String userEmail;
    private Uri userAvatar;
    private Context context;
    private Activity activity;
   // private ActionBarDrawerToggle drawerToggle;

    public AccountDrawer(DrawerLayout drawerLayout, NavigationView navigationView, Context context, Activity activity) {
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        this.context = context;
        this.activity = activity;

        navigationView.setNavigationItemSelectedListener(this);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            userName = user.getDisplayName();
            userEmail = user.getEmail();
            userAvatar = user.getPhotoUrl();

        }

        View header = navigationView.getHeaderView(0);
        CircleImageView avatar = header.findViewById(R.id.Avatar);
        Picasso.get().load(userAvatar).into(avatar);
        TextView name = header.findViewById(R.id.Name);
        name.setText(userName);
        TextView email = header.findViewById(R.id.Email);
        email.setText(userEmail);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_singout: {
                Toast.makeText(context, "Sing out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(context, LoginActivity.class);
                context.startActivity(loginActivity);
                activity.overridePendingTransition(R.anim.left_in, R.anim.left_out);
                activity.finish();
                activity.finish();
                break;
            }
        }
        return true;
    }

//    public ActionBarDrawerToggle GetDrawerToggle() {
//        return drawerToggle;
//    }
}
