package com.popularmovies.vpaliy.data.source;


import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.local.DatabaseUtils;
import com.popularmovies.vpaliy.data.utils.Constants;

import java.util.Arrays;
import java.util.List;

public final class DataSourceTestUtils {


    public static final String FAKE_MOVIE_ID="fake_movie_id";
    public static final int FAKE_RUNTIME=120;
    public static final long FAKE_VOTE_COUNT=1000L;
    public static final long FAKE_BUDGET=1000000;
    public static final long FAKE_REVENUE=100000;
    public static final Number FAKE_POPULARITY=10;
    public static final Number FAKE_VOTE_AVERAGE=7.5d;
    public static final String FAKE_TITLE="Logan";
    public static final String FAKE_STATUS="Some status";
    public static final String FAKE_OVERVIEW="Overview";
    public static final String FAKE_POSTER_PATH="Poster";
    public static final String FAKE_BACKDROP_PATH="Backdrop";
    public static final String FAKE_HOMEPAGE="Home page";
    public static final String FAKE_RELEASE_DATE="2017-02-28";
    public static final String FAKE_ORIGINAL_TITLE="Logan";
    public static final String FAKE_ORIGINAL_LANGUAGE="English";
    public static final boolean FAKE_IS_FAVORITE=true;


    public static final List<BackdropImage> FAKE_BACKDROPS= Arrays.asList(new BackdropImage(FAKE_POSTER_PATH),
            new BackdropImage(FAKE_POSTER_PATH),new BackdropImage(FAKE_POSTER_PATH),new BackdropImage(FAKE_POSTER_PATH));

    public static final List<Genre> FAKE_GENRES=Arrays.asList(new Genre("Action"),
            new Genre("Adventure"),new Genre("Thriller"));




    private DataSourceTestUtils(){
        throw new UnsupportedOperationException("Cannot be created!");
    }

    public static Movie provideFakeMovie(){
        Movie movie=new Movie();
        movie.setBackdropImages(FAKE_BACKDROPS);
        movie.setReleaseDate(FAKE_RELEASE_DATE);
        movie.setVoteAverage(FAKE_VOTE_AVERAGE);
        movie.setBudget(FAKE_BUDGET);
        movie.setFavorite(FAKE_IS_FAVORITE);
        movie.setMovieId(FAKE_MOVIE_ID);
        movie.setPosterPath(FAKE_POSTER_PATH);
        movie.setBackdropPath(FAKE_BACKDROP_PATH);
        movie.setGenres(FAKE_GENRES);
        movie.setVoteCount(FAKE_VOTE_COUNT);
        movie.setStatus(FAKE_STATUS);
        movie.setHomepage(FAKE_HOMEPAGE);
        movie.setOverview(FAKE_OVERVIEW);
        movie.setRuntime(FAKE_RUNTIME);
        movie.setRevenue(FAKE_REVENUE);
        movie.setPopularity(FAKE_POPULARITY);
        movie.setTitle(FAKE_TITLE);
        movie.setOriginalTitle(FAKE_ORIGINAL_TITLE);
        movie.setOriginalLanguage(FAKE_ORIGINAL_LANGUAGE);
        return movie;
    }

    public static ContentValues provideFakeValues(){
        return DatabaseUtils.convertToValues(provideFakeMovie());
    }

    public static ContentValues provideFakeValues(Movie movie){
        return DatabaseUtils.convertToValues(movie);
    }


}
