package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import java.util.List;

import javax.inject.Inject;

public class TvShowDetailsMapper implements Mapper<TVShowDetails,TvShowDetailEntity> {

    private final Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper;

    @Inject
    public TvShowDetailsMapper(@NonNull Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper){
        this.seasonMapper=seasonMapper;
    }

    @Override
    public TVShowDetails map(TvShowDetailEntity tvShowDetailEntity) {
        return null;
    }

    @Override
    public TvShowDetailEntity reverseMap(TVShowDetails tvShowDetails) {
        return null;
    }

    @Override
    public List<TVShowDetails> map(List<TvShowDetailEntity> from) {
        return null;
    }
}
