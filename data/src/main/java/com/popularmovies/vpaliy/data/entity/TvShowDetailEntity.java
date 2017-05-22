package com.popularmovies.vpaliy.data.entity;

import java.util.List;

public class TvShowDetailEntity implements HasId {

    private TvShow tvShowCover;
    private TvShowInfoEntity infoEntity;
    private List<TvShowSeasonEntity> seasons;
    private List<ActorEntity> cast;

    public List<TvShowSeasonEntity> getSeasons() {
        return seasons;
    }

    @Override
    public int id() {
        return tvShowCover.id();
    }

    public TvShow getTvShowCover() {
        return tvShowCover;
    }

    public TvShowInfoEntity getInfoEntity() {
        return infoEntity;
    }

    public List<ActorEntity> getCast() {
        return cast;
    }


    public void setCast(List<ActorEntity> cast) {
        this.cast = cast;
    }

    public void setInfoEntity(TvShowInfoEntity infoEntity) {
        this.infoEntity = infoEntity;
    }

    public void setSeasons(List<TvShowSeasonEntity> seasons) {
        this.seasons = seasons;
    }

    public void setTvShowCover(TvShow tvShowCover) {
        this.tvShowCover = tvShowCover;
    }

}
