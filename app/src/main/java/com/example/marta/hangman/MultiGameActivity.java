package com.example.marta.hangman;

import android.content.Intent;
import android.os.Bundle;

/**
 * Main GameActivity
 */


//TODO : CUSTOM KEYBOARD
// TODO:  CHANGE LAYOUT
// TODO: 14.05.16 multiplayer? 
// TODO: 14.05.16 bluetooth 
// TODO: 14.05.16 polish version 

public class MultiGameActivity extends Methods  {


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
        new Thread(new Runnable(){
            public void run() {
                setMultiWord();
            }
        }).start();

    }

    @Override
    public void gameOver(){

        Intent gameOverIntent = new Intent(this,MultiplayerActivity.class);
        startActivity(gameOverIntent);
        finish();
    }

    @Override
    public void nextTurn(){
        finish();
        clearScreen();
        getAlreadyCheckedLetters().clear();
        Intent gameOverIntent = new Intent(this,MultiplayerActivity.class);
        startActivity(gameOverIntent);
    }
}
