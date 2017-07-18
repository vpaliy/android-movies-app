package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SeasonEntity implements HasId {

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

    private List<BackdropImage> images;
    private List<String> videos;
    private List<ActorEntity> cast;

    @Override
    public String id() {
        return Integer.toString(id);
    }

    public void setCast(List<ActorEntity> cast) {
        this.cast = cast;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public void setImages(List<BackdropImage> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public List<ActorEntity> getCast() {
        return cast;
    }

    public List<BackdropImage> getImages() {
        return images;
    }

    public String getOverview() {
        return overview;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getId() {
        return Integer.toString(id);
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

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}

