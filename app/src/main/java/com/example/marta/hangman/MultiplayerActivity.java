package com.example.marta.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MultiplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

//        // new thread for setting filters for the editText
//        new Thread(new Runnable() {
//            public void run() {
//                setEditText();
//            }
//        }).start();


//        // new thread for loading the world list
//        new Thread(new Runnable() {
//            public void run() {
//                setWord();
//            }
//        }).start();

    }

    public void multiGameStart(View v){
        EditText getWord = (EditText)findViewById(R.id.editTextWord);
        assert getWord != null;
        String multiStr = getWord.getText().toString();

        Intent myIntent = new Intent(this, MultiGameActivity.class);
        myIntent.putExtra("WORD_IDENTIFIER",multiStr);

        startActivity(myIntent);

    }

}


