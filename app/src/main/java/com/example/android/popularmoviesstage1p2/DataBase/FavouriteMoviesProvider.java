package com.example.android.popularmoviesstage1p2.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.android.popularmoviesstage1p2.DataBase.MovieContract.CONTENT_AUTHORITY;
import static com.example.android.popularmoviesstage1p2.DataBase.MovieContract.Favorite.TABLE_NAME;
import static com.example.android.popularmoviesstage1p2.DataBase.MovieContract.Favorite._ID;
import static com.example.android.popularmoviesstage1p2.DataBase.MovieContract.PATH_MOVIE;

/**
 * Created by ebtesam on 19/12/2018 AD.
 */

public class FavouriteMoviesProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {

        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE, MOVIES);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE + "/#", MOVIE_ID);

    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor rCursor;
        switch (match) {
            case MOVIES:
                rCursor = db.query(
                        MovieContract.Favorite.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        orderBy
                );
                break;
            case MOVIE_ID:
                String id = uri.getPathSegments().get(1);
                rCursor = db.query(
                        MovieContract.Favorite.TABLE_NAME,
                        projection,
                        _ID + "=?",
                        new String[]{id},
                        null, null, null
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        if (getContext() != null) rCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return rCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieContract.Favorite.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieContract.Favorite.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertIMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }


    private Uri insertIMovie(Uri uri, ContentValues values) {

        // Check that the name is not null
        String title = values.getAsString(MovieContract.Favorite.COLUMN_TITLE);
        String date = values.getAsString(MovieContract.Favorite.COLUMN_DATE);
        String img = values.getAsString(MovieContract.Favorite.COLUMN_IMG);
        String overview = values.getAsString(MovieContract.Favorite.COLUMN_OVERVIEW);
        String vote = values.getAsString(MovieContract.Favorite.COLUMN_VOTE);
        String star = values.getAsString("true");
        String position=values.getAsString(MovieContract.Favorite.COLUMN_POSITION);
                //values.getAsString(MovieContract.Favorite.COLUMN_STAR);

        /// Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(MovieContract.Favorite.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e("Favorite", "Failed to insert row for " + uri);
            return null;
        }
        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Track the number of rows that were deleted
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(MovieContract.Favorite.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                // Delete a single row given by the ID in the URI
                selection = MovieContract.Favorite._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(MovieContract.Favorite.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows deleted
        return rowsDeleted;}

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
