package com.popularmovies.vpaliy.domain.model;

public class MovieInfo {

    private String movieId;
    private double averageRate;
    private String name;
    private String director;
    private String budget;
    private String revenue;
    private String description;
    private String releaseDate;

    public MovieInfo(String movieId, String description){
        this.movieId=movieId;
        this.description=description;
    }

    public String getMovieId() {
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

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setMovieId(String movieId) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
