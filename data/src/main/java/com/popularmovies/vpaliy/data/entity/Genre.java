package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    private int id;

    @SerializedName("genre")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
