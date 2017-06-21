package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.utils.MapperUtils;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.domain.model.Trailer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MovieDetailsMapper implements Mapper<MovieDetails,MovieDetailEntity> {

    private final Mapper<MediaCover,Movie> movieCoverMapper;
    private final Mapper<ActorCover,ActorEntity> actorEntityMapper;
    private final Mapper<MovieInfo,Movie> movieInfoMapper;
    private final Mapper<Review,ReviewEntity> reviewMapper;
    private final Mapper<Trailer,TrailerEntity> trailerMapper;

    @Inject
    public MovieDetailsMapper(Mapper<MediaCover,Movie> movieCoverMapper,
                              Mapper<ActorCover,ActorEntity> actorEntityMapper,
                              Mapper<MovieInfo,Movie> movieInfoMapper,
                              Mapper<Review,ReviewEntity> reviewMapper,
                              Mapper<Trailer,TrailerEntity> trailerMapper) {
        this.movieCoverMapper=movieCoverMapper;
        this.actorEntityMapper=actorEntityMapper;
        this.movieInfoMapper=movieInfoMapper;
        this.reviewMapper=reviewMapper;
        this.trailerMapper=trailerMapper;
    }

    @Override
    public MovieDetails map(MovieDetailEntity detailsEntity) {
        if(detailsEntity==null) return null;
        MovieDetails movieDetails=new MovieDetails(detailsEntity.getMovieId());
        movieDetails.setSimilarMovies(movieCoverMapper.map(detailsEntity.getSimilarMovies()));
        movieDetails.setCast(actorEntityMapper.map(detailsEntity.getCast()));
        movieDetails.setMovieInfo(movieInfoMapper.map(detailsEntity.getMovie()));
        movieDetails.setMovieCover(movieCoverMapper.map(detailsEntity.getMovie()));
        movieDetails.setTrailers(trailerMapper.map(detailsEntity.getTrailers()));
        movieDetails.setReviews(reviewMapper.map(detailsEntity.getReviews()));
        return movieDetails;

    }

    @Override
    public List<MovieDetails> map(List<MovieDetailEntity> from) {
        if(from!=null) {
            List<MovieDetails> result = new ArrayList<>(from.size());
            from.forEach(detailEntity ->result.add(map(detailEntity)));
            return result;
        }
        return null;
    }

    @Override
    public List<MovieDetailEntity> reverseMap(List<MovieDetails> from) {
        if (from != null){
            List<MovieDetailEntity> list=new ArrayList<>(from.size());
            from.forEach(details->list.add(reverseMap(details)));
            return list;
        }
        return null;
    }

    @Override
    public MovieDetailEntity reverseMap(MovieDetails details) {
        if(details==null) return null;
        MovieDetailEntity detailEntity=new MovieDetailEntity();
        Movie movie=movieCoverMapper.reverseMap(details.getMovieCover());
        Movie movieInfo=movieInfoMapper.reverseMap(details.getMovieInfo());
        if(movie!=null) {
            movie.setVoteAverage(movieInfo.getVoteAverage());
            movie.setReleaseDate(movieInfo.getReleaseDate());
            movie.setBudget(movieInfo.getBudget());
            movie.setRevenue(movieInfo.getRevenue());
            movie.setOverview(movieInfo.getOverview());
            movie.setMovieId(movieInfo.getMovieId());
        }else{
            movie=movieInfo;
        }

        if(movie!=null) {
            detailEntity.setMovie(movie);
            detailEntity.setBackdropImages(movie.getBackdropImages());
            detailEntity.setFavorite(movie.isFavorite());
        }
        detailEntity.setCast(actorEntityMapper.reverseMap(details.getCast()));
        detailEntity.setReviews(reviewMapper.reverseMap(details.getReviews()));
        detailEntity.setSimilarMovies(movieCoverMapper.reverseMap(details.getSimilarMovies()));
        detailEntity.setTrailers(trailerMapper.reverseMap(details.getTrailers()));
        return detailEntity;
    }
}
