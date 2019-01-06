package com.example.android.popularmoviesstage1p2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by ebtesam on 22/11/2018 AD.
 */
@Entity(tableName = "movies")
public class Movie  implements Parcelable{
    String title;
    String release_date;
    String poster_path;
    int vote_average;
    String plot_synopsis;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String idMovie;

    @Ignore
    public Movie() {
    }

    public Movie(String title, String release_date, String poster_path, int vote_average, String plot_synopsis,String idMovie) {
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.plot_synopsis = plot_synopsis;
        this.idMovie=idMovie;
    }


    protected Movie(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        vote_average = in.readInt();
        plot_synopsis = in.readString();
        idMovie = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(poster_path);
        parcel.writeInt(vote_average);
        parcel.writeString(plot_synopsis);
        parcel.writeString(idMovie);

    }


}
