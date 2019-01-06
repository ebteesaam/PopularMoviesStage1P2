package com.example.android.popularmoviesstage1p2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.popularmoviesstage1p2.DataBase.AppDatabase;
import com.example.android.popularmoviesstage1p2.DataBase.MainViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FavoriteAdapter.ItemClickListener{

    AppDatabase mDb;
    FavoriteAdapter favoriteAdapter;
    GridLayoutManager gridLayoutManager;
    public static RecyclerView gridView;
    private LinearLayoutManager linearLayoutManager;
    private List<Movie> movieList;
    private RecyclerView.Adapter adapter;
    String data="";
    AppDatabase mdatabase;
    FavoriteAdapter movieFavoriteAdapter;
    MenuItem option;
    private final static String MENU_SELECTED = "selected";
   // private int selected = -1;
    private int menu_selection ;
    private final static String TAG_POSITION = "callback";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle args =savedInstanceState;

            gridView = findViewById(R.id.list);
            movieList = new ArrayList<>();
            menu_selection = 0;

            gridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(MainActivity.this));
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setHasFixedSize(true);

            ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() == true) {
                final FetchData fetchData = new FetchData();
                fetchData.execute();
                setTitle("Popular Movies");


                Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();

            }

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(MENU_SELECTED))
             menu_selection = savedInstanceState.getInt(MENU_SELECTED);
             int i = savedInstanceState.getInt(MENU_SELECTED);

          onOptionsItemSelected(i);
menu_selection=i;
            Log.e("savedInstanceState ", String.valueOf(savedInstanceState.getInt(MENU_SELECTED)));
            Log.e("savedInstanceState ", String.valueOf(i));

            Log.e("savedInstanceStatemenu", String.valueOf(menu_selection));


        }
    }

    public boolean onOptionsItemSelected(int item) {

        switch (item) {
            case R.id.most_popular:
                final FetchData fetchData = new FetchData();
                fetchData.execute();
                setTitle("Popular Movies");


                return true;

            case R.id.highest_rated:

                final FetchDataHighVote fetchDataHighVote = new FetchDataHighVote();
                fetchDataHighVote.execute();
                setTitle("Highest Movies");

                return true;


            case R.id.favorite_movie:
                setTitle("Favorite Movies");
                retrieveData();

                return true;

            default:
                return false;
        }



    }


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
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);

    if (menu_selection != 0) {

        MenuItem selected = (MenuItem) menu.findItem(menu_selection);
        Log.e("onCreateOptionsMenu", String.valueOf(menu_selection));

        selected.setChecked(true);
    }

    return true;
}
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MENU_SELECTED, menu_selection);
        super.onSaveInstanceState(savedInstanceState);
        Log.e("onSaveInstanceState ", String.valueOf(menu_selection));

    }
    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        menu_selection = savedInstanceState.getInt(MENU_SELECTED);
        Log.e("onRestoreInstanceState ", String.valueOf(menu_selection));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (item.isChecked()) {
            item.setChecked(false);

        } else {
            item.setChecked(true);
            menu_selection = item.getItemId();
            Log.e("item ", String.valueOf(menu_selection));
        }

            switch (item.getItemId()) {
                case R.id.most_popular:
                    // option=menu.findItem(R.id.most_popular);
                    final FetchData fetchData = new FetchData();
                    fetchData.execute();
                    return true;

                case R.id.highest_rated:

                    final FetchDataHighVote fetchDataHighVote = new FetchDataHighVote();
                    fetchDataHighVote.execute();
                    setTitle("Highest Movies");

                    return true;


                case R.id.favorite_movie:
                    setTitle("Favorite Movies");
                    retrieveData();

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }



    }

    public void retrieveData(){
        favoriteAdapter=new FavoriteAdapter(this, (FavoriteAdapter.ItemClickListener) this);
        gridView.setAdapter(favoriteAdapter);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        //final LiveData<List<Movie>> movies=mDb.movieDao().getAll();
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies2) {

                favoriteAdapter.setMovieFav(movies2);
                Log.e("retrieveData", "Done");

            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

    }




    public class MovieComparator implements Comparator<Movie>
    {
        public int compare(Movie left, Movie right) {
            return right.vote_average-left.vote_average;
        }
    }

}
