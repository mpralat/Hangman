package com.example.marta.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main GameActivity
 */


//TODO : CUSTOM KEYBOARD
// TODO:  CHANGE LAYOUT
// TODO: 14.05.16 multiplayer? 
// TODO: 14.05.16 bluetooth 
// TODO: 14.05.16 polish version 

public class GameActivity extends Methods  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // new thread for setting filters for the editText
        new Thread(new Runnable() {
            public void run() {
                setEditText();
            }
        }).start();


        // new thread for loading the world list
        new Thread(new Runnable() {
            public void run() {
                setRandomWord();
            }
        }).start();

    }

    @Override
    public void gameOver(){

        Intent gameOverIntent = new Intent(this,GameOverActivity.class);
        gameOverIntent.putExtra("POINTS", getScore());
        startActivity(gameOverIntent);
        finish();

    }

    @Override
    public void nextTurn(){
        setScore(getScore() + 1);
        clearScreen();
        getAlreadyCheckedLetters().clear();
        setRandomWord();
    }
}
