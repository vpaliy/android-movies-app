package com.popularmovies.vpaliy.popularmoviesapp.presenter;


import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.data.utils.scheduler.ImmediateSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.domain.model.Trailer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasePresenterTest {

    static final BaseSchedulerProvider SCHEDULER_PROVIDER=new ImmediateSchedulerProvider();
    static final int FAKE_MOVIE_ID=123;
    static final MovieCover FAKE_MOVIE_COVER=new MovieCover();
    static final List<MovieCover> FAKE_EMPTY_COVER_LIST=new ArrayList<>();
    static final List<MovieCover> FAKE_COVER_LIST=Arrays.asList(FAKE_MOVIE_COVER,FAKE_MOVIE_COVER,FAKE_MOVIE_COVER);
    static final MovieDetails FAKE_MOVIE_DETAILS=new MovieDetails(FAKE_MOVIE_ID);
    static final ActorCover FAKE_ACTOR=new ActorCover(-1,FAKE_MOVIE_ID);
    static final Trailer FAKE_TRAILER=new Trailer(FAKE_MOVIE_ID,null,null);
    static final Review FAKE_REVIEW=new Review(FAKE_MOVIE_ID,null,null,null);
    static final List<Review> FAKE_REVIEWS=Arrays.asList(FAKE_REVIEW,FAKE_REVIEW,FAKE_REVIEW);
    static final List<ActorCover> FAKE_ACTORS=Arrays.asList(FAKE_ACTOR,FAKE_ACTOR,FAKE_ACTOR);
    static final List<Trailer> FAKE_TRAILERS=Arrays.asList(FAKE_TRAILER,FAKE_TRAILER,FAKE_TRAILER);
    static final MovieInfo FAKE_MOVIE_INFO=new MovieInfo(FAKE_MOVIE_ID,null);
    static final List<String> FAKE_BACKDROPS= Arrays.asList("BackdropPath1","BackdropPath2");

    static {init();}

    static void init() {
        FAKE_MOVIE_COVER.setBackdrops(FAKE_BACKDROPS);
        FAKE_MOVIE_DETAILS.setMovieCover(FAKE_MOVIE_COVER);
        FAKE_MOVIE_DETAILS.setCast(FAKE_ACTORS);
        FAKE_MOVIE_DETAILS.setTrailers(FAKE_TRAILERS);
        FAKE_MOVIE_DETAILS.setReviews(FAKE_REVIEWS);
        FAKE_MOVIE_DETAILS.setMovieInfo(FAKE_MOVIE_INFO);
        FAKE_MOVIE_DETAILS.setSimilarMovies(FAKE_COVER_LIST);
    }

}
