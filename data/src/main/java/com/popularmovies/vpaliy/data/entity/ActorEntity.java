package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

public class ActorEntity implements HasId {

    @SerializedName("id")
    private int actorId;

    @SerializedName("cast_id")
    private int movieId;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String actorAvatar;

    @SerializedName("character")
    private String role;

    @Override
    public String id() {
        return Integer.toString(actorId);
    }

    public String getActorId() {
        return Integer.toString(actorId);
    }

    public String getActorAvatar() {
        return actorAvatar;
    }

    public String getName() {
        return name;
    }

    public String getMovieId() {
        return Integer.toString(movieId);
    }

    public void setMovieId(String movieId) {
        this.movieId = Integer.parseInt(movieId);
    }

    public String getRole() {
        return role;
    }

    public void setActorAvatar(String actorAvatar) {
        this.actorAvatar = actorAvatar;
    }

    public void setActorId(String actorId) {
        this.actorId = Integer.parseInt(actorId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
