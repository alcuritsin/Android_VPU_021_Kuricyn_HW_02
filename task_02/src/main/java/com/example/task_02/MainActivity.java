package com.example.task_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GridLayout GL_UP = new GridLayout(this);
        GL_UP.setOrientation(GridLayout.HORIZONTAL);
        GL_UP.setColumnCount(2);
        GL_UP.setRowCount(2);

        GridLayout GL_DOWN = new GridLayout(this);
        GL_DOWN.setOrientation(GridLayout.HORIZONTAL);
        GL_DOWN.setColumnCount(2);
        GL_DOWN.setRowCount(2);

        GridLayout.LayoutParams	LP_GL	= new GridLayout.LayoutParams();
        LP_GL.width	= ViewGroup.LayoutParams.WRAP_CONTENT;
        LP_GL.height	= ViewGroup.LayoutParams.WRAP_CONTENT;

        //!!! Это свойство не применяется. Не могу понять почему.
        LP_GL.setGravity(Gravity.CENTER);

        GL_UP.setLayoutParams(LP_GL);
        GL_DOWN.setLayoutParams(LP_GL);

        for (int i = 1; i <= 4; i++)
        {
            Button buttonUp = new Button(this);
            buttonUp.setText("up " + i);
            GL_UP.addView(buttonUp);

            Button buttonDown = new Button(this);
            buttonDown.setText("dn " + i);
            GL_DOWN.addView(buttonDown);
        }

        FrameLayout FL_UP = new FrameLayout(this);
        FL_UP.setBackgroundColor(Color.rgb(199,244,196));
        FrameLayout FL_DOWN = new FrameLayout(this);
        FL_DOWN.setBackgroundColor(Color.rgb(244,196,196));
        LinearLayout.LayoutParams LP_FL = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1.0f
        );

        FL_UP.setLayoutParams(LP_FL);
        FL_DOWN.setLayoutParams(LP_FL);

        FL_UP.addView(GL_UP);
        FL_DOWN.addView(GL_DOWN);

        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(LinearLayout.VERTICAL);
        LL.setPadding(20,20,20,20);

        LinearLayout.LayoutParams LP_LL = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        LL.setLayoutParams(LP_LL);

        LL.addView(FL_UP);
        LL.addView(FL_DOWN);

        ConstraintLayout CL = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams LP_CL = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        CL.setLayoutParams(LP_CL);

        CL.addView(LL);

        this.setContentView(CL);
    }
}