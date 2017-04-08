package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

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

    public static List<String> convert(List<Genre> genres){
        if(genres==null) return null;
        List<String> result=new LinkedList<>();
        for(Genre genre:genres){
            result.add(genre.getName());
        }
        return result;
    }
}
