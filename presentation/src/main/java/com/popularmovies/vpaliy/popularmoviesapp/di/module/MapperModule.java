package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.CollectionEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.data.mapper.ActorDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.ActorMapper;
import com.popularmovies.vpaliy.data.mapper.CollectionMapper;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.mapper.MovieDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.MovieInfoMapper;
import com.popularmovies.vpaliy.data.mapper.MovieMapper;
import com.popularmovies.vpaliy.data.mapper.ReviewMapper;
import com.popularmovies.vpaliy.data.mapper.SeasonMapper;
import com.popularmovies.vpaliy.data.mapper.TrailerMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowDetailsMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowEpisodeMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowInfoMapper;
import com.popularmovies.vpaliy.data.mapper.TvShowMapper;
import com.popularmovies.vpaliy.data.mapper.SeasonCoverMapper;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.SeasonDetails;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import com.popularmovies.vpaliy.domain.model.Trailer;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class MapperModule {

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
    Mapper<MediaCollection,CollectionEntity> collectionMapper(CollectionMapper mapper){
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
    Mapper<SeasonCover,SeasonEntity> provideSeasonMapper(SeasonCoverMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<SeasonDetails,SeasonEntity> provideSeasonDetails(SeasonMapper mapper){
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
    Mapper<ActorDetails,ActorDetailEntity> provideActorMapper(ActorDetailsMapper mapper){
        return mapper;
    }

}
