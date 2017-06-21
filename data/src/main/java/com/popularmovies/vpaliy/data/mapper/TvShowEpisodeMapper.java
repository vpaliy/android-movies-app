package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowEpisodeMapper implements Mapper<TVShowEpisode,TvShowEpisodeEntity>{

    @Inject
    public TvShowEpisodeMapper(){}

    @Override
    public TVShowEpisode map(TvShowEpisodeEntity tvShowEpisodeEntity) {
        if(tvShowEpisodeEntity==null) return null;
        TVShowEpisode episode=new TVShowEpisode();
        episode.setEpisodeId(tvShowEpisodeEntity.getId());
        episode.setEpisodeName(tvShowEpisodeEntity.getName());
        episode.setEpisodeNumber(tvShowEpisodeEntity.getEpisodeNumber());
        episode.setEpisodeOverview(tvShowEpisodeEntity.getOverview());
        episode.setVoteAverage(tvShowEpisodeEntity.getVoteAverage().doubleValue());
        episode.setVoteCount(tvShowEpisodeEntity.getVoteCount());
        return episode;
    }

    @Override
    public List<TvShowEpisodeEntity> reverseMap(List<TVShowEpisode> from) {
        if(from!=null){
            List<TvShowEpisodeEntity> list=new ArrayList<>(from.size());
            from.forEach(tvShowEpisode -> list.add(reverseMap(tvShowEpisode)));
            return list;
        }
        return null;
    }

    @Override
    public List<TVShowEpisode> map(List<TvShowEpisodeEntity> from) {
        if(from==null) return null;
        List<TVShowEpisode> episodes=new ArrayList<>(from.size());
        from.forEach(tvShowEpisodeEntity -> episodes.add(map(tvShowEpisodeEntity)));
        return episodes;
    }

    @Override
    public TvShowEpisodeEntity reverseMap(TVShowEpisode tvShowEpisode) {
        if(tvShowEpisode==null) return null;
        TvShowEpisodeEntity episodeEntity=new TvShowEpisodeEntity();
        episodeEntity.setEpisodeNumber(tvShowEpisode.getEpisodeNumber());
        episodeEntity.setId(tvShowEpisode.getEpisodeId());
        episodeEntity.setName(tvShowEpisode.getEpisodeName());
        episodeEntity.setOverview(tvShowEpisode.getEpisodeOverview());
        episodeEntity.setEpisodeNumber(tvShowEpisode.getEpisodeNumber());
        episodeEntity.setVoteAverage(tvShowEpisode.getVoteAverage());
        episodeEntity.setVoteCount(tvShowEpisode.getVoteCount());
        return episodeEntity;
    }
}
