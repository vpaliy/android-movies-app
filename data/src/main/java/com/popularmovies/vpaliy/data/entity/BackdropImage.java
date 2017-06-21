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

}
