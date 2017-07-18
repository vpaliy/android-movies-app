package com.popularmovies.vpaliy.data.entity;

import java.util.List;

public class TvShowDetailEntity implements HasId {

    private TvShow tvShowCover;
    private TvShowInfoEntity infoEntity;
    private List<SeasonEntity> seasons;
    private List<ActorEntity> cast;
    private List<TrailerEntity> trailers;
    private List<TvShow> similarTvShows;

    public List<SeasonEntity> getSeasons() { return seasons;}

    @Override
    public String id() {
        return tvShowCover.id();
    }

    public void setTrailers(List<TrailerEntity> trailers) {
        this.trailers = trailers;
    }

    public List<TrailerEntity> getTrailers() {
        return trailers;
    }

    public void setSimilarTvShows(List<TvShow> similarTvShows) {
        this.similarTvShows = similarTvShows;
    }

    public List<TvShow> getSimilarTvShows() {
        return similarTvShows;
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

    public void setSeasons(List<SeasonEntity> seasons) {
        this.seasons = seasons;
    }

    public void setTvShowCover(TvShow tvShowCover) {
        this.tvShowCover = tvShowCover;
    }

}
