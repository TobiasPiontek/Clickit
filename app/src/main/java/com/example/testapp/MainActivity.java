package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    static ArrayList<Integer> values = new ArrayList<>();
    static int animationCounter =0;
    static int clickCounter = 0;
    static boolean dark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartButtonClick(View view) throws InterruptedException {
        findViewById(R.id.startButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.greenButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.redButton).setBackgroundColor(Color.BLACK);
        resetAnimation();
        setElement();
        findViewById(R.id.startButton).setEnabled(false);
        enableButtons(false);
        blinkText();
    }

    public void enableButtons(boolean set){
        findViewById(R.id.redButton).setEnabled(set);
        findViewById(R.id.greenButton).setEnabled(set);
        findViewById(R.id.blueButton).setEnabled(set);
        findViewById(R.id.yellowButton).setEnabled(set);
    }

    private void blinkText(){
        final Handler handler = new Handler();
        new Thread(() -> {
            int timeToBlink = 250;    //in ms
            try{
                Thread.sleep(timeToBlink);

            }catch (Exception e) {

            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(animationCounter < values.size()) {
                        if(!dark) {
                            if (values.get(animationCounter) == 0) {
                                resetColor();
                                findViewById(R.id.greenButton).setBackgroundColor(Color.GREEN);
                            } else if(values.get(animationCounter) == 1) {
                                resetColor();
                                findViewById(R.id.redButton).setBackgroundColor(Color.RED);
                            }else if(values.get(animationCounter) == 2){
                                resetColor();
                                findViewById(R.id.blueButton).setBackgroundColor(Color.BLUE);
                            }else{
                                resetColor();
                                findViewById(R.id.yellowButton).setBackgroundColor(Color.YELLOW);
                            }

                            dark = true;
                        }
                        else{
                            resetColor();
                            dark = false;
                            animationCounter++;
                        }
                        blinkText();
                    }else{
                        enableButtons(true);
                        resetColor();
                    }
                }
            });
        }).start();

    }

    public void resetColor(){
        findViewById(R.id.redButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.greenButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.blueButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.yellowButton).setBackgroundColor(Color.BLACK);
    }

    public void buttonPress(int buttonID){
        if(values.get(clickCounter)==buttonID){
            clickCounter++;
            if(values.size()==clickCounter){
                resetAnimation();
                setElement();
                enableButtons(false);
                blinkText();
            }

        }else{
            resetAnimation();
            values.clear();
            findViewById(R.id.startButton).setEnabled(true);
            enableButtons(false);
        }
    }

    public void setElement(){
        values.add((int)(Math.random()*4));
    }

    public void resetAnimation(){
        clickCounter=0;
        animationCounter=0;
    }

    public void buttonAnimation(int colorFrom, View view){
        ObjectAnimator backgroundColor = ObjectAnimator.ofInt(view, "backgroundColor", colorFrom, Color.BLACK);
        backgroundColor.setDuration(500);
        backgroundColor.start();
    }

    public void greenButtonOnClick(View view) {
        buttonAnimation(Color.GREEN,view);
        buttonPress(0);
    }

    public void redButtonOnClick(View view) {
        buttonAnimation(Color.RED,view);
        buttonPress(1);

    }

    public void blueButtonClick(View view) {
        buttonAnimation(Color.BLUE,view);
        buttonPress(2);
    }

    public void yellowButtonClick(View view) {
        buttonAnimation(Color.YELLOW,view);
        buttonPress(3);
    }
}