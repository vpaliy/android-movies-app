package com.popularmovies.vpaliy.domain.model;

import java.util.List;

public class SeasonDetails {

    private SeasonCover seasonCover;
    private String seasonId;
    private List<String> images;
    private List<Trailer> videos;
    private List<ActorCover> cast;
    private List<TVShowEpisode> episodeList;

    public void setCast(List<ActorCover> cast) {
        this.cast = cast;
    }

    public void setEpisodeList(List<TVShowEpisode> episodeList) {
        this.episodeList = episodeList;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setSeasonCover(SeasonCover seasonCover) {
        this.seasonCover = seasonCover;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public void setVideos(List<Trailer> videos) {
        this.videos = videos;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public List<ActorCover> getCast() {
        return cast;
    }

    public List<String> getImages() {
        return images;
    }

    public List<Trailer> getVideos() {
        return videos;
    }

    public List<TVShowEpisode> getEpisodeList() {
        return episodeList;
    }

    public SeasonCover getSeasonCover() {
        return seasonCover;
    }
}

