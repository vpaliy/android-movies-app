package com.popularmovies.vpaliy.domain.model;

public class TVShowEpisode {

    private String episodeId;
    private int episodeNumber;
    private String episodeOverview;
    private String episodeName;
    private String imagePath;
    private double voteAverage;
    private int voteCount;


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setEpisodeOverview(String episodeOverview) {
        this.episodeOverview = episodeOverview;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }


    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getEpisodeOverview() {
        return episodeOverview;
    }
}
