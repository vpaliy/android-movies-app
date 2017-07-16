package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.repository.CoverRepository;
import com.popularmovies.vpaliy.data.repository.DetailsRepository;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.local.LocalActorDetails;
import com.popularmovies.vpaliy.data.source.local.LocalMovieCovers;
import com.popularmovies.vpaliy.data.source.local.LocalMovieDetails;
import com.popularmovies.vpaliy.data.source.local.LocalTvShowCovers;
import com.popularmovies.vpaliy.data.source.local.LocalTvShowDetails;
import com.popularmovies.vpaliy.data.source.remote.RemoteActorDetails;
import com.popularmovies.vpaliy.data.source.remote.RemoteMovieCovers;
import com.popularmovies.vpaliy.data.source.remote.RemoteMovieDetails;
import com.popularmovies.vpaliy.data.source.remote.RemoteTvShowCovers;
import com.popularmovies.vpaliy.data.source.remote.RemoteTvShowDetails;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import com.popularmovies.vpaliy.data.source.qualifier.Local;
import com.popularmovies.vpaliy.data.source.qualifier.Remote;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;

@Module
public class DataModule {

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

    @Singleton
    @Provides
    @Remote
    DetailsDataSource<TvShowDetailEntity> provideTvDetailsRemote(RemoteTvShowDetails remoteTvShowDetails){
        return remoteTvShowDetails;
    }

    @Singleton
    @Provides
    @Local
    DetailsDataSource<TvShowDetailEntity> provideTvDetailsLocal(LocalTvShowDetails localTvShowDetails){
        return localTvShowDetails;
    }

    @Singleton
    @Provides
    @Local
    DetailsDataSource<MovieDetailEntity> provideMovieDetailsLocal(LocalMovieDetails localMovieDetails){
        return localMovieDetails;
    }

    @Singleton
    @Provides
    @Remote
    DetailsDataSource<MovieDetailEntity> provideMovieDetailsRemote(RemoteMovieDetails remoteMovieDetails){
        return remoteMovieDetails;
    }

    @Singleton
    @Provides
    @Local
    CoverDataSource<Movie> provideMovieCoverLocalSource(LocalMovieCovers localMovieCovers){
        return localMovieCovers;
    }

    @Singleton
    @Provides
    @Local
    CoverDataSource<TvShow> provideTvShowCoverLocalSource(LocalTvShowCovers localTvShowCovers){
        return localTvShowCovers;
    }

    @Singleton
    @Provides
    @Remote
    CoverDataSource<Movie> provideMovieCoverRemoteSource(RemoteMovieCovers remoteMovieCovers){
        return remoteMovieCovers;
    }

    @Singleton
    @Provides
    @Remote
    CoverDataSource<TvShow> provideTvShowDataSource(RemoteTvShowCovers remoteTvShowCovers){
        return remoteTvShowCovers;
    }

    @Singleton
    @Provides
    @Movies
    ICoverRepository<MediaCover> provideMovieCovers(CoverRepository<MediaCover,Movie> repository){
        return repository;
    }

    @Singleton
    @Provides
    @TV
    ICoverRepository<MediaCover> provideTvCovers(CoverRepository<MediaCover,TvShow> repository){
        return repository;
    }

    @Singleton
    @Provides
    IDetailsRepository<MovieDetails> provideMovieDetails(DetailsRepository<MovieDetails,MovieDetailEntity> detailsRepository){
        return detailsRepository;
    }

    @Singleton
    @Provides
    IDetailsRepository<TVShowDetails> provideTVShowDetails(DetailsRepository<TVShowDetails,TvShowDetailEntity> detailsRepository){
        return detailsRepository;
    }

    @Singleton
    @Provides
    IDetailsRepository<ActorDetails> provideActorDetails(DetailsRepository<ActorDetails,ActorDetailEntity> detailsRepository){
        return detailsRepository;
    }

    @Singleton
    @Provides
    @Remote
    DetailsDataSource<ActorDetailEntity> remoteActorDetails(RemoteActorDetails remoteActorDetails){
        return remoteActorDetails;
    }

    @Singleton
    @Provides
    @Local
    DetailsDataSource<ActorDetailEntity> localActorDetails(LocalActorDetails localActorDetails){
        return localActorDetails;
    }

}
