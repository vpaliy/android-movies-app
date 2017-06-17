package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvShow implements HasId {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("id")
    private int id;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private Number voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genre_ids")
    private int[] genres;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("backdrops")
    private List<BackdropImage> backdrops;

    @SerializedName("genres")
    private List<Genre> genreList;

    private boolean isFavorite;
    private boolean isWatched;
    private boolean isMustWatch;

    @Override
    public int id() {
        return id;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public boolean isMustWatch() {
        return isMustWatch;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setMustWatch(boolean mustWatch) {
        isMustWatch = mustWatch;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int[] getGenres() {
        return genres;
    }

    public Number getPopularity() {
        return popularity;
    }

    public Number getVoteAverage() {
        return voteAverage;
    }

    public List<BackdropImage> getBackdrops() {
        if(backdrops==null||!backdrops.isEmpty()){
            if(backdropPath!=null){
                backdrops= Collections.singletonList(new BackdropImage(backdropPath));
            }
        }
        return backdrops;
    }

    public void addGenre(Genre genre){
        if(genre!=null){
            if(genreList==null) genreList=new ArrayList<>();
            genreList.add(genre);
        }
    }

    public void setBackdrops(List<BackdropImage> backdrops) {
        this.backdrops = backdrops;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setGenres(int[] genres) {
        this.genres = genres;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}


