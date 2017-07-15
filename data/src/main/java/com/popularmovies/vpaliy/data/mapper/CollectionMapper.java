package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.CollectionEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CollectionMapper extends Mapper<MediaCollection,CollectionEntity> {

    private Mapper<MediaCover,Movie> mapper;

    @Inject
    public CollectionMapper(Mapper<MediaCover,Movie> mapper){
        this.mapper=mapper;
    }

    @Override
    public MediaCollection map(CollectionEntity collectionEntity) {
        if(collectionEntity==null) return null;
        MediaCollection collection=new MediaCollection();
        collection.setBackdrop(collectionEntity.getBackdropPath());
        collection.setCovers(mapper.map(collectionEntity.getParts()));
        collection.setId(collectionEntity.getId());
        collection.setName(collectionEntity.getName());
        collection.setOverview(collectionEntity.getOverview());
        return collection;
    }

    @Override
    public CollectionEntity reverseMap(MediaCollection mediaCollection) {
        if(mediaCollection==null) return null;
        CollectionEntity entity=new CollectionEntity();
        entity.setParts(mapper.reverseMap(mediaCollection.getCovers()));
        entity.setBackdropPath(mediaCollection.getBackdrop());
        entity.setOverview(mediaCollection.getOverview());
        entity.setName(mediaCollection.getName());
        entity.setId(mediaCollection.getId());
        return entity;
    }
}
