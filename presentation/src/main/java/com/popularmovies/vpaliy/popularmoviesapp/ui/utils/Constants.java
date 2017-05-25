package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;

/**
 * Simple constants that are used across UI code
 */
public final class Constants {

    private Constants(){
        throw new UnsupportedOperationException();
    }

    public static final String MOVIES_TAG="moviesTag";
    public static final String MOVIE_DETAILS_TAG="movieDetailsTag";

    public static final String EXTRA_DATA="extra";
    public static final String EXTRA_TRANSITION_NAME="transitionName";
    public static final String EXTRA_ID="id";
    public static final String EXTRA_SORT_TYPE="sort_type";
    public static final String EXTRA_MEDIA_TYPE="media_type";


    public static final String MOVIE_URL_BASE="http://api.themoviedb.org/movie/";
}
