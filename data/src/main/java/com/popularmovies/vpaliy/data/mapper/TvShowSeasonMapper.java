package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowSeasonMapper implements Mapper<TVShowSeason,TvShowSeasonEntity>{

    private final Mapper<TVShowEpisode,TvShowEpisodeEntity> episodeMapper;

    @Inject
    public TvShowSeasonMapper(@NonNull Mapper<TVShowEpisode,TvShowEpisodeEntity> episodeMapper){
        this.episodeMapper=episodeMapper;
    }

    @Override
    public TVShowSeason map(TvShowSeasonEntity tvShowSeasonEntity) {
        return null;
    }

    @Override
    public TvShowSeasonEntity reverseMap(TVShowSeason tvShowSeason) {
        return null;
    }

    @Override
    public List<TVShowSeason> map(List<TvShowSeasonEntity> from) {
        return null;
    }

}
