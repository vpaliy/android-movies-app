package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CastImagesWrapper {

    @SerializedName("profiles")
    public List<Profile> images;

    public static List<String> unwrap(List<Profile> profiles){
        if(profiles==null) return null;
        List<String> paths=new ArrayList<>(profiles.size());
        for(Profile profile:profiles) paths.add(profile.path);
        return paths;
    }


    public static class Profile {
        @SerializedName("file_path")
        private String path;
    }
}
