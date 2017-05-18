package com.popularmovies.vpaliy.domain.model;


import java.util.Date;

public class MovieInfo {

    private int movieId;
    private double averageRate;
    private String name;
    private String director;
    private String budget;
    private String revenue;
    private String description;
    private Date releaseDate;

    public MovieInfo(int movieId, String description){
        this.movieId=movieId;
        this.description=description;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public String getBudget() {
        return budget;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getRevenue() {
        return revenue;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
