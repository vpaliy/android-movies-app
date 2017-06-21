package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class Genre {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Genre(){}

    public Genre(String name){
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
