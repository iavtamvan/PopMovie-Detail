package com.iavariav.root.popmovie.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 1/21/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popular_movie.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+MovieContract.MovieEntry.TABLE_NAME+"(" +
                MovieContract.MovieEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_ID+" INTEGER, " +
                MovieContract.MovieEntry.COLUMN_JUDUL+" TEXT, " +
                MovieContract.MovieEntry.COLUMN_POSTER+" TEXT, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW+" TEXT, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE+" TEXT, " +
                "UNIQUE ("+MovieContract.MovieEntry.COLUMN_JUDUL+") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
