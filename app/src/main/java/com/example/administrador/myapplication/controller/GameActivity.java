package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


import com.example.administrador.myapplication.R;

/**
 * Created by Administrador on 29/07/2015.
 */
public class GameActivity extends AppCompatActivity {

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("drown");

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            System.out.println("up");
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            System.out.println("Dragging");
        }
        return false;
    }

    Button buttonBot;
    Button buttonTop;
    RadioButton bolinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buttonTop = (Button) findViewById(R.id.btnTop);
        buttonBot = (Button) findViewById(R.id.btnBot);
        bolinha = (RadioButton) findViewById(R.id.radioButton);


        buttonBot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                if (me.getAction() == MotionEvent.ACTION_MOVE) {
                    Integer i = (int) me.getRawX() - (v.getWidth() / 2);
                    if (i >= 30 && i <= 605) {
                        buttonBot.setX(i);
                        buttonTop.setX(i);
                        System.out.println(i.toString());
                        bolinha.setX(i);
                        bolinha.setY(i);
                    }
                }
                return true;
            }
        });
    }
}
