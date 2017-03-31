package com.popularmovies.vpaliy.popularmoviesapp.presentation.di.module;

import com.popularmovies.vpaliy.popularmoviesapp.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.data.entity.MovieEntity;
import com.popularmovies.vpaliy.popularmoviesapp.data.mapper.Mapper;
import com.popularmovies.vpaliy.popularmoviesapp.data.repository.Repository;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.DataSource;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.remote.FakeRemoteSource;
import com.popularmovies.vpaliy.popularmoviesapp.domain.IRepository;
import com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import rx.Observable;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import android.support.annotation.NonNull;

@Module
public class DataModule {

    @Singleton
    @Provides
    Mapper<Movie,MovieEntity> provideMovieMapper(){
        return new Mapper<Movie, MovieEntity>() {
            @Override
            public Movie map(MovieEntity movieEntity) {
                Movie movie=new Movie();
                movie.setID(movieEntity.getID());
                movie.setOriginalTitle(movieEntity.getOriginalTitle());
                movie.setPlot(movieEntity.getPlot());
                movie.setPosterPath(movieEntity.getPosterPath());
                movie.setOriginalTitle(movieEntity.getPosterPath());
                movie.setAverageVote(movieEntity.getAverageVote());
                movie.setUserRatings(movieEntity.getUserRatings());
                movie.setReleaseDate(movieEntity.getReleaseDate());
                return movie;
            }

            @Override
            public List<Movie> map(List<MovieEntity> from) {
                List<Movie> result=new ArrayList<>(from.size());
                for(MovieEntity entity:from){
                    result.add(map(entity));
                }
                return result;
            }
        };
    }

    //fake remote source
    @Singleton
    @Provides
    DataSource<MovieEntity> provideRemoteSource(@NonNull ISortConfiguration configuration){
        return new FakeRemoteSource(configuration);
    }

    @Singleton
    @Provides
    IRepository<Movie> provideRepository(Repository repository){
        return repository;
    }

    @Singleton
    @Provides
    ISortConfiguration provideSortConfiguration(@NonNull SortConfiguration configuration){
        return configuration;
    }

}
