package com.example.marta.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void introduceLetter(View v) {

        EditText myEditText = (EditText) findViewById(R.id.inputLetter);

        String inputLetter = myEditText.getText().toString();
        Log.d("MYLOG","The letter is - " + inputLetter);

        if(inputLetter.length() == 1)
            checkLetter(inputLetter);
        else
            Toast.makeText(this,"Please, type in a letter!", Toast.LENGTH_SHORT).show();
    }

    public void checkLetter(String inputLetter) {
        //Toast.makeText(this,"You've typed in a letter " + inputLetter, Toast.LENGTH_SHORT).show();
    }
}
