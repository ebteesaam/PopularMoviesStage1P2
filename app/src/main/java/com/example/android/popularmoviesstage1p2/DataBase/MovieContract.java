package com.example.android.popularmoviesstage1p2.DataBase;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ebtesam on 19/12/2018 AD.
 */

public class MovieContract {

    public static final String PATH_MOVIE = "favorite";
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage1p2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public MovieContract() {
    }

    public static final class Favorite implements BaseColumns{

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;


        public final static String TABLE_NAME = "favorite";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIE);

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_IMG = "img";
        public final static String COLUMN_OVERVIEW = "overview";
        public final static String COLUMN_VOTE = "vote";
        public final static String COLUMN_STAR = "star";
        public final static String COLUMN_POSITION ="position";




    }
}
