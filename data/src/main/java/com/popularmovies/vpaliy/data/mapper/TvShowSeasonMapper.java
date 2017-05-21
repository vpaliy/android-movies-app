package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import java.util.ArrayList;
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
        TVShowSeason season=new TVShowSeason();
        season.setPosterPath(tvShowSeasonEntity.getPosterPath());
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
        entity.setPosterPath(tvShowSeason.getPosterPath());
        entity.setSeasonNumber(tvShowSeason.getSeasonNumber());
        entity.setAirDate(tvShowSeason.getAirDate());
        if(tvShowSeason.getEpisodeList()!=null){
            List<TvShowEpisodeEntity> episodeEntities=new ArrayList<>(tvShowSeason.getEpisodeList().size());
            tvShowSeason.getEpisodeList().forEach(tvShowEpisode -> episodeEntities.add(episodeMapper.reverseMap(tvShowEpisode)));
            entity.setEpisodes(episodeEntities);
        }
        entity.setName(tvShowSeason.getSeasonName());
        entity.setId(tvShowSeason.getSeasonId());
        return entity;
    }

    @Override
    public List<TVShowSeason> map(List<TvShowSeasonEntity> from) {
        if(from==null) return null;
        List<TVShowSeason> seasons=new ArrayList<>(from.size());
        from.forEach(tvShowSeasonEntity -> seasons.add(map(tvShowSeasonEntity)));
        return seasons;
    }

}
