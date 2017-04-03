package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class ActorDetails {

    private int actorId;
    private ActorCover actor;
    private ActorInfo actorInfo;
    private List<MovieCover> movies;

    public ActorDetails(int actorId){
        this.actorId=actorId;
    }

    public ActorCover getActorCover() {
        return actor;
    }

    public ActorInfo getActorInfo() {
        return actorInfo;
    }

    public int getActorId() {
        return actorId;
    }

    public List<MovieCover> getMovies() {
        return movies;
    }

    public void setActor(ActorCover actor) {
        this.actor = actor;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public void setActorInfo(ActorInfo actorInfo) {
        this.actorInfo = actorInfo;
    }

    public void setMovies(List<MovieCover> movies) {
        this.movies = movies;
    }
}
