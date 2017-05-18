package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class TVShowSeason {

    private int seasonId;
    private String airDate;
    private String seasonName;
    private String posterPath;
    private int seasonNumber;
    private List<TVShowEpisode> episodeList;

    public int getSeasonId() {
        return seasonId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<TVShowEpisode> getEpisodeList() {
        return episodeList;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public void setEpisodeList(List<TVShowEpisode> episodeList) {
        this.episodeList = episodeList;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

}
