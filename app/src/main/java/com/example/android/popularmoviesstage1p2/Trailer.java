package com.example.android.popularmoviesstage1p2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ebtesam on 15/12/2018 AD.
 */

public class Trailer implements Parcelable{

    String Key;
    String name;


    public Trailer(String key, String name) {
        Key = key;
        this.name = name;
    }

    protected Trailer(Parcel in) {
        Key = in.readString();
        name = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Key);
        parcel.writeString(name);
    }
}
