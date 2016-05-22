package com.example.marta.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("POINTS",0);

        TextView scoreTextView = (TextView) findViewById(R.id.score1);
        assert scoreTextView != null;
        scoreTextView.setText(String.valueOf(score));
    }
}
