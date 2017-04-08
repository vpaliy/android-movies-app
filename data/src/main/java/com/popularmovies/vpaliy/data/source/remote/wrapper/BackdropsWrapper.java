package com.popularmovies.vpaliy.data.source.remote.wrapper;


import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.BackdropImage;

import java.util.List;

public class BackdropsWrapper {

    @SerializedName("backdrops")
    private List<BackdropImage> backdropImages;

    public List<BackdropImage> getBackdropImages() {
        return backdropImages;
    }
}
