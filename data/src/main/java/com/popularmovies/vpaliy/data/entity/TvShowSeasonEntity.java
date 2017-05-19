package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TvShowSeasonEntity {

    @SerializedName("_id")
    private String _id;

    @SerializedName("id")
    private int id;

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episodes")
    private List<TvShowEpisodeEntity> episodes;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("season_number")
    private int seasonNumber;

    public String getOverview() {
        return overview;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getId() {
        return id;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<TvShowEpisodeEntity> getEpisodes() {
        return episodes;
    }

    public String get_id() {
        return _id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public void setEpisodes(List<TvShowEpisodeEntity> episodes) {
        this.episodes = episodes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}

