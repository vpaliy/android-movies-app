package com.popularmovies.vpaliy.data.entity;


import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;

import java.util.LinkedList;
import java.util.List;

public class BackdropImage {

    private static final String TAG=BackdropImage.class.getSimpleName();

    @SerializedName("file_path")
    private String backdropPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    public BackdropImage(){}


    public BackdropImage(String backdropPath){
        this.backdropPath=backdropPath;
    }

    public static List<String> convert(List<BackdropImage> images, ImageQualityConfiguration configuration){
        List<String> paths=new LinkedList<>();
        for(BackdropImage image:images){
            paths.add(configuration.convertBackdrop(image.getBackdropPath()));
            Log.d(TAG,configuration.convertBackdrop(image.getBackdropPath()));
        }
        return paths;
    }

    public static List<BackdropImage> convertToBackdrops(List<String> backdrops, ImageQualityConfiguration configuration){
        if(backdrops==null) return null;
        List<BackdropImage> images=new LinkedList<>();
        for(String image:backdrops){
            String result=configuration.extractPath(image);
            Log.d(TAG,result);
            images.add(new BackdropImage(result));
        }
        return images;
    }
}
