package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.domain.model.Trailer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrailerMapper extends Mapper<Trailer,TrailerEntity> {

    @Inject
    public TrailerMapper(){}

    @Override
    public Trailer map(TrailerEntity trailerEntity) {
        if(trailerEntity==null) return null;
        return new Trailer(trailerEntity.getMovieId(),trailerEntity.getTrailerUrl(),
                trailerEntity.getTrailerTitle());
    }

    @Override
    public TrailerEntity reverseMap(Trailer trailer) {
        TrailerEntity entity=new TrailerEntity();
        entity.setTrailerTitle(trailer.getTrailerTitle());
        entity.setTrailerUrl(trailer.getTrailerUrl());
        entity.setMovieId(trailer.getMovieId());
        return entity;
    }
}
