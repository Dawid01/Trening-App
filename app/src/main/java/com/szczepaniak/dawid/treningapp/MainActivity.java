package com.szczepaniak.dawid.treningapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView searcheType;
    ImageView showTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Trenings");


    }

      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.treningtype, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.All:
                searcheType.setText("All");
                return true;
            case R.id.Abdomen:
                searcheType.setText("Abdomen");
                return true;
            case R.id.Back:
                searcheType.setText("Back");
                return true;
            case R.id.Biceps:
                searcheType.setText("Biceps");
                return true;
            case R.id.Chest:
                searcheType.setText("Chest");
                return true;
            case R.id.Shoulders:
                searcheType.setText("Shoulders");
                return true;
            case R.id.Legs:
                searcheType.setText("Legs");
                return true;
            case R.id.Triceps:
                searcheType.setText("Triceps");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
