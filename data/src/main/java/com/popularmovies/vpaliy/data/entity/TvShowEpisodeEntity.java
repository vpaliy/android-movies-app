package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

public class TvShowEpisodeEntity {

    @SerializedName("id")
    private int id;

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episode_number")
    private int episodeNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("still_path")
    private String stillPath;

    @SerializedName("vote_average")
    private Number voteAverage;

    @SerializedName("vote_count")
    private int voteCount;


    public int getId() {
        return id;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Number getVoteAverage() {
        return voteAverage;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
