package com.popularmovies.vpaliy.data.entity;


import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class BackdropImage {

    private static final String baseImageUrl="http://image.tmdb.org/t/p/w780/";
    private static final String TAG=BackdropImage.class.getSimpleName();

    @SerializedName("file_path")
    private String backdropPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    public static List<String> convert(List<BackdropImage> images){
        List<String> paths=new LinkedList<>();
        for(BackdropImage image:images){
            paths.add(baseImageUrl+image.getBackdropPath());
            Log.d(TAG,baseImageUrl+image.getBackdropPath());
        }
        return paths;
    }
}
