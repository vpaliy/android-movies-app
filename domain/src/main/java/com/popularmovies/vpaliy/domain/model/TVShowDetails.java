package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class TVShowDetails {

    private int tvShowId;
    private MediaCover tvShowCover;
    private TVShowInfo tvShowInfo;
    private List<TVShowSeason> seasons;
    private List<ActorCover> cast;


    public int getTvShowId() {
        return tvShowId;
    }

    public List<ActorCover> getCast() {
        return cast;
    }

    public List<TVShowSeason> getSeasons() {
        return seasons;
    }

    public MediaCover getTvShowCover() {
        return tvShowCover;
    }

    public TVShowInfo getTvShowInfo() {
        return tvShowInfo;
    }

    public void setCast(List<ActorCover> cast) {
        this.cast = cast;
    }

    public void setSeasons(List<TVShowSeason> seasons) {
        this.seasons = seasons;
    }

    public void setTvShowCover(MediaCover tvShowCover) {
        this.tvShowCover = tvShowCover;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public void setTvShowInfo(TVShowInfo tvShowInfo) {
        this.tvShowInfo = tvShowInfo;
    }
}
