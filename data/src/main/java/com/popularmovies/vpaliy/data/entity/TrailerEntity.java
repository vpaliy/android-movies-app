package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;

public class TrailerEntity {

    private int movieId;

    private int trailerId;

    @SerializedName("name")
    private String trailerTitle;

    @SerializedName("key")
    private String trailerUrl;

    @SerializedName("site")
    private String site;

    public int getMovieId() {
        return movieId;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public String getSite() {
        return site;
    }

    public int getTrailerId() {
        return trailerId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public boolean isFromYoutube() {
        return site.toLowerCase(Locale.US).equals("YouTube".toLowerCase(Locale.US));
    }
}
