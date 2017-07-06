package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.domain.model.MovieInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MovieInfoMapper extends Mapper<MovieInfo,Movie> {

    @Inject
    public MovieInfoMapper(){}

    @Override
    public MovieInfo map(Movie movie) {
        if(movie==null) return null;
        MovieInfo movieInfo=new MovieInfo(movie.getMovieId(),movie.getOverview());
        movieInfo.setReleaseDate(movie.getReleaseDate());
        movieInfo.setRevenue(Long.toString(movie.getRevenue()));
        movieInfo.setBudget(Long.toString(movie.getBudget()));
        movieInfo.setAverageRate(movie.getVoteAverage());
        movieInfo.setDescription(movie.getOverview());
        return movieInfo;
    }

    @Override
    public Movie reverseMap(MovieInfo movieInfo) {
        if(movieInfo==null) return null;
        Movie movie=new Movie();
        movie.setVoteAverage(movieInfo.getAverageRate());
        movie.setReleaseDate(movieInfo.getReleaseDate());
        movie.setBudget(Long.parseLong(movieInfo.getBudget()));
        movie.setRevenue(Long.parseLong(movieInfo.getRevenue()));
        movie.setOverview(movieInfo.getDescription());
        movie.setMovieId(movieInfo.getMovieId());
        return movie;
    }
}
