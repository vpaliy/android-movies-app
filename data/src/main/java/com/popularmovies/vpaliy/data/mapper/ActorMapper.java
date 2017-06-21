package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActorMapper implements Mapper<ActorCover,ActorEntity> {

    private final ImageQualityConfiguration qualityConfiguration;

    @Inject
    public ActorMapper(ImageQualityConfiguration qualityConfiguration){
        this.qualityConfiguration=qualityConfiguration;
    }
    @Override
    public ActorCover map(ActorEntity actorEntity) {
        ActorCover cover=new ActorCover(actorEntity.getActorId(),actorEntity.getMovieId());
        cover.setActorAvatar(qualityConfiguration.convertCover(actorEntity.getActorAvatar()));
        cover.setRole(actorEntity.getRole());
        cover.setName(actorEntity.getName());
        return cover;
    }

    @Override
    public List<ActorCover> map(List<ActorEntity> from) {
        if(from!=null) {
            List<ActorCover> coverList = new ArrayList<>(from.size());
            from.forEach(actorEntity -> coverList.add(map(actorEntity)));
            return coverList;
        }
        return null;
    }

    @Override
    public List<ActorEntity> reverseMap(List<ActorCover> from) {
        if(from!=null){
            List<ActorEntity> list=new ArrayList<>(from.size());
            from.forEach(actorCover -> list.add(reverseMap(actorCover)));
            return list;
        }
        return null;
    }

    @Override
    public ActorEntity reverseMap(ActorCover actorCover) {
        ActorEntity entity=new ActorEntity();
        entity.setActorId(actorCover.getActorId());
        entity.setMovieId(actorCover.getMovieId());
        entity.setActorAvatar(qualityConfiguration.extractPath(actorCover.getActorAvatar()));
        entity.setRole(actorCover.getRole());
        entity.setName(actorCover.getName());
        return entity;
    }
}
