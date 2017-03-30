package com.popularmovies.vpaliy.popularmoviesapp.presentation.di.module;


import com.popularmovies.vpaliy.popularmoviesapp.data.entity.MovieEntity;
import com.popularmovies.vpaliy.popularmoviesapp.data.mapper.Mapper;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

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
                movie.setUserRatings(movieEntity.getUserRatings());
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


}
