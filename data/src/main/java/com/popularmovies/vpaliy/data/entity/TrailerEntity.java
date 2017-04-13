package com.popularmovies.vpaliy.data.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.domain.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerEntity {

    private int movieId;

    @SerializedName("name")
    private String trailerTitle;

    @SerializedName("key")
    private String trailerUrl;


    public int getMovieId() {
        return movieId;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public static List<Trailer> convert(@NonNull List<TrailerEntity> list){
        List<Trailer> result=new ArrayList<>(list.size());
        for(TrailerEntity entity:list){
            Trailer trailer=new Trailer(entity.getMovieId(),entity.getTrailerUrl(),entity.getTrailerTitle());
            result.add(trailer);
        }
        return result;
    }

}
