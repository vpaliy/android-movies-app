package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.configuration.SortConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.data.mapper.ActorMapper;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.mapper.MovieDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.MovieInfoMapper;
import com.popularmovies.vpaliy.data.mapper.MovieMapper;
import com.popularmovies.vpaliy.data.mapper.ReviewMapper;
import com.popularmovies.vpaliy.data.mapper.TrailerMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowEpisodeMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowInfoMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowSeasonMapper;
import com.popularmovies.vpaliy.data.repository.CoverRepository;
import com.popularmovies.vpaliy.data.repository.DetailsRepository;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.local.LocalMovieCovers;
import com.popularmovies.vpaliy.data.source.local.LocalMovieDetails;
import com.popularmovies.vpaliy.data.source.local.LocalTvShowCovers;
import com.popularmovies.vpaliy.data.source.local.LocalTvShowDetails;
import com.popularmovies.vpaliy.data.source.remote.RemoteMovieCovers;
import com.popularmovies.vpaliy.data.source.remote.RemoteMovieDetails;
import com.popularmovies.vpaliy.data.source.remote.RemoteTvShowCovers;
import com.popularmovies.vpaliy.data.source.remote.RemoteTvShowDetails;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

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
    Mapper<MediaCover,Movie> provideMovieMediaMapper(MovieMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<MediaCover,TvShow> provideTvMediaMapper(TvShowMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<TVShowDetails,TvShowDetailEntity> provideTvDetailsMapper(TvShowDetailsMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<Review,ReviewEntity> provideReviewMapper(ReviewMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<Trailer,TrailerEntity> provideTrailerMapper(TrailerMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<TVShowEpisode,TvShowEpisodeEntity> provideEpisodeMapper(TvShowEpisodeMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<TVShowInfo,TvShowInfoEntity> provideTvShowInfoMapper(TvShowInfoMapper mapper){
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
    Mapper<MovieDetails,MovieDetailEntity> provideMovieDetailsMapper(MovieDetailsMapper mapper){
        return mapper;
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

}
