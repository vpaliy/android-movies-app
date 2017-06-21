package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements HasId {

    @SerializedName("id")
    private int movieId;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    private List<BackdropImage> backdropImages;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("genre_ids")
    private int[] genresId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    private long revenue;

    @SerializedName("budget")
    private long budget;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("status")
    private String status;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private Number voteAverage;

    @SerializedName("vote_count")
    private long voteCount;

    private boolean isFavorite;
    private boolean isWatched;
    private boolean isMustWatch;

    @Override
    public int id() {
        return movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getPopularity(){
        return popularity!=null?popularity.doubleValue():0;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public List<BackdropImage> getBackdropImages() {
        return backdropImages;
    }

    public void setBackdropImages(List<BackdropImage> backdropImages) {
        this.backdropImages = backdropImages;
    }

    public Movie addGenre(Genre genre){
        if(genre!=null){
            if(genres==null) genres=new ArrayList<>();
            genres.add(genre);
        }
        return this;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage!=null?voteAverage.doubleValue():0d;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public long getBudget() {
        return budget;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public boolean isMustWatch() {
        return isMustWatch;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public void setMustWatch(boolean mustWatch) {
        isMustWatch = mustWatch;
    }

    public void setGenresId(int[] genresId) {
        this.genresId = genresId;
    }

    public int[] getGenresId() {
        return genresId;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
