package com.popularmovies.vpaliy.domain.model;

import java.util.List;

public class MediaCollection {

    private String id;
    private List<MediaCover> covers;
    private String name;
    private String overview;
    private String backdrop;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setCovers(List<MediaCover> covers) {
        this.covers = covers;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<MediaCover> getCovers() {
        return covers;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getOverview() {
        return overview;
    }
}
