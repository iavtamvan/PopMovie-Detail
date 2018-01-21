package com.iavariav.root.popmovie.Helper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    public static final int ALL_FILM = 100;
    public static final int FILM_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_TASKS, ALL_FILM);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_TASKS + "/#", FILM_WITH_ID);

        return uriMatcher;
    }

    //1
    DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DatabaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case ALL_FILM:
                retCursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case ALL_FILM:
                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numRowsDeleted;

        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case ALL_FILM:
                numRowsDeleted = dbHelper.getWritableDatabase().delete(
                        MovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MovieContract.MovieEntry.TABLE_NAME + "'");

                break;

            case FILM_WITH_ID:
                numRowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});

                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MovieContract.MovieEntry.TABLE_NAME + "'");

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
