package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.domain.model.Trailer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrailerMapper implements Mapper<Trailer,TrailerEntity> {

    @Inject
    public TrailerMapper(){}

    @Override
    public List<TrailerEntity> reverseMap(List<Trailer> from) {
        if(from!=null){
            List<TrailerEntity> result=new ArrayList<>(from.size());
            from.forEach(trailer -> result.add(reverseMap(trailer)));
            return result;
        }
        return null;
    }

    @Override
    public List<Trailer> map(List<TrailerEntity> from) {
        if(from!=null){
            List<Trailer> result=new ArrayList<>(from.size());
            from.forEach(trailerEntity -> result.add(map(trailerEntity)));
            return result;
        }
        return null;
    }

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
