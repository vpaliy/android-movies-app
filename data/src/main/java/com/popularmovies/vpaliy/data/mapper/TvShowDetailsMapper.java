package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowDetailsMapper extends Mapper<TVShowDetails,TvShowDetailEntity> {

    private final Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper;
    private final Mapper<MediaCover,TvShow> coverMapper;
    private final Mapper<ActorCover,ActorEntity> actorMapper;
    private final Mapper<TVShowInfo,TvShowInfoEntity> infoMapper;

    @Inject
    public TvShowDetailsMapper(@NonNull Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper,
                               @NonNull Mapper<ActorCover,ActorEntity> actorMapper,
                               @NonNull Mapper<TVShowInfo,TvShowInfoEntity> infoMapper,
                               @NonNull Mapper<MediaCover,TvShow> coverMapper){
        this.seasonMapper=seasonMapper;
        this.actorMapper=actorMapper;
        this.infoMapper=infoMapper;
        this.coverMapper=coverMapper;
    }

    @Override
    public TVShowDetails map(TvShowDetailEntity tvShowDetailEntity) {
        TVShowDetails details=new TVShowDetails();
        details.setTvShowCover(coverMapper.map(tvShowDetailEntity.getTvShowCover()));
        details.setTvShowInfo(infoMapper.map(tvShowDetailEntity.getInfoEntity()));
        details.setCast(actorMapper.map(tvShowDetailEntity.getCast()));
        details.setSeasons(seasonMapper.map(tvShowDetailEntity.getSeasons()));
        details.setTvShowId(tvShowDetailEntity.getTvShowCover().getId());
        return details;
    }

    @Override
    public TvShowDetailEntity reverseMap(TVShowDetails tvShowDetails) {
        TvShowDetailEntity entity=new TvShowDetailEntity();
        entity.setTvShowCover(coverMapper.reverseMap(tvShowDetails.getTvShowCover()));
        entity.setInfoEntity(infoMapper.reverseMap(tvShowDetails.getTvShowInfo()));
        entity.setCast(actorMapper.reverseMap(tvShowDetails.getCast()));
        entity.setSeasons(seasonMapper.reverseMap(tvShowDetails.getSeasons()));
        return entity;
    }
}
