package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

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
        values.add((int)(Math.random()*4));
        animationCounter = 0;
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
            int timeToBlink = 500;    //in ms
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
                        findViewById(R.id.startButton).setEnabled(true);
                        enableButtons(true);
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
    public boolean buttonPress(int buttonID){
        
        return true;
    }

    public void redButtonOnClick(View view) {
        resetColor();
        view.setBackgroundColor(Color.RED);
    }

    public void greenButtonOnClick(View view) {
        resetColor();
        view.setBackgroundColor(Color.GREEN);
    }


    public void blueButtonClick(View view) {
        resetColor();
        view.setBackgroundColor(Color.BLUE);
    }

    public void yellowButtonClick(View view) {
        resetColor();
        view.setBackgroundColor(Color.YELLOW);
    }
}