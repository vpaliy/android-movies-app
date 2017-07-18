package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;
import android.util.Log;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.domain.model.SeasonCover;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SeasonCoverMapper extends Mapper<SeasonCover,SeasonEntity>{

    private ImageQualityConfiguration qualityConfiguration;

    @Inject
    public SeasonCoverMapper(@NonNull ImageQualityConfiguration configuration){
        this.qualityConfiguration=configuration;
    }

    @Override
    public SeasonCover map(SeasonEntity seasonEntity) {
        SeasonCover season=new SeasonCover();
        season.setPosterPath(qualityConfiguration.convertBackdrop(seasonEntity.getPosterPath()));
        season.setAirDate(seasonEntity.getAirDate());
        season.setSeasonId(seasonEntity.getId());
        season.setSeasonName(seasonEntity.getName());
        season.setOverview(seasonEntity.getOverview());
        season.setSeasonNumber(seasonEntity.getSeasonNumber());
        return season;
    }

    @Override
    public SeasonEntity reverseMap(SeasonCover tvShowSeason) {
        SeasonEntity entity=new SeasonEntity();
        entity.setPosterPath(qualityConfiguration.extractPath(tvShowSeason.getPosterPath()));
        entity.setSeasonNumber(tvShowSeason.getSeasonNumber());
        entity.setAirDate(tvShowSeason.getAirDate());
        entity.setName(tvShowSeason.getSeasonName());
        entity.setOverview(tvShowSeason.getOverview());
        entity.setId(tvShowSeason.getSeasonId());
        return entity;
    }
}
