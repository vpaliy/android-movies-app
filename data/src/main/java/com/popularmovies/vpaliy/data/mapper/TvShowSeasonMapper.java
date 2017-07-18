package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowSeasonMapper extends Mapper<TVShowSeason,TvShowSeasonEntity>{

    private final Mapper<TVShowEpisode,TvShowEpisodeEntity> episodeMapper;
    private ImageQualityConfiguration qualityConfiguration;

    @Inject
    public TvShowSeasonMapper(@NonNull Mapper<TVShowEpisode,TvShowEpisodeEntity> episodeMapper,
                            @NonNull ImageQualityConfiguration configuration){
        this.episodeMapper=episodeMapper;
        this.qualityConfiguration=configuration;
    }

    @Override
    public TVShowSeason map(TvShowSeasonEntity tvShowSeasonEntity) {
        TVShowSeason season=new TVShowSeason();
        season.setPosterPath(qualityConfiguration.convertBackdrop(tvShowSeasonEntity.getPosterPath()));
        season.setAirDate(tvShowSeasonEntity.getAirDate());
        season.setEpisodeList(episodeMapper.map(tvShowSeasonEntity.getEpisodes()));
        season.setSeasonId(tvShowSeasonEntity.getId());
        season.setSeasonName(tvShowSeasonEntity.getName());
        season.setSeasonNumber(tvShowSeasonEntity.getSeasonNumber());
        return season;
    }

    @Override
    public TvShowSeasonEntity reverseMap(TVShowSeason tvShowSeason) {
        TvShowSeasonEntity entity=new TvShowSeasonEntity();
        entity.setPosterPath(qualityConfiguration.extractPath(tvShowSeason.getPosterPath()));
        entity.setSeasonNumber(tvShowSeason.getSeasonNumber());
        entity.setAirDate(tvShowSeason.getAirDate());
        entity.setEpisodes(episodeMapper.reverseMap(tvShowSeason.getEpisodeList()));
        entity.setName(tvShowSeason.getSeasonName());
        entity.setId(tvShowSeason.getSeasonId());
        return entity;
    }
}
