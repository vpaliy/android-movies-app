package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaggedImagesWrapper {

    @SerializedName("results")
    public List<Result> results;

    public static List<String> unwrap(List<Result> results){
        if(results==null) return null;
        List<String> backdrops=new ArrayList<>(results.size());
        for(Result result:results) backdrops.add(result.path);
        return backdrops;
    }

    private static class Result {
        @SerializedName("file_path")
        private String path;
    }
}
