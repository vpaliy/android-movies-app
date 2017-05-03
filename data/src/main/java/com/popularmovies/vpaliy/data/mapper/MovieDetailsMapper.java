package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MovieDetailsMapper implements Mapper<MovieDetails,MovieDetailEntity> {

    private final Mapper<MovieCover,Movie> movieCoverMapper;
    private final Mapper<ActorCover,ActorEntity> actorEntityMapper;

    @Inject
    public MovieDetailsMapper(Mapper<MovieCover,Movie> movieCoverMapper,
                              Mapper<ActorCover,ActorEntity> actorEntityMapper) {
        this.movieCoverMapper=movieCoverMapper;
        this.actorEntityMapper=actorEntityMapper;
    }

    @Override
    public MovieDetails map(MovieDetailEntity detailsEntity) {
        MovieDetails movieDetails=new MovieDetails(detailsEntity.getMovieId());
        movieDetails.setSimilarMovies(movieCoverMapper.map(detailsEntity.getSimilarMovies()));
        movieDetails.setCast(actorEntityMapper.map(detailsEntity.getCast()));
        Movie movie=detailsEntity.getMovie();
        MovieInfo movieInfo=new MovieInfo(movie.getMovieId(),movie.getOverview());
        movieInfo.setReleaseDate(Date.valueOf(movie.getReleaseDate()));
        movieInfo.setRevenue(Long.toString(movie.getRevenue()));
        movieInfo.setBudget(Long.toString(movie.getBudget()));
        movieInfo.setAverageRate(movie.getVoteAverage());
        movieInfo.setDescription(movie.getOverview());
        movieDetails.setMovieInfo(movieInfo);
        movieDetails.setMovieCover(movieCoverMapper.map(movie));
        movieDetails.setTrailers(TrailerEntity.convert(detailsEntity.getTrailers()));
        movieDetails.setReviews(ReviewEntity.convert(detailsEntity.getReviews()));
        return movieDetails;

    }

    @Override
    public List<MovieDetails> map(List<MovieDetailEntity> from) {
        if(from!=null) {
            List<MovieDetails> coverList = new ArrayList<>(from.size());
            for (int index = 0; index < from.size(); index++) {
                coverList.add(map(from.get(index)));
            }
            return coverList;
        }
        return null;
    }


    @Override
    public MovieDetailEntity reverseMap(MovieDetails details) {
        return null;
    }
}
