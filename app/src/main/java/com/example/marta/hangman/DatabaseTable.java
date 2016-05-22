package com.example.marta.hangman;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by marta on 21.05.16.
 * Hangman
 */


public class DatabaseTable {

    private static final String  TAG = "Database";

    //OUR COLUMNS
    private static final String COL_INDEX = "INDEX";
    private static final String COL_WORD = "WORD";

    private static final String DATABASE_NAME = "DICTIONARY";
    private static final String ENGLISH_TABLE = "ENGLISH";
    private static final String POLISH_TABLE = "POLISH";
   // private static final String FTS_VIRTUAL_TABLE = "FTS";

    private static final int DATABASE_VERSION = 1;

    private final DatabaseOpenHelper mDatabaseOpenHelper;

    public DatabaseTable(Context context) {
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {
        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        //creating the tables
        private static final String ENGLISH_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + ENGLISH_TABLE +
                        "USING fts3 (" +
                        COL_INDEX + ", " +
                        COL_WORD  + " )";
        private static final String POLISH_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + POLISH_TABLE +
                        "USING fts3 (" +
                        COL_INDEX + ", " +
                        COL_WORD  + " )";

        // constructor
        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(ENGLISH_TABLE_CREATE);
            mDatabase.execSQL(POLISH_TABLE_CREATE);
            loadDictionary();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " +
                    oldVersion + "to version " + newVersion +
                    "which will destroy all of the previous data");
            db.execSQL("DROP TABLE IF EXISTS " + ENGLISH_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "  + POLISH_TABLE);
            onCreate(db);
        }

        // Loading words is in a new thread so it doesn't interfere with UI
        public void loadDictionary() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        loadWords();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        // loading words from the text file
        private void loadWords() throws IOException {
            final Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.english);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    long id = addWord(i++, line);
                    if (id < 0) {
                        Log.e(TAG, "unable to add word: " + line);
                    }
                }
            }
        }

        public long addWord(int index, String word) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_INDEX, index);
            initialValues.put(COL_WORD, word);

            return mDatabase.insert(ENGLISH_TABLE, null, initialValues);
        }

    }


}
