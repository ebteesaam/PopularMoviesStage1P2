package com.example.android.popularmoviesstage1p2;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage1p2.DataBase.MovieContract;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by ebtesam on 19/12/2018 AD.
 */

public class MovieFavoriteAdapter extends  CursorAdapter {

    private Context context;
    private List<Movie> movies;
    private Movie movie;
    public ImageView image;
    String idMovies;

    public MovieFavoriteAdapter(Context context, Cursor c) {
        super(context, c);
    }




    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list, viewGroup, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        image=view.findViewById(R.id.image);
        int uri=cursor.getColumnIndex(MovieContract.Favorite.COLUMN_IMG);
        final int id=cursor.getColumnIndex(MovieContract.Favorite.COLUMN_POSITION);

        //image.setImageURI(Uri.parse(cursor.getString(uri)));
        Log.e("Adapter", cursor.getString(uri));
        Log.e("Adapter", cursor.getString(id));

        Picasso.with(context).load(cursor.getString(uri)).into(image);

        idMovies=cursor.getString(id);

    }

//
//    public class FetchId extends AsyncTask<Void, Void, Void> {
//        String data="";
//        List<Trailer> trailers;
//        public  String name;
//        Object key;
//        String id=null;
//
//        public FetchId() {
//        }
//
//        public FetchId(String id) {
//            this.id = id;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//                URL url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=1523ffb9e470e71f41bb8bdcf3a4a405");
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuilder builder = new StringBuilder();
//
//                String line = "";
//                while (line != null) {
//                    line = bufferedReader.readLine();
//                    builder.append(line).append("\n");
//                    data = data + line;
//                }
//
//                if (builder.length() == 0) {
//                    return null;
//                }
//                Log.e(FetchId.this.toString(), builder.toString());
//                JSONObject jsonObject = new JSONObject(builder.toString());
//
//                Log.e(FetchId.this.toString(), builder.toString());
//
//                // Getting JSON Array node
//                JSONArray results = jsonObject.getJSONArray("results");
//                Log.e(FetchId.this.toString(), String.valueOf(results.length()));
//                for (int i = 0; i < results.length(); i++) {
//                    JSONObject c = results.getJSONObject(i);
//                    String idm = c.optString("id");
//                    Log.e(this.toString(), idm);
//                    Log.e("id", id);
//                    if(id.equals(idm)){
//                        Log.e("hello", idm);
//                        String img = "http://image.tmdb.org/t/p/w500/" + c.optString("poster_path");
//                        String title = c.optString("title");
//                        Log.e("title", title);
//                        int vote_average = c.optInt("vote_average");
//                        String plot_synopsis = c.optString("overview");
//                        String date = c.optString("release_date");
//
//                        key= results.get(i);
//                        // id.add(String.valueOf(i));
//                        Log.e("key" , String.valueOf(key));
//                        // https://api.themoviedb.org/3/movie/19404/vedio?api_key=1523ffb9e470e71f41bb8bdcf3a4a405&language=en-US
//                        //  http://api.themoviedb.org/3/movie/19404/videos
//                        Log.e(this.toString(), title + date + img + vote_average + plot_synopsis);
//                        Intent intent = new Intent(FetchId.this, DetailsActivity.class);
////                //String item = String.valueOf(position);
//                        intent.putExtra("index", (String[]) key);
//                        context.startActivity(intent);
//                        //moviesList.add(new Movie(title, date, img, vote_average, plot_synopsis, id));
//                    }
//                }
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                return null;
//
//            }
//        }



    }

