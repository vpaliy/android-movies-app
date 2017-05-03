package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.mapper.ActorMapper;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.mapper.MovieDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.MovieMapper;
import com.popularmovies.vpaliy.data.repository.MovieRepository;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.local.MovieLocalSource;
import com.popularmovies.vpaliy.data.source.remote.RemoteSource;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import com.popularmovies.vpaliy.data.source.qualifier.MovieRemote;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton
    @Provides
    Mapper<MovieCover,Movie> provideMovieCoverMapper(@NonNull MovieMapper mapper){
        return mapper;

    }

    @Singleton
    @Provides
    Mapper<ActorCover,ActorEntity> provideActorCoverMapper(@NonNull ActorMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<MovieDetails,MovieDetailEntity> provideMovieDetailMapper(@NonNull MovieDetailsMapper mapper){
        return mapper;
    }


    @Singleton
    @MovieRemote
    @Provides
    DataSource<Movie,MovieDetailEntity> provideRemoteSource(@NonNull RemoteSource remoteSource){
        return remoteSource;
    }

    @Singleton
    @MovieLocal
    @Provides
    DataSource<Movie,MovieDetailEntity> provideLocalSource(@NonNull MovieLocalSource localSource){
        return localSource;
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

    @Singleton
    @Provides
    IImageQualityConfiguration provideImageQualityConfig(@NonNull ImageQualityConfiguration qualityConfiguration){
        return qualityConfiguration;
    }

}
