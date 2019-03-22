package com.example.android.popularmoviesstage1p2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static RecyclerView gridView;
    private LinearLayoutManager linearLayoutManager;
    private List<Movie> movieList;
    private RecyclerView.Adapter adapter;
    String data="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.list);
        movieList = new ArrayList<>();
          GridLayoutManager gridLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(MainActivity.this));
         gridView.setLayoutManager(gridLayoutManager);
        gridView.setHasFixedSize(true);

        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true )
        {
            final FetchData fetchData = new FetchData();
            fetchData.execute();

            Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.most_popular:
                final FetchData fetchData = new FetchData();
                fetchData.execute();
                return true;
            case R.id.highest_rated:

                final FetchDataHighVote fetchDataHighVote = new FetchDataHighVote();
                fetchDataHighVote.execute();
               //Log.e(MainActivity.this.toString(),FetchData.movies.toString());
                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    public class MovieComparator implements Comparator<Movie>
    {
        public int compare(Movie left, Movie right) {
            return right.vote_average-left.vote_average;
        }
    }

}
