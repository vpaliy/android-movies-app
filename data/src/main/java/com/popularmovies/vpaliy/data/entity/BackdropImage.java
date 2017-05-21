package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;

import java.util.LinkedList;
import java.util.List;

public class BackdropImage {


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
        if(images==null) return null;
        List<String> paths=new LinkedList<>();
        images.forEach(backdropImage ->paths.add(configuration.
                convertBackdrop(backdropImage.getBackdropPath())));
        return paths;
    }

    public static List<BackdropImage> convertToBackdrops(List<String> backdrops, ImageQualityConfiguration configuration){
        if(backdrops==null) return null;
        List<BackdropImage> images=new LinkedList<>();
        backdrops.forEach(string->{
            String result=configuration.extractPath(string);
            images.add(new BackdropImage(result));
        });
        return images;
    }
}
