package com.example.android.popularmoviesstage1p2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage1p2.DataBase.AppDatabase;
import com.example.android.popularmoviesstage1p2.DataBase.AppExecutor;
import com.example.android.popularmoviesstage1p2.DataBase.MovieContract;
import com.squareup.picasso.Picasso;

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
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ebtesam on 28/11/2018 AD.
 */


public class DetailsActivityF extends AppCompatActivity {
    Context context = DetailsActivityF.this;
    ImageView img;
    TextView vote;
    TextView title;
    TextView date;
    TextView overview;
    ImageView playTrailer;
    ImageView star;
    TextView reviews;
    Movie movie;
    String idMovie;
    AppDatabase mDatabase;
    private List<Movie> movies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Favorite");
        title = findViewById(R.id.title);
        img = findViewById(R.id.img);
        vote = findViewById(R.id.vote);
        date = findViewById(R.id.date);
        overview = findViewById(R.id.overview);
        playTrailer = findViewById(R.id.play);
        star = findViewById(R.id.star);
        reviews = findViewById(R.id.reviews);

        mDatabase = AppDatabase.getDatabase(this);
        movies = new ArrayList<>();
        String index = getIntent().getStringExtra("index");
        if (index != "") {
            movie = getIntent().getParcelableExtra("index");
        } else {
            movie = getIntent().getParcelableExtra("id");
        }
        Log.e(DetailsActivityF.this.toString(), "#" + movie);
        title.setText(movie.getTitle());
        vote.setText(movie.getVote_average() + " ");
        date.setText(movie.getRelease_date());
        overview.setText(movie.getPlot_synopsis());
        idMovie = movie.getIdMovie();
        https:
//api.themoviedb.org/3//movie/{id}/reviews
        Log.e(String.valueOf(DetailsActivityF.this), "https://api.themoviedb.org/3/movie/" + movie.getIdMovie() + "/videos?api_key=1523ffb9e470e71f41bb8bdcf3a4a405");
        playTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FetchTrailer fetchData = new FetchTrailer();
                fetchData.execute();
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FetchTrailer fetchReviws = new FetchTrailer(movie.getIdMovie());
                fetchReviws.execute();
                Log.e(String.valueOf(DetailsActivityF.this), "https://api.themoviedb.org/3/reviws/" + movie.getIdMovie() + "/videos?api_key=1523ffb9e470e71f41bb8bdcf3a4a405");

//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fetchReviws.Url));
//                startActivity(websiteIntent);
            }
        });
       // star.setVisibility(View.GONE);
        star.setColorFilter(getApplicationContext().getResources().getColor(R.color.yellow));
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star.setColorFilter(getApplicationContext().getResources().getColor(R.color.gray));
                final Movie movie1=new Movie(movie.getTitle(), movie.getRelease_date(),movie.getPoster_path(),movie.getVote_average(),  movie.getPlot_synopsis(), movie.getIdMovie());
//                AppExecutor executor = new AppExecutor();
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDatabase.movieDao().delete(movie1);
                            }
                        });
//
               finish();
//                    }
//                });
                finish();
                Toast.makeText(context, "Deleted from your favorite movies", Toast.LENGTH_SHORT).show();

                // insertMovie(movie.getTitle(), String.valueOf(movie.getVote_average()), movie.getPoster_path(), movie.getRelease_date(), movie.getPlot_synopsis(), "true", movie.getIdMovie());
            }
        });

        Picasso.with(DetailsActivityF.this)
                .load(movie.getPoster_path())
                .into(img);


    }

    private void insertMovie(String title, String vote, String img, String date, String overview, String star, String position) {

        // Gets the database in write mode
        // Create a ContentValues object where column names are the keys,
        //  attributes are the values.
       // if(title.equals(MovieContract.Favorite.COLUMN_TITLE))
        ContentValues values = new ContentValues();
        values.put(MovieContract.Favorite.COLUMN_TITLE, title);
        values.put(MovieContract.Favorite.COLUMN_DATE, date);
        values.put(MovieContract.Favorite.COLUMN_IMG, img);
        values.put(MovieContract.Favorite.COLUMN_OVERVIEW, overview);
        values.put(MovieContract.Favorite.COLUMN_VOTE, vote);
        values.put(MovieContract.Favorite.COLUMN_STAR, "true");

        values.put(MovieContract.Favorite.COLUMN_POSITION, position);

        // values.put(MovieContract.Favorite.COLUMN_STAR,star);
        Log.e("Insert Method ", title);
        Uri newUri = getContentResolver().insert(MovieContract.Favorite.CONTENT_URI, values);
    }

    public class FetchTrailer extends AsyncTask<Void, Void, Void> {
        public String name;
        String data = "";
        List<Trailer> trailers;
        String key;
        String id = null;

        public FetchTrailer() {
        }

        public FetchTrailer(String id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (id == null) {
                try {

//                https://api.themoviedb.org/3/movie/19404/reviews?api_key=1523ffb9e470e71f41bb8bdcf3a4a405

                    URL url = new URL("https://api.themoviedb.org/3/movie/" + idMovie + "/videos?api_key=1523ffb9e470e71f41bb8bdcf3a4a405");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder builder = new StringBuilder();

                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        builder.append(line).append("\n");
                        data = data + line;
                    }

                    if (builder.length() == 0) {
                        return null;
                    }
                    Log.e(this.toString(), builder.toString());
                    JSONObject jsonObject = new JSONObject(builder.toString());

                    Log.e(this.toString(), builder.toString());

                    // Getting JSON Array node
                    JSONObject results = jsonObject.getJSONArray("results").getJSONObject(0);
                    name = results.optString("name");
                    Log.e(this.toString(), name);
                    key = results.optString("key");
                    Log.e(this.toString(), name + key);

                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + key);

                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(websiteIntent);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return null;
            } else {
                try {
                    Log.e("Review inside", idMovie);

                    String Url;

                    URL url = new URL("https://api.themoviedb.org/3/movie/" + idMovie + "/reviews?api_key=1523ffb9e470e71f41bb8bdcf3a4a405");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder builder = new StringBuilder();

                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        builder.append(line).append("\n");
                        data = data + line;
                    }

                    if (builder.length() == 0) {
                        return null;
                    }
                    Log.e(this.toString(), builder.toString());
                    JSONObject jsonObject = new JSONObject(builder.toString());

                    Log.e(this.toString(), builder.toString());

                    // Getting JSON Array node
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() != 0) {
                        JSONObject object = results.getJSONObject(0);

                        Url = object.optString("url");
                        Log.e("Review Url", String.valueOf(results.length()));

                        Uri uri = Uri.parse(Url);
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(websiteIntent);
                    } else {
                        Log.e(String.valueOf(DetailsActivityF.this), "there is no review yet");
                        runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast = Toast.makeText(context, "there is no review yet", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });

                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return null;

            }
        }


    }
}
