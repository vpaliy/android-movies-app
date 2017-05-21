package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class MediaCover {

    private int mediaId;
    private double averageRate;
    private boolean isFavorite;
    private boolean isWatched;
    private boolean isMustWatch;
    private String movieTitle;
    private String posterPath;
    private String duration;
    private String releaseDate;
    private List<String> genres;
    private List<String> backdrops;
    private boolean isTvShow;

    public double getAverageRate() {
        return averageRate;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<String> getBackdrops() {
        return backdrops;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isMustWatch() {
        return isMustWatch;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setMustWatch(boolean mustWatch) {
        isMustWatch = mustWatch;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getDuration() {
        return duration;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setTvShow(boolean tvShow) {
        isTvShow = tvShow;
    }

    public boolean isTvShow() {
        return isTvShow;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void setBackdrops(List<String> backdrops) {
        this.backdrops = backdrops;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
