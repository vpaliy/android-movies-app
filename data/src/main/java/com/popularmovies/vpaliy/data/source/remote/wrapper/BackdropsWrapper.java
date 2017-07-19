package com.popularmovies.vpaliy.data.source.remote.wrapper;


import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.BackdropImage;

import java.util.List;

public class BackdropsWrapper {

    @SerializedName("backdrops")
    private List<BackdropImage> backdropImages;

    @SerializedName("posters")
    private List<BackdropImage> posters;

    public List<BackdropImage> getBackdropImages() {
        return backdropImages!=null?backdropImages:posters;
    }
}
