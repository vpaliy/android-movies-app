package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShow {

    @SerializedName("id")
    private int tvShowId;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("episode_run_time")
    private int[] episodeRuntime;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("name")
    private String name;

    @SerializedName("networks")
    private List<Network> networks;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("vote_average")
    private Number voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    public int[] getEpisodeRuntime() {
        return episodeRuntime;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getStatus() {
        return status;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Number getPopularity() {
        return popularity;
    }

    public Number getVoteAverage() {
        return voteAverage;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getType() {
        return type;
    }

}


