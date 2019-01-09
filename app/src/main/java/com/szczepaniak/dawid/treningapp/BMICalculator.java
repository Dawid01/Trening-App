package com.szczepaniak.dawid.treningapp;

import android.graphics.PorterDuff;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BMICalculator extends AppCompatActivity {

    private TextInputEditText weightText;
    private TextInputEditText heightText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeListner(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        final TextView textBIM = findViewById(R.id.BMItext);
        Button calculate = findViewById(R.id.Calculate);
        weightText = findViewById(R.id.WeightText);
        heightText = findViewById(R.id.HeightText);

        weightText.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.SRC_ATOP);
        heightText.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.SRC_ATOP);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weight = weightText.getText().toString();
                String height = heightText.getText().toString();

                if(weight != null && weight != "" && height != null && height != ""){

                    float w = Float.parseFloat(weight);
                    float h = Float.parseFloat(height);
                    double bmi = w/Math.pow(2, (double) (h/100f));
                    DecimalFormat form = new DecimalFormat("0.00");
                    String result = "";

                    if(bmi < 18f){
                        result = "Underweight";
                    }else if (bmi < 25){
                        result = "Normal weight";
                    }else if(bmi < 30){
                        result = "Overweight";
                    }else if(bmi < 40){
                        result = "Obesity";
                    }else{
                        result = "Serious obesity";
                    }

                    textBIM.setText("Your BMI is: " + form.format(bmi) + "\n" + result);
                }else{

                    Toast.makeText(BMICalculator.this, "Set values to calculate", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
