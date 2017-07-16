package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastImagesWrapper {

    @SerializedName("file_path")
    public List<String> images;
}
