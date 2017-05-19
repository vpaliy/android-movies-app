package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.mapper.ActorMapper;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.mapper.MovieDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.MovieInfoMapper;
import com.popularmovies.vpaliy.data.mapper.MovieMapper;
import com.popularmovies.vpaliy.data.repository.MovieRepository;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.data.source.local.MovieLocalSourceMovie;
import com.popularmovies.vpaliy.data.source.remote.RemoteMovieSource;
import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;

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
    Mapper<MovieInfo,Movie> provideMovieInfoMapper(@NonNull MovieInfoMapper mapper){
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
    MediaDataSource<Movie,MovieDetailEntity> provideRemoteSource(@NonNull RemoteMovieSource remoteSource){
        return remoteSource;
    }

    @Singleton
    @MovieLocal
    @Provides
    MediaDataSource<Movie,MovieDetailEntity> provideLocalSource(@NonNull MovieLocalSourceMovie localSource){
        return localSource;
    }


    @Singleton
    @Provides
    IMediaRepository<MovieCover,MovieDetails> provideMovieRepository(MovieRepository repository){
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
