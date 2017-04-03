package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import android.content.Context;
import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.MovieEntity;
import com.popularmovies.vpaliy.data.entity.MovieInfoEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.repository.MovieRepository;
import com.popularmovies.vpaliy.data.repository.Repository;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.remote.FakeRemoteSource;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Review;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {


    @Singleton
    @Provides
    Mapper<MovieCover,MovieEntity> provideMovieCoverMapper(){
        return new Mapper<MovieCover, MovieEntity>() {
            @Override
            public MovieCover map(MovieEntity movieEntity) {
                MovieCover cover=new MovieCover();
                cover.setMovieId(movieEntity.getMovieId());
                cover.setPosterPath(movieEntity.getPosterPath());
                cover.setGenres(movieEntity.getGenres());
                cover.setMovieTitle(movieEntity.getMovieTitle());
                cover.setBackdrops(movieEntity.getBackdrops());
                cover.setReleaseYear(movieEntity.getReleaseYear());
                cover.setDuration(movieEntity.getDuration());
                return cover;
            }

            @Override
            public List<MovieCover> map(List<MovieEntity> from) {
                List<MovieCover> coverList=new ArrayList<>(from.size());
                for(int index=0;index<coverList.size();index++){
                    coverList.add(map(from.get(index)));
                }
                return coverList;
            }
        };
    }

    @Singleton
    @Provides
    Mapper<ActorCover,ActorEntity> provideActorCoverMapper(){
        return new Mapper<ActorCover, ActorEntity>() {
            @Override
            public ActorCover map(ActorEntity actorEntity) {
                ActorCover cover=new ActorCover(actorEntity.getActorId(),actorEntity.getMovieId());
                cover.setActorAvatar(actorEntity.getActorAvatar());
                cover.setRole(actorEntity.getRole());
                cover.setFirstName(actorEntity.getFirstName());
                cover.setLastName(actorEntity.getLastName());
                return cover;
            }

            @Override
            public List<ActorCover> map(List<ActorEntity> from) {
                List<ActorCover> coverList=new ArrayList<>(from.size());
                for(int index=0;index<coverList.size();index++){
                    coverList.add(map(from.get(index)));
                }
                return coverList;
            }
        };
    }

    @Singleton
    @Provides
    Mapper<MovieDetails,MovieDetailEntity> provideMovieDetailMapper(){
        return new Mapper<MovieDetails, MovieDetailEntity>() {
            @Override
            public MovieDetails map(MovieDetailEntity detailsEntity) {
                MovieDetails details=new MovieDetails(detailsEntity.getMovieId());
                details.setMovieId(detailsEntity.getMovieId());
                //copy actors
                List<ActorEntity> cast=detailsEntity.getCast();
                if(cast!=null) {
                    details.setCast(provideActorCoverMapper().map(detailsEntity.getCast()));
                }
                //copy cover
                details.setMovieCover(provideMovieCoverMapper().map(detailsEntity.getMovieCover()));
                //Copy reviews
                List<ReviewEntity> reviews=detailsEntity.getReviews();
                if(reviews!=null) {
                    List<Review> list=new ArrayList<>(reviews.size());
                    for(ReviewEntity reviewEntity:reviews){
                        Review review=new Review(reviewEntity.getMovieId(),reviewEntity.getAuthor(),
                                reviewEntity.getContent(),reviewEntity.getUrl());
                        list.add(review);

                    }
                    details.setReviews(list);
                }
                //copy movie info
                MovieInfoEntity infoEntity=detailsEntity.getMovieInfo();
                MovieInfo info=new MovieInfo(infoEntity.getMovieId(),infoEntity.getDescription());
                info.setAverageRate(infoEntity.getAverageRate());
                info.setBudget(infoEntity.getBudget());
                info.setReleaseDate(infoEntity.getReleaseDate());
                info.setDirector(infoEntity.getDirector());
                info.setRevenue(infoEntity.getRevenue());
                details.setMovieInfo(info);

                details.setSimilarMovies(provideMovieCoverMapper().map(detailsEntity.getSimilarMovies()));
                //TODO copy trailers

                return details;

            }

            @Override
            public List<MovieDetails> map(List<MovieDetailEntity> from) {
                List<MovieDetails> coverList=new ArrayList<>(from.size());
                for(int index=0;index<coverList.size();index++){
                    coverList.add(map(from.get(index)));
                }
                return coverList;
            }
        };
    }

    //fake remote source
    @Singleton
    @Provides
    DataSource<MovieEntity,MovieDetailEntity> provideRemoteSource(@NonNull Context context,
                                                                  @NonNull ISortConfiguration configuration){
        return new FakeRemoteSource(context,configuration);
    }

    @Singleton
    @Provides
    IRepository<MovieCover,MovieDetails> provideRepository(MovieRepository repository){
        return repository;
    }


    @Singleton
    @Provides
    ISortConfiguration provideSortConfiguration(@NonNull SortConfiguration configuration){
        return configuration;
    }

}
