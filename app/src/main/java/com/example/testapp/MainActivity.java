package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
    static int[] values;
    static int blinkLength = 10;
    static int counter=0;
    static boolean dark = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartButtonClick(View view) throws InterruptedException {
        findViewById(R.id.startButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.greenButton).setBackgroundColor(Color.BLACK);
        findViewById(R.id.redButton).setBackgroundColor(Color.BLACK);
        values = new int[blinkLength + 1];
        for (int i = 0; i < blinkLength; i++) {
            values[i]=(int)(Math.random()*4);
        }
        counter = 0;
        blinkText();
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
                    if(counter< blinkLength) {
                        if(!dark) {
                            if (values[counter] == 0) {
                                resetColor();
                                findViewById(R.id.greenButton).setBackgroundColor(Color.GREEN);
                            } else if(values[counter] == 1) {
                                resetColor();
                                findViewById(R.id.redButton).setBackgroundColor(Color.RED);
                            }else if(values[counter] == 2){
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
                            counter++;
                        }

                        blinkText();
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