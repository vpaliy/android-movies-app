package com.popularmovies.vpaliy.data.entity;

import java.util.List;


public class ActorDetailEntity {


    private int actorId;
    private ActorEntity actor;
    private ActorInfoEntity actorInfo;
    private List<Movie> movies;

    public ActorDetailEntity(int actorId){
        this.actorId=actorId;
    }

    public ActorEntity getActorCover() {
        return actor;
    }

    public ActorInfoEntity getActorInfo() {
        return actorInfo;
    }

    public int getActorId() {
        return actorId;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setActor(ActorEntity actor) {
        this.actor = actor;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public void setActorInfo(ActorInfoEntity actorInfo) {
        this.actorInfo = actorInfo;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
