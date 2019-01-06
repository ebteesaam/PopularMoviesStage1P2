package com.example.android.popularmoviesstage1p2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.android.popularmoviesstage1p2.DataBase.AppDatabase;
import com.example.android.popularmoviesstage1p2.DataBase.AppExecutor;

import java.util.List;

/**
 * Created by ebtesam on 24/12/2018 AD.
 */

public class Favorite extends AppCompatActivity implements FavoriteAdapter.ItemClickListener {


    public static RecyclerView gridView;
AppDatabase mDb;
FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(Favorite.this));
        gridView.setLayoutManager(gridLayoutManager);
        gridView.setHasFixedSize(true);

        favoriteAdapter=new FavoriteAdapter(this,this);
        gridView.setAdapter(favoriteAdapter);
        mDb = AppDatabase.getDatabase(getApplicationContext());
retrieveData();

    }
    public void retrieveData(){
        final LiveData<List<Movie>> movies=mDb.movieDao().getAll();
        movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies2) {

                favoriteAdapter.setMovieFav(movies2);


            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
        // COMPLETED (3) Call the adapter's setTasks method using the result
        // of the loadAllTasks method from the taskDao
      //  AppExecutor executor=new AppExecutor();


//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        AppExecutor.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                final List<Movie> movies=mDb.movieDao().getAll();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        favoriteAdapter.setMovieFav(movies);
//                    }
//                });
//
//            }
//        });
      //  favoriteAdapter.setMovieFav(mDb.movieDao().getAll());
 //   }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
    @Override
    public void onItemClickListener(int itemId) {

    }
}
