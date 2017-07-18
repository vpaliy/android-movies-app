package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class TVShowInfo {

    private String tvShowId;
    private String overview;
    private String lastAirDate;
    private String firstAirDate;
    private String originalLanguage;
    private int numberOfEpisodes;
    private int numberOfSeasons;
    private double popularity;
    private String name;
    private double averageRate;
    private int voteCount;
    private List<String> networks;
    private int[] episodeRuntime;
    private String status;


    public double getAverageRate() {
        return averageRate;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public String getTvShowId() {
        return tvShowId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int[] getEpisodeRuntime() {
        return episodeRuntime;
    }

    public String getOverview() {
        return overview;
    }

    public String getStatus() {
        return status;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public void setEpisodeRuntime(int[] episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTvShowId(String tvShowId) {
        this.tvShowId = tvShowId;
    }
}
