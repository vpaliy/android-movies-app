package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Network;
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
public class TvShowDetailsMapper implements Mapper<TVShowDetails,TvShowDetailEntity> {

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
        if(tvShowDetailEntity.getCast()!=null){
            List<ActorCover> cast=new ArrayList<>(tvShowDetailEntity.getCast().size());
            tvShowDetailEntity.getCast().forEach(actorEntity -> cast.add(actorMapper.map(actorEntity)));
            details.setCast(cast);
        }
        //
        if(tvShowDetailEntity.getSeasons()!=null){
            List<TVShowSeason> seasons=new ArrayList<>(tvShowDetailEntity.getSeasons().size());
            tvShowDetailEntity.getSeasons().forEach(seasonEntity->seasons.add(seasonMapper.map(seasonEntity)));
            details.setSeasons(seasons);
        }
        details.setTvShowId(tvShowDetailEntity.getTvShowCover().getId());
        return details;
    }

    @Override
    public TvShowDetailEntity reverseMap(TVShowDetails tvShowDetails) {
        TvShowDetailEntity entity=new TvShowDetailEntity();
        entity.setTvShowCover(coverMapper.reverseMap(tvShowDetails.getTvShowCover()));
        entity.setInfoEntity(infoMapper.reverseMap(tvShowDetails.getTvShowInfo()));
        if(tvShowDetails.getCast()!=null){
            List<ActorEntity> cast=new ArrayList<>(tvShowDetails.getCast().size());
            tvShowDetails.getCast().forEach(actorCover -> cast.add(actorMapper.reverseMap(actorCover)));
            entity.setCast(cast);
        }
        //
        if(tvShowDetails.getSeasons()!=null){
            List<TvShowSeasonEntity> seasons=new ArrayList<>(tvShowDetails.getSeasons().size());
            tvShowDetails.getSeasons().forEach(season->seasons.add(seasonMapper.reverseMap(season)));
            entity.setSeasons(seasons);
        }
        return entity;

    }

    @Override
    public List<TVShowDetails> map(List<TvShowDetailEntity> from) {
        if(from==null) return null;
        List<TVShowDetails> details=new ArrayList<>(from.size());
        from.forEach(tvShowDetailEntity -> details.add(map(tvShowDetailEntity)));
        return details;
    }
}
