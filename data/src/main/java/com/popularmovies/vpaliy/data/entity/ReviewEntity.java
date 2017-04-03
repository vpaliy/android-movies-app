package com.popularmovies.vpaliy.data.entity;

public class ReviewEntity {

    private int movieId;
    private String author;
    private String content;
    private String url;

    public ReviewEntity(int movieId, String author, String content, String url){
        this.movieId=movieId;
        this.author=author;
        this.content=content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
