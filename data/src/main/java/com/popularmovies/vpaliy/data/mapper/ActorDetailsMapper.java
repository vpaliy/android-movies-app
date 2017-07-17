package com.popularmovies.vpaliy.data.mapper;


import android.util.Log;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCover;

import java.util.ArrayList;
import java.util.List;

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
        this.qualityConfiguration=qualityConfiguration;
    }

    @Override
    public ActorDetails map(ActorDetailEntity actorDetailEntity) {
        ActorDetails details=new ActorDetails();
        details.setBioDescription(actorDetailEntity.getBioDescription());
        details.setBirthplace(actorDetailEntity.getBirthplace());
        details.setActorId(actorDetailEntity.getActorId());
        details.setBirthplace(actorDetailEntity.getBirthplace());
        details.setImagePaths(actorDetailEntity.getImages());
        details.setBirthday(actorDetailEntity.getBirthday());
        if(actorDetailEntity.getImages()!=null) {
            List<String> images = new ArrayList<>(actorDetailEntity.getImages().size());
            actorDetailEntity.getImages().forEach(image -> {
                images.add(qualityConfiguration.convertBackdrop(image));
            });
            details.setImages(images);
        }

        if(actorDetailEntity.getTaggedImages()!=null){
            List<String> images = new ArrayList<>(actorDetailEntity.getTaggedImages().size());
            actorDetailEntity.getTaggedImages().forEach(image -> {
                images.add(qualityConfiguration.convertBackdrop(image));
            });
            details.setTaggedImages(images);
        }
        details.setDeathday(actorDetailEntity.getDeathday());
        details.setActor(actorMapper.map(actorDetailEntity.getActorCover()));
        details.setTvShows(tvShowMapper.map(actorDetailEntity.getTvShows()));
        details.setMovies(movieMapper.map(actorDetailEntity.getMovies()));
        return details;
    }

    @Override
    public ActorDetailEntity reverseMap(ActorDetails actorDetails) {
        ActorDetailEntity detailEntity=new ActorDetailEntity();
        detailEntity.setBioDescription(actorDetails.getBioDescription());
        detailEntity.setImages(actorDetails.getImagePaths());
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
