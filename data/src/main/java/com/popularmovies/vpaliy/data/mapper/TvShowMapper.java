package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.domain.model.MediaCover;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowMapper implements Mapper<MediaCover,TvShow> {

    @Inject
    public TvShowMapper(){}

    @Override
    public MediaCover map(TvShow tvShow) {
        return null;
    }

    @Override
    public TvShow reverseMap(MediaCover mediaCover) {
        return null;
    }

    @Override
    public List<MediaCover> map(List<TvShow> from) {
        return null;
    }

}
