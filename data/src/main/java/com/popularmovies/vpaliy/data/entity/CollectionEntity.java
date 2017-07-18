package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectionEntity implements HasId{

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("parts")
    private List<Movie> parts;

    public String getOverview() {
        return overview;
    }

    public String getId() {
        return Integer.toString(id);
    }

    public List<Movie> getParts() {
        return parts;
    }

    public void setParts(List<Movie> parts) {
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @Override
    public String id() {
        return Integer.toString(id);
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
