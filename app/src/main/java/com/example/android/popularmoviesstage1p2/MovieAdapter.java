package com.example.android.popularmoviesstage1p2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ebtesam on 22/11/2018 AD.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context context;
    private List<Movie> movies;
    private Movie movie;
   // final private ItemClickListener mItemClickListener;

//    public MovieAdapter(Context context, List<Movie> movies) {
//        this.context = context;
//        this.movies = movies;
//    }
    public MovieAdapter(Context context) {
        this.context = context;

    }
    public MovieAdapter( List<Movie> moviesList) {
        this.movies = moviesList;

    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup convertView, int viewType) {
        Context context = convertView.getContext();
        this.context=context;

        View view =  LayoutInflater.from(context).inflate(R.layout.list_item, convertView, false);
        MovieViewHolder movieViewHolder=new MovieViewHolder(view);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final Movie movie=movies.get(position);
       // holder.image.setImageURI(Uri.parse(movie.getImg()));

        Picasso.with(context)
                .load(movie.getPoster_path())
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                String item = String.valueOf(position);
                intent.putExtra("index",movie);
                context.startActivity(intent);
            }
        });
        Log.d(MovieAdapter.class.toString(), "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        Log.d(MovieAdapter.class.toString(), "#" + movies.size());

        return movies.size();
    }

    public void setMovieFav(List<Movie> taskEntries) {
        movies = taskEntries;
        notifyDataSetChanged();
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;

        public MovieViewHolder(View itemView) {
            super(itemView);
           image=itemView.findViewById(R.id.image);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String elementId = movies.get(getAdapterPosition()).getIdMovie();
//                    mItemClickListener.onItemClickListener(elementId);
//                }
//            });
        }

        void bind(int listIndex) {
//            image.setText(listIndex);
        }
    }


    public class MovieFViewHolder {
    }
}
