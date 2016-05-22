package com.example.marta.hangman;

import android.content.Context;
import android.content.res.Resources;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by marta on 21.05.16.
 * Hangman
 */
public class WordList {

    private static final String  TAG = "WordList";
    private ArrayList<String> words;


    public WordList(Context context, String filename) {

        try {
            words = new ArrayList<>();
            InputStream in = context.getResources().openRawResource(
                    context.getResources().getIdentifier(filename,
                            "raw", context.getPackageName()));

            String line;

            if(in != null) {
                InputStreamReader input = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(input);
               while( (line = br.readLine()) != null) {
                   words.add(line);
               }
            }

        } catch (IOException e) {
            Log.e(TAG, "Couldn't read the file correctly");
        }

    }

   public ArrayList<String> loadFile() {
       return this.words;
   }

}
