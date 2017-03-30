package com.popularmovies.vpaliy.popularmoviesapp.data.entity;

/**
 * This is a fake entity
 */
public class MovieEntity {

    private int ID;
    private String posterPath;
    private String originalTitle;
    private String plot;
    private String userRatings;
    private String releaseDate;


    public int getID() {
        return ID;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPlot() {
        return plot;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUserRatings() {
        return userRatings;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUserRatings(String userRatings) {
        this.userRatings = userRatings;
    }

}
