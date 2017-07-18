package com.popularmovies.vpaliy.domain.model;


public class Review {

    private String movieId;
    private String author;
    private String content;
    private String url;

    public Review(String movieId, String author, String content, String url){
        this.movieId=movieId;
        this.author=author;
        this.content=content;
        this.url=url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMovieId() {
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
