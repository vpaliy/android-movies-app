package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCover;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActorDetailsMapper extends Mapper<ActorDetails,ActorDetailEntity>  {

    private Mapper<MediaCover,TvShow> tvShowMapper;
    private Mapper<MediaCover,Movie> movieMapper;
    private Mapper<ActorCover,ActorEntity> actorMapper;

    private ImageQualityConfiguration qualityConfiguration;

    @Inject
    public ActorDetailsMapper(Mapper<MediaCover,TvShow> tvShowMapper,
                              Mapper<MediaCover,Movie> movieMapper,
                              Mapper<ActorCover,ActorEntity> actorMapper,
                              ImageQualityConfiguration qualityConfiguration){
        this.movieMapper=movieMapper;
        this.tvShowMapper=tvShowMapper;
        this.actorMapper=actorMapper;
    }

    @Override
    public ActorDetails map(ActorDetailEntity actorDetailEntity) {
        ActorDetails details=new ActorDetails();
        details.setBioDescription(actorDetailEntity.getBioDescription());
        details.setBirthplace(actorDetailEntity.getBirthplace());
        details.setActorId(actorDetailEntity.getActorId());
        details.setBirthplace(actorDetailEntity.getBirthplace());
        details.setImagePaths(actorDetailEntity.getImagePaths());
        details.setBirthday(actorDetailEntity.getBirthday());
        details.setImages(actorDetailEntity.getImages());
        details.setDeathday(actorDetailEntity.getDeathday());
        details.setActor(actorMapper.map(actorDetailEntity.getActor()));
        details.setTvShows(tvShowMapper.map(actorDetailEntity.getTvShows()));
        details.setMovies(movieMapper.map(actorDetailEntity.getMovies()));
        return details;
    }

    @Override
    public ActorDetailEntity reverseMap(ActorDetails actorDetails) {
        ActorDetailEntity detailEntity=new ActorDetailEntity();
        detailEntity.setBioDescription(actorDetails.getBioDescription());
        detailEntity.setImagePaths(actorDetails.getImagePaths());
        detailEntity.setMovies(movieMapper.reverseMap(actorDetails.getMovies()));
        detailEntity.setTvShows(tvShowMapper.reverseMap(actorDetails.getTvShows()));
        detailEntity.setDeathday(actorDetails.getDeathday());
        detailEntity.setBirthplace(actorDetails.getBirthplace());
        detailEntity.setBirthday(actorDetails.getBirthday());
        detailEntity.setActorId(actorDetails.getActorId());
        detailEntity.setActor(actorMapper.reverseMap(actorDetails.getActor()));
        return detailEntity;
    }
}
