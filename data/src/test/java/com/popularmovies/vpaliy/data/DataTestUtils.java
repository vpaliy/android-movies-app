package com.popularmovies.vpaliy.data;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.local.MoviesContract;

public class DataTestUtils {

    public static final int FAKE_ID=123;
    public static final int FAKER_ID=1234;
    public static final String FAKE_ROLE="fake_role";
    public static final String FAKE_AVATAR="fake_avatar";
    public static final String FAKE_NAME="fake_name";
    public static final int FAKE_RUNTIME=120;
    public static final long FAKE_VOTE_COUNT=1000L;
    public static final long FAKE_BUDGET=1000000;
    public static final long FAKE_REVENUE=100000;
    public static final double FAKE_POPULARITY=10;
    public static final double FAKE_VOTE_AVERAGE=7.5d;
    public static final String FAKE_TITLE="fake_title";
    public static final String FAKE_STATUS="fake_status";
    public static final String FAKE_OVERVIEW="fake_overview";
    public static final String FAKE_POSTER_PATH="fake_poster_path";
    public static final String FAKE_BACKDROP_PATH="fake_backdrop_path";
    public static final String FAKE_HOMEPAGE="fake_home_page";
    public static final String FAKE_RELEASE_DATE="fake_date";
    public static final String FAKE_ORIGINAL_TITLE="fake_original_title";
    public static final String FAKE_ORIGINAL_LANGUAGE="fake_language";

    public static ActorEntity provideActorEntity(){
        ActorEntity actorEntity=new ActorEntity();
        actorEntity.setMovieId(FAKER_ID);
        actorEntity.setActorId(FAKE_ID);
        actorEntity.setRole(FAKE_ROLE);
        actorEntity.setActorAvatar(FAKE_AVATAR);
        actorEntity.setName(FAKE_NAME);
        return actorEntity;
    }

    public static Movie provideMovieEntity(){
        Movie movie=new Movie();
        movie.setPosterPath(FAKE_POSTER_PATH);
        movie.setMovieId(FAKE_ID);
        movie.setReleaseDate(FAKE_RELEASE_DATE);
        movie.setTitle(FAKE_TITLE);
        movie.setOriginalTitle(FAKE_ORIGINAL_TITLE);
        movie.setStatus(FAKE_STATUS);
        movie.setBudget(FAKE_BUDGET);
        movie.setRuntime(FAKE_RUNTIME);
        movie.setVoteCount(FAKE_VOTE_COUNT);
        movie.setVoteAverage(FAKE_VOTE_AVERAGE);
        movie.setOverview(FAKE_OVERVIEW);
        movie.setBackdropPath(FAKE_BACKDROP_PATH);
        movie.setHomepage(FAKE_HOMEPAGE);
        movie.setOriginalLanguage(FAKE_ORIGINAL_LANGUAGE);
        movie.setRevenue(FAKE_REVENUE);
        movie.setPopularity(FAKE_POPULARITY);
        return movie;
    }
}
