package com.popularmovies.vpaliy.data.utils;

import com.vpaliy.tmdb.model.MovieModel;

import java.util.List;

public class Constants {

    public final static String BASE_MOVIE_URL = "http://image.tmdb.org/t/p/";
    public final static String IMAGE_SIZE_W185 = "w185/";
    public final static String IMAGE_SIZE_W342 = "w342/";
    public final static String IMAGE_SIZE_W500 = "w500/";
    public final static String IMAGE_SIZE_W780 = "w780/";

    public static final String MOVIE_BACKDROPS="movieBackdrops";


    private static String appendBaseMovieURL(String path){
        if(path==null) return null;
        return BASE_MOVIE_URL+path;
    }

    public static String appendPosterSize(String posterPath){
        if(posterPath==null) return null;
        return appendBaseMovieURL(IMAGE_SIZE_W185)+posterPath;
    }

    public static String appendBackdropSize(String backdropPath){
        if(backdropPath==null) return null;
        return appendBaseMovieURL(IMAGE_SIZE_W780)+backdropPath;
    }

    public static List<MovieModel> filter(List<MovieModel> models){
        for(MovieModel model:models){
            model.setBackdrop_path(appendBackdropSize(model.getBackdrop_path()));
            model.setPoster_path(appendPosterSize(model.getPoster_path()));
        }
        return models;
    }
}
