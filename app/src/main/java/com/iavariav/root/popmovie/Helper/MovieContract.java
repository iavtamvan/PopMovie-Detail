package com.iavariav.root.popmovie.Helper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 1/21/18.
 */

public class MovieContract {
    public static final String AUTHORITY = "com.iavariav.root.popmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_TASKS = "listfilm";

    public static final class MovieEntry implements BaseColumns{
        //Untuk Uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_TASKS)
                .build();

        public static final String TABLE_NAME = "list_favorite";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_JUDUL = "judul";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATING = "rating";
    }
}
