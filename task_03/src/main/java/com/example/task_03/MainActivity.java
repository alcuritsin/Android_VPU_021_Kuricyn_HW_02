package com.example.task_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private CheckBox chBoxRed, chBoxGreen, chBoxBlue;
    private ConstraintLayout CL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) this.findViewById(R.id.tvTitle);

        chBoxRed = (CheckBox) this.findViewById(R.id.chBoxRed);
        chBoxGreen = (CheckBox) this.findViewById(R.id.chBoxGreen);
        chBoxBlue = (CheckBox) this.findViewById(R.id.chBoxBlue);

        CL = (ConstraintLayout) this.findViewById(R.id.CL);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackGraund();
            }
        };

        chBoxRed.setOnClickListener(ocl);
        chBoxGreen.setOnClickListener(ocl);
        chBoxBlue.setOnClickListener(ocl);

    }

    private void changeBackGraund(){
        int redBack, greenBack, blueBack;

        redBack = 0;
        greenBack = 0;
        blueBack = 0;

        if (chBoxRed.isChecked()){
            redBack = 255;
        }

        if (chBoxGreen.isChecked()){
            greenBack = 255;
        }

        if (chBoxBlue.isChecked()){
            blueBack = 255;
        }

        CL.setBackgroundColor(Color.rgb(redBack, greenBack, blueBack));

        int redText, greenText, blueText;

        redText = 255 - redBack;
        greenText = 255 - greenBack;
        blueText = 255 - blueBack;

        int colorText = Color.rgb(redText, greenText, blueText);

        chBoxBlue.setTextColor(colorText);
        chBoxGreen.setTextColor(colorText);
        chBoxRed.setTextColor(colorText);

        tvTitle.setTextColor(colorText);
    }
}