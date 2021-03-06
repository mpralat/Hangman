package com.example.marta.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by Marta on 29.05.16
 */
public abstract class Methods extends AppCompatActivity {

    private String ourWord;
    private ArrayList<String> wordsToGuess;
    private int failedAttempts = 0;
    private int guessedLetters = 0;
    private int score = 0;
    private List<Character> alreadyCheckedLetters = new ArrayList<>();

    public abstract void  gameOver() ;
    public abstract void nextTurn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
    }

    /**
     * Sets the EditText and its filters.
     */

    public void setEditText(){
        final EditText myEditText = (EditText) findViewById(R.id.inputLetter);
        assert myEditText != null;
        InputFilter[] filterArray = new InputFilter[3];             // Creating a filter array for our EditText, so it is both Caps-only and max-length = 1
        filterArray[0] = new InputFilter.LengthFilter(1);           // Important info - creating filter for uppercase overrides .maxlength in xml file
        filterArray[1] = new InputFilter.AllCaps();

        filterArray[2] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("")) { // for backspace
                    return source;
                }
                if (source.toString().matches("[A-Z]+")) {
                    return source;
                }
                myEditText.setText("");
                return "";
            }
        };
        myEditText.setFilters(filterArray);
    }

    /**
     * Getting the input letter
     * @param v view
     */

    public void introduceLetter(View v) {
        // HIDES THE SOFT KEYBOARD AFTER HITTING THE BUTTON
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        EditText myEditText = (EditText) findViewById(R.id.inputLetter);
        assert myEditText != null;
        String inputLetter = myEditText.getText().toString();

        if (inputLetter.length() == 1) {
            checkLetter(inputLetter);
            myEditText.getText().clear();
        } else
            Toast.makeText(this, "Please, type in a letter!", Toast.LENGTH_SHORT).show();
    }



    /**
     * Checking if the secret word contains the letter typed in by a user.
     *
     * @param inputLetter Letter the player introduced
     */

    public void checkLetter(String inputLetter) {

        boolean letterGuessed = false;
        boolean letterRepeated = false;

        //LETTER CORRECT
        for (int i = 0; i < getOurWord().length(); i++) {
            Log.d("MYLOG", "charat: " + i + " " + getOurWord().charAt(i));
            if (getOurWord().charAt(i) == inputLetter.charAt(0)) {

                //First, we check if it hasn't been already checked
                for (int j = 0; j < getAlreadyCheckedLetters().size(); j++)                 //Checking if the user tried the wrong letter before
                    if (getAlreadyCheckedLetters().get(j) == inputLetter.charAt(0)) {
                        Toast.makeText(this, "You've already tried this one!", Toast.LENGTH_SHORT).show();
                        letterRepeated = true;
                    }

                if(!letterRepeated) {
                    setGuessedLetters(getGuessedLetters() + 1);
                    Log.d("MYLOG", "i " + i);
                    showCorrectLetter(i, inputLetter.charAt(0));
                    letterGuessed = true;
                }
            }
        }
        if (letterGuessed) getAlreadyCheckedLetters().add(inputLetter.charAt(0));

        // LETTER NOT CORRECT
        else {
            for (int i = 0; i < getAlreadyCheckedLetters().size(); i++)                 //Checking if the user tried the wrong letter before
                if (getAlreadyCheckedLetters().get(i) == inputLetter.charAt(0)) {
                    Toast.makeText(this, "You've already tried this one!", Toast.LENGTH_SHORT).show();
                    letterRepeated = true;
                }

            if (!letterRepeated) {
                getAlreadyCheckedLetters().add(inputLetter.charAt(0));
                letterFailed(inputLetter.charAt(0));
            }
        }
        //Checking if all letters are already guessed.
        if (getGuessedLetters() == getOurWord().length())
            nextTurn();

    }


    /**
     * Displays a guessed letter when the user's input is correct.
     *
     * @param position Position on which the letter is in given word.
     * @param guessedLetter Letter guessed by a player.
     */
    public void showCorrectLetter(int position, char guessedLetter) {

        LinearLayout lettersLayout = (LinearLayout) findViewById(R.id.letters);
        TextView letterGaps = (TextView)findViewById(R.id.letter1);
        assert letterGaps != null;
        String input = letterGaps.getText().toString();
        char[] letters = new char[input.length()];
        Log.d("MYLOG", " length : " + input.length());

        for (int i = 0; i < input.length(); i++)
            letters[i] = input.charAt(i);

        letters[2*position] = guessedLetter;
        input = new String(letters);
        letterGaps.setText(input);
    }

    /**
     * Used when the input letter is not correct.
     * Changes the image and counts the failed attempts.
     * @param inputLetter Letter introduced by a player.
     */
    public void letterFailed(char inputLetter) {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        TextView failedLetters = (TextView)findViewById(R.id.textView7);
        assert failedLetters != null;
        failedLetters.append(inputLetter + ", ");
        setFailedAttempts(getFailedAttempts() + 1);

        if (getFailedAttempts() < 6) {
            String mDrawableName = "hangdroid_" + getFailedAttempts();
            int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
            assert image != null;
            image.setImageResource(resID);
        }
        //GAME OVER
        else{
//            finish();
//            Intent gameOverIntent = new Intent(this,GameOverActivity.class);
//            gameOverIntent.putExtra("POINTS",getScore());
//            startActivity(gameOverIntent);
            gameOver();
        }

    }



    /**
     * Clears the screen and counters after loosing or winning the game, so it can be started over again.
     */
    public void clearScreen() {
        TextView failedLetters = (TextView)findViewById(R.id.textView7);
        assert failedLetters != null;
        failedLetters.setText("");
        setFailedAttempts(0);
        setGuessedLetters(0);

        TextView scoreIndicator = (TextView)findViewById(R.id.score);
        Log.d("MYLOG", "ups ?" + getScore());
        assert scoreIndicator != null;
        scoreIndicator.setText("Your score: " + Integer.toString(getScore()));

        TextView letterGaps = (TextView)findViewById(R.id.letter1);
        assert letterGaps != null;
        letterGaps.setText("");

        ImageView image = (ImageView) findViewById(R.id.imageView);
        assert image != null;
        image.setImageResource(R.drawable.hangdroid_0);
    }

    /**
     * Sets the word to be guessed.
     */
    public void setRandomWord(){
        String filename = "english";
        WordList words = new WordList(this.getBaseContext(), filename);
        setWordsToGuess(words.loadFile());

        String randomStr = getWordsToGuess().get(new Random().nextInt(getWordsToGuess().size())).toUpperCase();
        Log.d("MYLOG", randomStr);
        Log.d("MYLOG", String.valueOf(randomStr.length()));

        TextView letterGaps = (TextView)findViewById(R.id.letter1);
        for (int i = 0; i < randomStr.length(); i++) {
            assert letterGaps != null;
            letterGaps.append("_ ");
        }

        setOurWord(randomStr);
    }

    /**
     * Sets the word to be guessed.
     */
    public void setMultiWord(){

        String multiStr = getIntent().getStringExtra("WORD_IDENTIFIER").toUpperCase();

        Log.d("MYLOG", multiStr);
        Log.d("MYLOG", String.valueOf(multiStr.length()));

        TextView letterGaps = (TextView)findViewById(R.id.letter1);
        for (int i = 0; i < multiStr.length(); i++) {
            assert letterGaps != null;
            letterGaps.append("_ ");
        }

        setOurWord(multiStr);
    }

    /**********************SETTERS AND GETTERS**************************/

    // failedAttempts
    public int getFailedAttempts() {
        return failedAttempts;
    }
    public void setFailedAttempts(int i) {
        failedAttempts = i;
    }

    //guessedLetters
    public int getGuessedLetters() {
        return guessedLetters;
    }
    public void setGuessedLetters(int i) {
        guessedLetters = i;
    }

    //score
    public int getScore() {
        return score;
    }
    public void setScore(int i) {
        score = i;
    }

    //alreadyCheckedLetters - list
    public List<Character> getAlreadyCheckedLetters() { return alreadyCheckedLetters; }
    public void setAlreadyCheckedLetters(List<Character> list) { this.alreadyCheckedLetters = list; }

    //wordsToGuess
    public ArrayList<String> getWordsToGuess() {return wordsToGuess;}
    public void setWordsToGuess(ArrayList<String> list) {this.wordsToGuess = list; }

    //ourWord
    public String getOurWord() {return ourWord;}
    public void setOurWord(String word) {this.ourWord = word; }

}
