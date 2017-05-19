package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowEpisodeMapper implements Mapper<TVShowEpisode,TvShowEpisodeEntity>{

    @Inject
    public TvShowEpisodeMapper(){}

    @Override
    public TVShowEpisode map(TvShowEpisodeEntity tvShowEpisodeEntity) {
        return null;
    }

    @Override
    public List<TVShowEpisode> map(List<TvShowEpisodeEntity> from) {
        return null;
    }

    @Override
    public TvShowEpisodeEntity reverseMap(TVShowEpisode tvShowEpisode) {
        return null;
    }
}
