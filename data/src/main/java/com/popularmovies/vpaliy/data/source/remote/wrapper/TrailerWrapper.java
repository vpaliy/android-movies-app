package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrailerWrapper {

    @SerializedName("results")
    private List<TrailerEntity> trailers;

    public List<TrailerEntity> getTrailers() {
        if(trailers==null) return null;
        List<TrailerEntity> trailerEntities=new ArrayList<>(trailers.size());
        for(int index=0;index<trailers.size();index++){
            TrailerEntity trailerEntity=trailers.get(index);
            if(trailerEntity.isFromYoutube()){
                trailerEntities.add(trailerEntity);
            }
        }
        return trailerEntities;
    }
}
