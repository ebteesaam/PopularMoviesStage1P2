package com.example.android.popularmoviesstage1p2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ebtesam on 28/11/2018 AD.
 */


public class DetailsActivity extends AppCompatActivity {

    ImageView img;
    TextView vote;
    TextView title;
    TextView date;
    TextView overview;
    private List<Movie> movies;
    Movie movie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        ButterKnife.bind(this);
        title=findViewById(R.id.title);
        img=findViewById(R.id.detail_img);
        vote=findViewById(R.id.vote);
        date=findViewById(R.id.date);
        overview=findViewById(R.id.overview);
        movies=new ArrayList<>();


        movie=getIntent().getParcelableExtra("index");

        Log.e(DetailsActivity.this.toString(),"#"+movie.getDate());
        title.setText(movie.getTitle());
        vote.setText(movie.getVote_average()+" ");
        date.setText(movie.getDate());
        overview.setText(movie.getPlot_synopsis());

        Picasso.with(DetailsActivity.this)
                .load(movie.getImg())
                .into(img);



    }
}
