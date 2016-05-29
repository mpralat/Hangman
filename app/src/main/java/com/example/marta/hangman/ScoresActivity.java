package com.example.marta.hangman;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        SharedPreferences preferences = getSharedPreferences("SCORES",MODE_PRIVATE);

        String scores = preferences.getString("POINTS", "No scores yet");
        TextView textViewScores = (TextView) findViewById(R.id.scoresText);

        assert textViewScores != null;
        textViewScores.setText(scores);
    }

}
