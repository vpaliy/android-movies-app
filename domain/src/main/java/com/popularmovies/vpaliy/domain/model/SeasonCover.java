package com.popularmovies.vpaliy.domain.model;


public class SeasonCover {

    private String seasonId;
    private String airDate;
    private String seasonName;
    private String posterPath;
    private String overview;
    private int seasonNumber;

    public String getSeasonId() {
        return seasonId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
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

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

}
