package com.example.android.popularmoviesstage1p2;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ebtesam on 24/11/2018 AD.
 */

public class FetchData extends AsyncTask<Void, Void, List<Movie>> {

    String data="";
    List<Movie> moviesList;
    static List<Movie> movies;
    ArrayList<String> id;
    String dataParsed = "";
    String singleParsed ="";
    public RecyclerView.Adapter adapter;

    // JSON Keys
    private static final String MOVIE_TITLE_KEY = "title";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String PLOT_SYNOPSIS_KEY = "overview";
    private static final String USER_RATING_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected List<Movie> doInBackground(Void... voids) {
      moviesList = new ArrayList<>();
       movies=new ArrayList<>();
       id=new ArrayList<String>();
        try {
            URL url=new URL("http://api.themoviedb.org/3/movie/popular?api_key=1523ffb9e470e71f41bb8bdcf3a4a405");

            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line="";
            while (line!=null){
                line=bufferedReader.readLine();
                builder.append(line).append("\n");
                data=data+line;
            }

            if (builder.length() == 0) {
                return null;
            }
            Log.e(FetchData.this.toString(),builder.toString());
          JSONObject jsonObject=new JSONObject(builder.toString());

            Log.e(FetchData.this.toString(), builder.toString());

            // Getting JSON Array node
            JSONArray results = jsonObject.getJSONArray("results");
            Log.e(FetchData.this.toString(), String.valueOf(results.length()));
            for (int i = 0; i < results.length(); i++) {
                JSONObject c = results.getJSONObject(i);
                String img = "http://image.tmdb.org/t/p/w500/" + c.optString("poster_path");
                String title = c.optString("name");
                Log.e(FetchData.this.toString(), title);
                int vote_average = c.optInt("vote_average");
                String plot_synopsis = c.optString("overview");
                String date = c.optString("first_air_date");

                // movies.add((Movie) results.get(i));
                id.add(String.valueOf(i));
                Log.e(this.toString(), String.valueOf(i));

                Log.e(this.toString(), title + date + img + vote_average + plot_synopsis);
                moviesList.add(new Movie(title, date, img, vote_average, plot_synopsis));
            }


            movies=moviesList;
            Log.e(FetchData.this.toString(), String.valueOf(moviesList));
           // Collections.sort(movies, new MovieComparator());

                adapter=new MovieAdapter(moviesList);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return moviesList;
    }

    @Override
    protected void onPostExecute(List<Movie> aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.gridView.setAdapter(adapter);


    }

    public class MovieComparator implements Comparator<Movie>
    {
        public int compare(Movie left, Movie right) {
            return right.vote_average-left.vote_average;
        }
    }


}
