package com.example.android.popularmoviesstage1p2;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by ebtesam on 22/11/2018 AD.
 */

public class Movie  implements Parcelable{
    String title;
    String date;
    String img;
    int vote_average;
    String plot_synopsis;

    public Movie() {
    }

    public Movie(String title, String date, String img, int vote_average, String plot_synopsis) {
        this.title = title;
        this.date = date;
        this.img = img;
        this.vote_average = vote_average;
        this.plot_synopsis = plot_synopsis;
    }

    public Movie(Parcel parcel) {
        this.title = parcel.readString();
        this.date = parcel.readString();
        this.img = parcel.readString();
        this.vote_average = parcel.readInt();
        this.plot_synopsis = parcel.readString();
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



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
        parcel.writeString(date);
        parcel.writeString(img);
        parcel.writeInt(vote_average);
        parcel.writeString(plot_synopsis);
    }


//    @Override
//    public int compareTo(@NonNull Movie movie) {
//
//        int compare= ((Movie) movie).getVote_average();
//
//        //descending order
//        return  compare- this.vote_average;
//
//
//    }
}
