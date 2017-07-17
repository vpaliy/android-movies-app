package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ActorDetailEntity implements HasId {

    @SerializedName("id")
    private int actorId;
    private ActorEntity actor;
    private List<Movie> movies;
    private List<TvShow> tvShows;
    private List<String> images;
    private List<String> taggedImages;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("birthplace")
    private String birthplace;

    @SerializedName("biography")
    private String bioDescription;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("deathday")
    private String deathday;

    public ActorEntity getActorCover() {
        if(actor==null){
            actor=new ActorEntity();
            actor.setActorId(actorId);
            actor.setName(name);
            actor.setActorAvatar(profilePath);
        }
        return actor;
    }

    @Override
    public int id() {
        return actorId;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getBirthday() {
        return birthday;
    }

    public List<String> getImages() {
        return images;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }

    public String getBioDescription() {
        return bioDescription;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getName() {
        return name;
    }

    public int getActorId() {
        return actorId;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setTaggedImages(List<String> taggedImages) {
        this.taggedImages = taggedImages;
    }

    public List<String> getTaggedImages() {
        return taggedImages;
    }

    public void setActor(ActorEntity actor) {
        this.actor = actor;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    public void setBioDescription(String bioDescription) {
        this.bioDescription = bioDescription;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
