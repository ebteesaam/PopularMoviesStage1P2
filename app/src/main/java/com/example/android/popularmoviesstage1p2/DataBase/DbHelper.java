package com.example.android.popularmoviesstage1p2.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ebtesam on 19/12/2018 AD.
 */

public class DbHelper extends SQLiteOpenHelper {
    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the  table
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + MovieContract.Favorite.TABLE_NAME + " ("
                + MovieContract.Favorite._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieContract.Favorite.COLUMN_TITLE + " TEXT, "
                + MovieContract.Favorite.COLUMN_DATE + " TEXT,"
                + MovieContract.Favorite.COLUMN_IMG + " TEXT,"
                + MovieContract.Favorite.COLUMN_OVERVIEW + " TEXT,"
                + MovieContract.Favorite.COLUMN_VOTE + " TEXT,"
                +MovieContract.Favorite.COLUMN_STAR+" TEXT,"
                +MovieContract.Favorite.COLUMN_POSITION +" TEXT );";


        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.Favorite.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
