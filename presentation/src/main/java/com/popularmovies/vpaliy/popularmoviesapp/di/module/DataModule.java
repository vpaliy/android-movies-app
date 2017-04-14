package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.repository.MovieRepository;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.remote.MovieDatabaseAPI;
import com.popularmovies.vpaliy.data.source.remote.RemoteSource;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.util.Log;

import javax.inject.Singleton;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {



    private static final Mapper<MovieCover,Movie> MOVIE_COVER_MAPPER=new Mapper<MovieCover, Movie>() {
        @Override
        public MovieCover map(Movie movieEntity) {
            MovieCover cover=new MovieCover();
            cover.setMovieId(movieEntity.getMovieId());
            cover.setPosterPath(movieEntity.getPosterPath());
            cover.setGenres(Genre.convert(movieEntity.getGenres()));
            cover.setMovieTitle(movieEntity.getTitle());
            List<BackdropImage> backdropImages=movieEntity.getBackdropImages();
            if(backdropImages==null){
                if(movieEntity.getBackdrop_path()!=null) {
                    cover.setBackdrops(Collections.singletonList(movieEntity.getBackdrop_path()));
                }
            }else {
                cover.setBackdrops(BackdropImage.convert(backdropImages));
            }

            if (movieEntity.getReleaseDate() != null){
                cover.setReleaseYear(Integer.parseInt(movieEntity.getReleaseDate().substring(0,4)));
            }

            if(movieEntity.getRuntime()>0) {
                int hours=movieEntity.getRuntime()/60;
                String duration="";
                if(hours>0) {
                    duration = Integer.toString(hours)+" hr"+(hours>1?"s ":" ");
                }
                int minRemainder=movieEntity.getRuntime() % 60;
                if(minRemainder!=0){
                    duration+=Integer.toString(minRemainder)+" min";
                }
                cover.setDuration(duration);
            }
            return cover;
        }

        @Override
        public List<MovieCover> map(List<Movie> from) {
            if(from!=null) {
                List<MovieCover> coverList = new ArrayList<>(from.size());
                for (int index = 0; index < from.size(); index++) {
                    coverList.add(map(from.get(index)));
                }
                return coverList;
            }
            return null;
        }
    };

    private static final Mapper<ActorCover,ActorEntity> ACTOR_COVER_MAPPER= new Mapper<ActorCover, ActorEntity>() {
        @Override
        public ActorCover map(ActorEntity actorEntity) {
            ActorCover cover=new ActorCover(actorEntity.getActorId(),actorEntity.getMovieId());
            cover.setActorAvatar(actorEntity.getActorAvatar());
            cover.setRole(actorEntity.getRole());
            cover.setName(actorEntity.getName());
            return cover;
        }

        @Override
        public List<ActorCover> map(List<ActorEntity> from) {
            if(from!=null) {
                List<ActorCover> coverList = new ArrayList<>(from.size());
                for (int index = 0; index < from.size(); index++) {
                    coverList.add(map(from.get(index)));
                }
                return coverList;
            }
            return null;
        }
    };

    @Singleton
    @Provides
    Mapper<MovieCover,Movie> provideMovieCoverMapper(){
        return MOVIE_COVER_MAPPER;
    }

    @Singleton
    @Provides
    Mapper<ActorCover,ActorEntity> provideActorCoverMapper(){
        return ACTOR_COVER_MAPPER;
    }

    @Singleton
    @Provides
    Mapper<MovieDetails,MovieDetailEntity> provideMovieDetailMapper(){
        return new Mapper<MovieDetails, MovieDetailEntity>() {
            @Override
            public MovieDetails map(MovieDetailEntity detailsEntity) {
                MovieDetails movieDetails=new MovieDetails(detailsEntity.getMovieId());
                movieDetails.setSimilarMovies(MOVIE_COVER_MAPPER.map(detailsEntity.getSimilarMovies()));
                movieDetails.setCast(ACTOR_COVER_MAPPER.map(detailsEntity.getCast()));
                Movie movie=detailsEntity.getMovie();
                MovieInfo movieInfo=new MovieInfo(movie.getMovieId(),movie.getOverview());
                movieInfo.setReleaseDate(Date.valueOf(movie.getReleaseDate()));
                movieInfo.setRevenue(Long.toString(movie.getRevenue()));
                movieInfo.setBudget(Long.toString(movie.getBudget()));
                movieInfo.setAverageRate(movie.getVoteAverage());
                movieInfo.setDescription(movie.getOverview());
                movieDetails.setMovieInfo(movieInfo);
                movieDetails.setMovieCover(MOVIE_COVER_MAPPER.map(movie));
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
        };
    }


    @Singleton
    @Provides
    DataSource<Movie,MovieDetailEntity> provideRemoteSource(@NonNull MovieDatabaseAPI movieDatabaseAPI,
                                                            @NonNull ISortConfiguration configuration){
        return new RemoteSource(configuration,movieDatabaseAPI);
    }

    @Singleton
    @Provides
    IRepository<MovieCover,MovieDetails> provideRepository(MovieRepository repository){
        return repository;
    }

    @Singleton
    @Provides
    IMovieRepository<MovieCover,MovieDetails> provideMovieRepository(MovieRepository repository){
        return repository;
    }



    @Singleton
    @Provides
    ISortConfiguration provideSortConfiguration(@NonNull SortConfiguration configuration){
        return configuration;
    }

}
