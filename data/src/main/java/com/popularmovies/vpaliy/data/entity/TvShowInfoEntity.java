package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowInfoEntity {

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

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("seasons")
    private List<TvShowSeasonEntity> seasonEntities;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("vote_average")
    private Number voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("genres")
    private List<Genre> genreList;

    public void setSeasonEntities(List<TvShowSeasonEntity> seasonEntities) {
        this.seasonEntities = seasonEntities;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setEpisodeRuntime(int[] episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public List<TvShowSeasonEntity> getSeasonEntities() {
        return seasonEntities;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public int[] getEpisodeRuntime() {
        return episodeRuntime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Network> getNetworks() {
        return networks;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public static TvShow createTvShowCover(TvShowInfoEntity info){
        if(info==null) return null;
        TvShow cover=new TvShow();
        cover.setFirstAirDate(info.getFirstAirDate());
        cover.setId(info.getTvShowId());
        cover.setGenreList(info.getGenres());
        cover.setVoteAverage(info.getVoteAverage());
        cover.setVoteCount(info.getVoteCount());
        cover.setBackdropPath(info.getBackdrop_path());
        cover.setPosterPath(info.getPosterPath());
        cover.setName(info.getName());
        cover.setOverview(info.getOverview());
        cover.setOriginalName(info.getOriginalName());
        cover.setPopularity(info.getPopularity());
        return cover;
    }
}
