package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.domain.model.MovieInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MovieInfoMapper implements Mapper<MovieInfo,Movie> {

    @Inject
    public MovieInfoMapper(){}

    @Override
    public List<MovieInfo> map(List<Movie> from) {
        if(from==null) return null;
        List<MovieInfo> result=new ArrayList<>(from.size());
        from.forEach(movie ->result.add(map(movie)));
        return result;
    }

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
    public List<Movie> reverseMap(List<MovieInfo> from) {
        if(from!=null){
            List<Movie> list=new ArrayList<>(from.size());
            from.forEach(movieInfo -> list.add(reverseMap(movieInfo)));
            return list;
        }
        return null;
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
