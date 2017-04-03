package com.popularmovies.vpaliy.data.entity;


public class ActorEntity {

    private int actorId;
    private int movieId;
    private String firstName;
    private String lastName;
    private String actorAvatar;
    private String role;

    public ActorEntity(int actorId, int movieId){
        this.actorId=actorId;
        this.movieId=movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public String getActorAvatar() {
        return actorAvatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
