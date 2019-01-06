package com.example.android.popularmoviesstage1p2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ebtesam on 23/12/2018 AD.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MovieFViewHolder> {

    private Context context;
    private List<Movie> movies;
    private Movie movie;
    final private ItemClickListener mItemClickListener;

    //    public MovieAdapter(Context context, List<Movie> movies) {
//        this.context = context;
//        this.movies = movies;
//    }
    public FavoriteAdapter(Context context, ItemClickListener mItemClickListener) {
        this.context = context;

        this.mItemClickListener = mItemClickListener;
    }

    public FavoriteAdapter(List<Movie> moviesList, ItemClickListener mItemClickListener) {
        this.movies = moviesList;

        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    @Override
    public MovieFViewHolder onCreateViewHolder(ViewGroup convertView, int viewType) {
        Context context = convertView.getContext();
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, convertView, false);
        MovieFViewHolder movieViewHolder = new MovieFViewHolder(view);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        // holder.image.setImageURI(Uri.parse(movie.getImg()));

        Picasso.with(context)
                .load(movie.getPoster_path())
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivityF.class);
                String item = String.valueOf(position);
                intent.putExtra("index", movie);
                context.startActivity(intent);
            }
        });
        Log.d(MovieAdapter.class.toString(), "#" + position);
        holder.bind(position);
    }



    @Override
    public int getItemCount() {
//        Log.d(MovieAdapter.class.toString(), "#" + movies.size());
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }

    public void setMovieFav(List<Movie> taskEntries) {
        Log.e("MovieAdapter Favorate", String.valueOf(movies));

        movies = taskEntries;
        notifyDataSetChanged();
    }

    public class MovieFViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public MovieFViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String elementId = movies.get(getAdapterPosition()).getIdMovie();
                    mItemClickListener.onItemClickListener(Integer.valueOf(elementId));
                }
            });
        }

        void bind(int listIndex) {
//            image.setText(listIndex);
        }
    }
}
