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

    public static List<String> convert(List<Genre> genres){
        if(genres==null) return null;
        List<String> result=new LinkedList<>();
        genres.forEach(genre -> result.add(genre.getName()));
        return result;
    }

    public static List<Genre> convertToGenres(List<String> stringList){
        if(stringList==null) return null;
        List<Genre> genres=new LinkedList<>();
        stringList.forEach(string->genres.add(new Genre(string)));
        return genres;
    }
}
