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
    private static final String COL_NAME = "NAME";
    private static final String COL_POINTS = "POINTS";

    private static final String DATABASE_NAME = "SCORES";
    private static final String MAIN_TABLE = "MAIN_TABLE";
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
        private static final String STATS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + MAIN_TABLE +
                        "USING fts3 (" +
                        COL_NAME  + "," +
                        COL_POINTS + " )";


        // constructor
        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(STATS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " +
                    oldVersion + "to version " + newVersion +
                    "which will destroy all of the previous data");
            db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE);
            onCreate(db);
        }



        public long addScore(String name, int points) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_NAME, name);
            initialValues.put(COL_POINTS,points);

            return mDatabase.insert(MAIN_TABLE, null, initialValues);
        }

    }


}
