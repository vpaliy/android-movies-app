package com.popularmovies.vpaliy.domain.model;

public class ActorCover {

    private String actorId;
    private String movieId;
    private String name;
    private String actorAvatar;
    private String role;

    public ActorCover(String actorId, String movieId){
        this.actorId=actorId;
        this.movieId=movieId;
    }

    public String getActorId() {
        return actorId;
    }

    public String getActorAvatar() {
        return actorAvatar;
    }

    public String getName() {
        return name;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getRole() {
        return role;
    }

    public void setActorAvatar(String actorAvatar) {
        this.actorAvatar = actorAvatar;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
