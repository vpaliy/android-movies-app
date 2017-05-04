package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

public class ActorEntity {


    @SerializedName("cast_id")
    private int actorId;

    @SerializedName("id")
    private int movieId;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String actorAvatar;

    @SerializedName("character")
    private String role;

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

    public void setRole(String role) {
        this.role = role;
    }
}
