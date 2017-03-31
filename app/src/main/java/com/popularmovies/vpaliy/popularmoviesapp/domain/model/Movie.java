package com.popularmovies.vpaliy.popularmoviesapp.domain.model;

import java.util.Comparator;
import java.util.Date;

public class Movie {

    private int ID;
    private String posterPath;
    private String originalTitle;
    private String plot;
    private String userRatings;
    private Date releaseDate;
    private int averageVote;



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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getUserRatings() {
        return userRatings;
    }

    public int getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(int averageVote) {
        this.averageVote = averageVote;
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

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUserRatings(String userRatings) {
        this.userRatings = userRatings;
    }
}
