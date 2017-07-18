package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class TVShowDetails {

    private String tvShowId;
    private MediaCover tvShowCover;
    private TVShowInfo tvShowInfo;
    private List<SeasonCover> seasons;
    private List<ActorCover> cast;
    private List<Trailer> trailers;
    private List<MediaCover> similar;

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public void setSimilar(List<MediaCover> similar) {
        this.similar = similar;
    }

    public List<MediaCover> getSimilar() {
        return similar;
    }

    public String getTvShowId() {
        return tvShowId;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public List<ActorCover> getCast() {
        return cast;
    }

    public List<SeasonCover> getSeasons() {
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

    public void setSeasons(List<SeasonCover> seasons) {
        this.seasons = seasons;
    }

    public void setTvShowCover(MediaCover tvShowCover) {
        this.tvShowCover = tvShowCover;
    }

    public void setTvShowId(String tvShowId) {
        this.tvShowId = tvShowId;
    }

    public void setTvShowInfo(TVShowInfo tvShowInfo) {
        this.tvShowInfo = tvShowInfo;
    }
}
