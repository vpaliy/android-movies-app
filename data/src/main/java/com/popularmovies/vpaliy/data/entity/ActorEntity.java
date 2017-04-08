package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;

public class ActorEntity {

    private static final String baseImageUrl="http://image.tmdb.org/t/p/w780/";

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
        return baseImageUrl+actorAvatar;
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
}
