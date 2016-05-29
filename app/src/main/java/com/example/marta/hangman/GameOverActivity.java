package com.example.marta.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    int mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("POINTS",0);

        TextView scoreTextView = (TextView) findViewById(R.id.score1);
        assert scoreTextView != null;
        scoreTextView.setText(String.valueOf(score));
        mPoints = score;

    }

    public void saveScore(View v){
        SharedPreferences preferences = getSharedPreferences("SCORES", Context.MODE_PRIVATE);

        EditText nameInput = (EditText) findViewById(R.id.yourName);
        assert nameInput != null;
        String name = nameInput.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        String prevScores = preferences.getString("POINTS","");

        editor.putString("POINTS", prevScores + name + " "  + mPoints + " POINTS\n");
        editor.commit();

        finish();
    }
}
