package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.Network;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowInfoMapper implements Mapper<TVShowInfo,TvShowInfoEntity> {

    @Inject
    public TvShowInfoMapper(){}

    @Override
    public TVShowInfo map(TvShowInfoEntity tvShowInfoEntity) {
        TVShowInfo info=new TVShowInfo();
        info.setTvShowId(tvShowInfoEntity.getTvShowId());
        info.setOverview(tvShowInfoEntity.getOverview());
        info.setPopularity(tvShowInfoEntity.getPopularity().doubleValue());
        info.setVoteCount(tvShowInfoEntity.getVoteCount());
        info.setAverageRate(tvShowInfoEntity.getVoteAverage().doubleValue());
        info.setEpisodeRuntime(tvShowInfoEntity.getEpisodeRuntime());
        info.setFirstAirDate(tvShowInfoEntity.getFirstAirDate());
        info.setLastAirDate(tvShowInfoEntity.getLastAirDate());
        info.setName(tvShowInfoEntity.getName());
        info.setNetworks(Network.convertToString(tvShowInfoEntity.getNetworks()));
        info.setNumberOfEpisodes(tvShowInfoEntity.getNumberOfEpisodes());
        info.setNumberOfSeasons(tvShowInfoEntity.getNumberOfSeasons());
        info.setOriginalLanguage(tvShowInfoEntity.getOriginalLanguage());
        info.setStatus(tvShowInfoEntity.getStatus());
        return info;
    }

    @Override
    public List<TvShowInfoEntity> reverseMap(List<TVShowInfo> from) {
        if(from!=null){
            List<TvShowInfoEntity> list=new ArrayList<>(from.size());
            from.forEach(info->list.add(reverseMap(info)));
            return list;
        }
        return null;
    }

    @Override
    public List<TVShowInfo> map(List<TvShowInfoEntity> from) {
        if(from==null) return null;
        List<TVShowInfo> infoList=new ArrayList<>(from.size());
        from.forEach(entity->infoList.add(map(entity)));
        return infoList;
    }

    @Override
    public TvShowInfoEntity reverseMap(TVShowInfo info) {
        TvShowInfoEntity entity=new TvShowInfoEntity();
        entity.setTvShowId(info.getTvShowId());
        entity.setOverview(info.getOverview());
        entity.setPopularity(info.getPopularity());
        entity.setVoteAverage(info.getVoteCount());
        entity.setVoteAverage(info.getAverageRate());
        entity.setEpisodeRuntime(info.getEpisodeRuntime());
        entity.setFirstAirDate(info.getFirstAirDate());
        entity.setLastAirDate(info.getLastAirDate());
        entity.setName(info.getName());
        entity.setNetworks(Network.convertToNetworks(info.getNetworks()));
        entity.setNumberOfEpisodes(info.getNumberOfEpisodes());
        entity.setNumberOfSeasons(info.getNumberOfSeasons());
        entity.setOriginalLanguage(info.getOriginalLanguage());
        entity.setStatus(info.getStatus());
        return entity;
    }

}
