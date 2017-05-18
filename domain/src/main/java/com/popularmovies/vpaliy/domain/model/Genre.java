package com.popularmovies.vpaliy.domain.model;

import java.util.List;

public class Genre {

    private String genreName;
    private List<MediaCover> media;

    public List<MediaCover> getMedia() {
        return media;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public void setMedia(List<MediaCover> media) {
        this.media = media;
    }
}
