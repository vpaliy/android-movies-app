package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.data.mapper.ActorMapper;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.mapper.MovieDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.MovieInfoMapper;
import com.popularmovies.vpaliy.data.mapper.MovieMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowEpisodeMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowSeasonMapper;
import com.popularmovies.vpaliy.data.repository.MediaRepository;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.data.source.local.MovieLocalSourceMovie;
import com.popularmovies.vpaliy.data.source.remote.RemoteMovieSource;
import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import javax.inject.Singleton;
import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import com.popularmovies.vpaliy.data.source.qualifier.MovieRemote;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton
    @Provides
    Mapper<MediaCover,Movie> provideMediaMapper(MovieMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<MediaCover,TvShow> provideMediaMapper(TvShowMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<TVShowDetails,TvShowDetailEntity> provideDetailsMapper(TvShowDetailsMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<TVShowEpisode,TvShowEpisodeEntity> provideEpisodeMapper(TvShowEpisodeMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<TVShowSeason,TvShowSeasonEntity> provideSeasonMapper(TvShowSeasonMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<MovieInfo,Movie> provideMovieInfoMapper(MovieInfoMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<ActorCover,ActorEntity> provideActorCoverMapper(ActorMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<MovieDetails,MovieDetailEntity> provideDetailsMapper(MovieDetailsMapper mapper){
        return mapper;
    }

    @Singleton
    @MovieRemote
    @Provides
    MediaDataSource<Movie,MovieDetailEntity> provideMovieRemoteSource(RemoteMovieSource remoteSource){
        return remoteSource;
    }

    @Singleton
    @MovieLocal
    @Provides
    MediaDataSource<Movie,MovieDetailEntity> provideMovieLocalSource(MovieLocalSourceMovie localSource){
        return localSource;
    }


    @Singleton
    @Provides
    IMediaRepository<MediaCover,MovieDetails> provideMovieRepository(MediaRepository<Movie,MovieDetails,MovieDetailEntity> repository){
        return repository;
    }

    @Singleton
    @Provides
    IMediaRepository<MediaCover,TVShowDetails> provideTvRepository(MediaRepository<TvShow,TVShowDetails,TvShowDetailEntity> repository){
        return repository;
    }

    @Singleton
    @Provides
    ISortConfiguration provideSortConfiguration(SortConfiguration configuration){
        return configuration;
    }

    @Singleton
    @Provides
    IImageQualityConfiguration provideImageQualityConfig(ImageQualityConfiguration qualityConfiguration){
        return qualityConfiguration;
    }

}
