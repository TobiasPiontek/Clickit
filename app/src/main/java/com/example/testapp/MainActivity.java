package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    static ArrayList<Integer> values = new ArrayList<>();
    static int animationCounter;
    static int clickCounter;
    static boolean dark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetAnimation();
    }

    public void StartButtonClick(View view){
        findViewById(R.id.startButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.greenButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.redButton).setBackgroundColor(Color.BLACK);
        resetAnimation();
        setElement();
        findViewById(R.id.startButton).setEnabled(false);
        enableButtons(false);
        animateButton();
        animationCounter=0;
    }

    public void enableButtons(boolean set){
        findViewById(R.id.redButton).setEnabled(set);
        findViewById(R.id.greenButton).setEnabled(set);
        findViewById(R.id.blueButton).setEnabled(set);
        findViewById(R.id.yellowButton).setEnabled(set);
    }

    private void animateButton(){
        final Handler handler = new Handler();
        new Thread(() -> {
            int timeToBlink = 250;    //in ms
            try{
                Thread.sleep(timeToBlink);

            }catch (Exception e) {

            }
            handler.post(() -> {
                if(animationCounter<0){
                    animationCounter++;
                    animateButton();
                    return;
                }

                if(animationCounter < values.size()) {
                    if(!dark) {
                        if (values.get(animationCounter) == 0) {
                            buttonAnimation(Color.BLACK,Color.GREEN,findViewById(R.id.greenButton),timeToBlink);
                        } else if(values.get(animationCounter) == 1) {
                            buttonAnimation(Color.BLACK,Color.RED,findViewById(R.id.redButton),timeToBlink);
                        }else if(values.get(animationCounter) == 2){
                            buttonAnimation(Color.BLACK,Color.BLUE,findViewById(R.id.blueButton),timeToBlink);
                        }else{
                            buttonAnimation(Color.BLACK,Color.YELLOW,findViewById(R.id.yellowButton),timeToBlink);
                        }

                        dark = true;
                    }
                    else{
                        if (values.get(animationCounter) == 0) {
                            buttonAnimation(Color.GREEN,Color.BLACK,findViewById(R.id.greenButton),timeToBlink);
                        } else if(values.get(animationCounter) == 1) {
                            buttonAnimation(Color.RED,Color.BLACK,findViewById(R.id.redButton),timeToBlink);
                        }else if(values.get(animationCounter) == 2){
                            buttonAnimation(Color.BLUE,Color.BLACK,findViewById(R.id.blueButton),timeToBlink);
                        }else{
                            buttonAnimation(Color.YELLOW,Color.BLACK,findViewById(R.id.yellowButton),timeToBlink);
                        }


                        dark = false;
                        animationCounter++;
                    }
                    animateButton();
                }else{
                    enableButtons(true);
                    resetColor();
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
                animateButton();
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
        animationCounter=-3;
    }

    public void buttonAnimation(int colorFrom,int colorTo, View view,int duration){
        ObjectAnimator backgroundColor = ObjectAnimator.ofInt(view, "backgroundColor", colorFrom, colorTo);
        backgroundColor.setDuration(duration);
        backgroundColor.setEvaluator(new ArgbEvaluator());
        backgroundColor.start();
    }

    public void greenButtonOnClick(View view) {
        buttonAnimation(Color.GREEN,Color.BLACK,view,500);
        buttonPress(0);
    }

    public void redButtonOnClick(View view) {
        buttonAnimation(Color.RED,Color.BLACK,view,500);
        buttonPress(1);

    }

    public void blueButtonClick(View view) {
        buttonAnimation(Color.BLUE,Color.BLACK,view,500);
        buttonPress(2);
    }

    public void yellowButtonClick(View view) {
        buttonAnimation(Color.YELLOW,Color.BLACK,view,500);
        buttonPress(3);
    }
}