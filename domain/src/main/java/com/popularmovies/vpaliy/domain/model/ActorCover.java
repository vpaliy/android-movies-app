package com.popularmovies.vpaliy.domain.model;

public class ActorCover {

    private int actorId;
    private int movieId;
    private String name;
    private String actorAvatar;
    private String role;

    public ActorCover(int actorId, int movieId){
        this.actorId=actorId;
        this.movieId=movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public String getActorAvatar() {
        return actorAvatar;
    }

    public String getName() {
        return name;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getRole() {
        return role;
    }

    public void setActorAvatar(String actorAvatar) {
        this.actorAvatar = actorAvatar;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
