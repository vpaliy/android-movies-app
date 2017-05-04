package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Trailer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MovieDetailsMapper implements Mapper<MovieDetails,MovieDetailEntity> {

    private final Mapper<MovieCover,Movie> movieCoverMapper;
    private final Mapper<ActorCover,ActorEntity> actorEntityMapper;
    private final Mapper<MovieInfo,Movie> movieInfoMapper;

    @Inject
    public MovieDetailsMapper(Mapper<MovieCover,Movie> movieCoverMapper,
                              Mapper<ActorCover,ActorEntity> actorEntityMapper,
                              Mapper<MovieInfo,Movie> movieInfoMapper) {
        this.movieCoverMapper=movieCoverMapper;
        this.actorEntityMapper=actorEntityMapper;
        this.movieInfoMapper=movieInfoMapper;
    }

    @Override
    public MovieDetails map(MovieDetailEntity detailsEntity) {
        if(detailsEntity==null) return null;
        MovieDetails movieDetails=new MovieDetails(detailsEntity.getMovieId());
        movieDetails.setSimilarMovies(movieCoverMapper.map(detailsEntity.getSimilarMovies()));
        movieDetails.setCast(actorEntityMapper.map(detailsEntity.getCast()));
        movieDetails.setMovieInfo(movieInfoMapper.map(detailsEntity.getMovie()));
        movieDetails.setMovieCover(movieCoverMapper.map(detailsEntity.getMovie()));
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
        if(details==null) return null;
        MovieDetailEntity detailEntity=new MovieDetailEntity();
        Movie movie=movieCoverMapper.reverseMap(details.getMovieCover());
        Movie movieInfo=movieInfoMapper.reverseMap(details.getMovieInfo());
        movie.setVoteAverage(movieInfo.getVoteAverage());
        movie.setReleaseDate(movieInfo.getReleaseDate());
        movie.setBudget(movieInfo.getBudget());
        movie.setRevenue(movieInfo.getRevenue());
        movie.setOverview(movieInfo.getOverview());
        movie.setMovieId(movieInfo.getMovieId());

        detailEntity.setMovie(movie);
        detailEntity.setBackdropImages(movie.getBackdropImages());
        detailEntity.setFavorite(movie.isFavorite());
        List<ActorCover> actorCovers=details.getCast();
        if(actorCovers!=null) {
            List<ActorEntity> actorEntities = new ArrayList<>(actorCovers.size());
            for (int index = 0; index <actorCovers.size();index++) {
                actorEntities.add(actorEntityMapper.reverseMap(actorCovers.get(index)));
            }
            detailEntity.setCast(actorEntities);
        }

        detailEntity.setReviews(ReviewEntity.convertBack(details.getReviews()));
        List<MovieCover> similarMovies=details.getSimilarMovies();
        if(similarMovies!=null){
            List<Movie> movieList=new ArrayList<>(similarMovies.size());
            for(int index=0;index<movieList.size();index++){
                movieList.add(movieCoverMapper.reverseMap(similarMovies.get(index)));
            }
            detailEntity.setSimilarMovies(movieList);
        }
        detailEntity.setTrailers(TrailerEntity.convertBack(details.getTrailers()));
        return detailEntity;
    }
}
