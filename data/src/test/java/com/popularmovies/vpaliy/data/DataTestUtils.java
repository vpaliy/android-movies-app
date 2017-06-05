package com.popularmovies.vpaliy.data;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;

import java.util.Arrays;
import java.util.List;

public class DataTestUtils {

    public static final int FAKE_ID=123;
    public static final int FAKER_ID=1234;
    public static final int FAKE_RUNTIME=120;
    public static final long FAKE_VOTE_COUNT=1000L;
    public static final long FAKE_BUDGET=1000000;
    public static final long FAKE_REVENUE=100000;
    public static final double FAKE_POPULARITY=10;
    public static final double FAKE_VOTE_AVERAGE=7.5d;
    public static final String FAKE_ROLE="fake_role";
    public static final String FAKE_AVATAR="fake_avatar";
    public static final String FAKE_NAME="fake_name";
    public static final String FAKE_TITLE="fake_title";
    public static final String FAKE_STATUS="fake_status";
    public static final String FAKE_OVERVIEW="fake_overview";
    public static final String FAKE_POSTER_PATH="fake_poster_path";
    public static final String FAKE_BACKDROP_PATH="fake_backdrop_path";
    public static final String FAKE_HOMEPAGE="fake_home_page";
    public static final String FAKE_RELEASE_DATE="fake_date";
    public static final String FAKE_ORIGINAL_TITLE="fake_original_title";
    public static final String FAKE_ORIGINAL_LANGUAGE="fake_language";
    public static final String FAKE_TRAILER_URL="fake_trailer_url";
    public static final String FAKE_SITE="fake_site";

    public static ActorEntity provideActorEntity(){
        ActorEntity actorEntity=new ActorEntity();
        actorEntity.setMovieId(FAKER_ID);
        actorEntity.setActorId(FAKE_ID);
        actorEntity.setRole(FAKE_ROLE);
        actorEntity.setActorAvatar(FAKE_AVATAR);
        actorEntity.setName(FAKE_NAME);
        return actorEntity;
    }

    public static BackdropImage provideBackdropImage(){
        return new BackdropImage(FAKE_BACKDROP_PATH);
    }

    public static List<BackdropImage> provideBackdrops(){
        return Arrays.asList(provideBackdropImage(),provideBackdropImage(),
                provideBackdropImage(),provideBackdropImage());
    }

    public static Genre provideGenre(){
        Genre genre=new Genre();
        genre.setId(FAKE_ID);
        genre.setName(FAKE_NAME);
        return genre;
    }

    public static List<Genre> provideGenres(){
        return Arrays.asList(provideGenre(),provideGenre(),provideGenre(),
                provideGenre(),provideGenre(),provideGenre());
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
        movie.setBackdropImages(provideBackdrops());
        movie.setGenres(provideGenres());
        return movie;
    }

    public static List<Movie> provideMovieList(){
        return Arrays.asList(provideMovieEntity(),provideMovieEntity(),
                provideMovieEntity(),provideMovieEntity(),provideMovieEntity());
    }

    public static TrailerEntity provideTrailerEntity(){
        TrailerEntity trailerEntity=new TrailerEntity();
        trailerEntity.setMovieId(FAKER_ID);
        trailerEntity.setTrailerUrl(FAKE_TRAILER_URL);
        trailerEntity.setSite(FAKE_SITE);
        trailerEntity.setTrailerId(FAKE_ID);
        trailerEntity.setTrailerTitle(FAKE_TITLE);
        return trailerEntity;
    }

    public static MovieDetailEntity provideMovieDetailsEntity(){
        MovieDetailEntity detailEntity=new MovieDetailEntity();
        detailEntity.setGenres(provideGenres());
        detailEntity.setBackdropImages(provideBackdrops());
        detailEntity.setMovie(provideMovieEntity());
        detailEntity.setSimilarMovies(provideMovieList());

        return detailEntity;
    }


}
