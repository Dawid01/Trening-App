package com.szczepaniak.dawid.treningapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    View signInGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInGoogle = findViewById(R.id.SignInGoogle);

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainAvivity = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(mainAvivity);
                LoginActivity.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
                LoginActivity.this.finish();
            }
        });
    }
}
