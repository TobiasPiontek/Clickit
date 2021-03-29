package com.example.testapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    static ArrayList<Integer> values = new ArrayList<>();
    static int animationCounter;
    static int clickCounter;
    static boolean dark = false;
    static int timeToBlink;
    static int highscore = 0;
    /*
    SharedPreferences mPrefs = getSharedPreferences("label", 0);
    String mString = mPrefs.getString("tag", "default_value_if_variable_not_found");
    SharedPreferences.Editor mEditor = mPrefs.edit();
    mEditor.putString("tag", value_of_variable).commit();
    */

    static SharedPreferences sp;
    static SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetAnimation();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        findViewById(R.id.background).setBackgroundColor(Color.DKGRAY);
        sp = getSharedPreferences("FILE_NAME", MODE_PRIVATE);
        edit = sp.edit();
        try{
        SharedPreferences sp = getSharedPreferences("FILE_NAME", MODE_PRIVATE);
        highscore = sp.getInt("highscore", 0);
        ((TextView)(findViewById(R.id.highscore))).setText(Integer.toString(highscore));
        }catch(Exception e){

        }
    }

    public void StartButtonClick(View view){
        resetColor();
        resetAnimation();
        setElement();
        timeToBlink = 250;
        ((TextView) findViewById(R.id.level)).setText(Integer.toString(0));
        findViewById(R.id.startButton).setEnabled(false);
        enableButtons(false);
        drawButtonPattern();
        resetAnimation();

    }

    public void enableButtons(boolean set){
        findViewById(R.id.redButton).setEnabled(set);
        findViewById(R.id.greenButton).setEnabled(set);
        findViewById(R.id.blueButton).setEnabled(set);
        findViewById(R.id.yellowButton).setEnabled(set);
    }

    private void drawButtonPattern(){
        final Handler handler = new Handler();
        new Thread(() -> {
            try{
                Thread.sleep(timeToBlink);

            }catch (Exception e) {

            }
            handler.post(() -> {
                if(animationCounter<0){
                    animationCounter++;
                    drawButtonPattern();
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
                    drawButtonPattern();
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
                buttonAnimation(Color.GREEN,Color.DKGRAY,findViewById(R.id.background),timeToBlink*2);
                timeToBlink=(int)((double)timeToBlink*0.95);
                if(highscore < clickCounter){
                    highscore = clickCounter;
                    ((TextView) findViewById(R.id.highscore)).setText(Integer.toString(highscore));
                    edit.putInt("highscore",highscore);
                    edit.apply();

                }
                ((TextView) findViewById(R.id.level)).setText(Integer.toString(clickCounter));
                resetAnimation();
                setElement();
                enableButtons(false);
                drawButtonPattern();
            }

        }else{
            buttonAnimation(Color.RED,Color.DKGRAY,findViewById(R.id.background),timeToBlink*4);
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
        animationCounter=-4;
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