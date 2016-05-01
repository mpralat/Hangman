package com.example.marta.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    String ourWord = "WORD";
    EditText myEditText;
    int failedAttempts = 0;
    int goodShots = 0;
    List<Character> wrongLetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        myEditText = (EditText) findViewById(R.id.inputLetter);
        myEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    /**
     * Getting the input letter
     * @param v
     */
    public void introduceLetter(View v) {
        String inputLetter = myEditText.getText().toString();
        Log.d("MYLOG", "The letter is - " + inputLetter);

        if (inputLetter.length() == 1) {
            checkLetter(inputLetter);
            myEditText.getText().clear();
        } else
            Toast.makeText(this, "Please, type in a letter!", Toast.LENGTH_SHORT).show();
    }


    /**
     * Checking the letter typed in by a user. If the word contains this letter, it is shown on the screen.
     *
     * @param inputLetter
     */

    public void checkLetter(String inputLetter) {

        boolean letterGuessed = false;
        boolean letterRepeated = false;

        //Checking if the letter is right.
        for (int i = 0; i < ourWord.length(); i++) {
            if (ourWord.charAt(i) == inputLetter.charAt(0)) {
                Log.d("MYLOG", "its a match!");
                goodShots++;
                showCorrectLetter(i, inputLetter.charAt(0));
                letterGuessed = true;
            }
        }
        // If our word doesn't contain user's letter:
        if (!letterGuessed) {
            for (int i = 0; i < wrongLetters.size(); i++)
                if (wrongLetters.get(i) == inputLetter.charAt(0)) {
                    Toast.makeText(this, "You've already tried this one!", Toast.LENGTH_SHORT).show();
                    letterRepeated = true;
                }

            if (!letterRepeated) {
                wrongLetters.add(inputLetter.charAt(0));
                letterFailed(inputLetter.charAt(0));
                letterRepeated = false;
            }
        }

        //Checking if all letters are already guessed.
        if (goodShots == ourWord.length()) {
            //TODO SCORE +1 POINT
            clearScreen();
            //NEW WORD
        }
    }


    /**
     * Displays a guessed letter when the user's input is correct.
     *
     * @param position
     * @param guessedLetter
     */
    public void showCorrectLetter(int position, char guessedLetter) {

        LinearLayout lettersLayout = (LinearLayout) findViewById(R.id.letters);
        TextView textView = (TextView) lettersLayout.getChildAt(position);
        textView.setText(Character.toString(guessedLetter));
    }


    public void letterFailed(char inputLetter) {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        TextView failedLetters = (TextView)findViewById(R.id.textView7);
        failedLetters.append(inputLetter+", ");

        Log.d("MYLOG", "failed attempts : " + failedAttempts);
        failedAttempts++;
        if (failedAttempts < 6) {
            String mDrawableName = "hangdroid_" + failedAttempts;
            int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
            image.setImageResource(resID);
        }
        else{
            Intent gameOverIntent = new Intent(this,GameOverActivity.class);
            startActivity(gameOverIntent);
        }

    }

    public void clearScreen() {
        TextView failedLetters = (TextView)findViewById(R.id.textView7);
        failedLetters.setText("");
        failedAttempts = 0;
        goodShots = 0;

        LinearLayout lettersLayout = (LinearLayout) findViewById(R.id.letters);
        for (int i = 0; i <lettersLayout.getChildCount(); i++) {
            TextView currentTextView = (TextView) lettersLayout.getChildAt(i);
            currentTextView.setText("_");
        }

        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.hangdroid_0);
    }
}
