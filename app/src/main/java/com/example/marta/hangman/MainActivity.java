package com.example.marta.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startSinglePlayerGame(View v) {
        Intent firstIntent = new Intent(this,GameActivity.class);
        //DatabaseTable.start();
        startActivity(firstIntent);

    }

    public void startMultiplayerGame(View v) {
        Intent firstIntent = new Intent(this, MultiplayerActivity.class);

        startActivity(firstIntent);
    }
}