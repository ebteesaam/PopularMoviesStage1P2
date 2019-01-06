package com.example.android.popularmoviesstage1p2.DataBase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.android.popularmoviesstage1p2.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAll();

//    @Query("SELECT movieId FROM movie WHERE movieId = :id")
//    MiniMovie getMovieById(String id);
@Update(onConflict = OnConflictStrategy.REPLACE)
void update(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);
}