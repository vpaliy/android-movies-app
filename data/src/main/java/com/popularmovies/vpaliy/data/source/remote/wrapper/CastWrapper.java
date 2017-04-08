package com.popularmovies.vpaliy.data.source.remote.wrapper;


import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.ActorEntity;

import java.util.List;

public class CastWrapper {

    @SerializedName("cast")
    private List<ActorEntity> cast;

    public List<ActorEntity> getCast() {
        return cast;
    }
}
