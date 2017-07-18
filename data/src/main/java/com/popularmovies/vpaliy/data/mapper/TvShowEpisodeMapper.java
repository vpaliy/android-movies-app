package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.domain.model.Episode;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowEpisodeMapper extends Mapper<Episode,TvShowEpisodeEntity>{

    @Inject
    public TvShowEpisodeMapper(){}

    @Override
    public Episode map(TvShowEpisodeEntity tvShowEpisodeEntity) {
        if(tvShowEpisodeEntity==null) return null;
        Episode episode=new Episode();
        episode.setEpisodeId(tvShowEpisodeEntity.getId());
        episode.setEpisodeName(tvShowEpisodeEntity.getName());
        episode.setEpisodeNumber(tvShowEpisodeEntity.getEpisodeNumber());
        episode.setEpisodeOverview(tvShowEpisodeEntity.getOverview());
        episode.setVoteAverage(tvShowEpisodeEntity.getVoteAverage().doubleValue());
        episode.setVoteCount(tvShowEpisodeEntity.getVoteCount());
        return episode;
    }

    @Override
    public TvShowEpisodeEntity reverseMap(Episode episode) {
        if(episode ==null) return null;
        TvShowEpisodeEntity episodeEntity=new TvShowEpisodeEntity();
        episodeEntity.setEpisodeNumber(episode.getEpisodeNumber());
        episodeEntity.setId(episode.getEpisodeId());
        episodeEntity.setName(episode.getEpisodeName());
        episodeEntity.setOverview(episode.getEpisodeOverview());
        episodeEntity.setEpisodeNumber(episode.getEpisodeNumber());
        episodeEntity.setVoteAverage(episode.getVoteAverage());
        episodeEntity.setVoteCount(episode.getVoteCount());
        return episodeEntity;
    }
}
