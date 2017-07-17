package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class ActorDetails {

    private int actorId;
    private ActorCover actor;
    private List<MediaCover> movies;
    private List<MediaCover> tvShows;
    private List<String> images;
    private List<String> taggedImages;
    private String birthplace;
    private String bioDescription;
    private List<String> imagePaths;
    private String popularity;
    private String birthday;
    private String deathday;

    public void setBioDescription(String bioDescription) {
        this.bioDescription = bioDescription;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setTvShows(List<MediaCover> tvShows) {
        this.tvShows = tvShows;
    }

    public void setTaggedImages(List<String> taggedImages) {
        this.taggedImages = taggedImages;
    }

    public List<String> getTaggedImages() {
        return taggedImages;
    }

    public ActorInfo buildActorInfo(){
        ActorInfo actorInfo=new ActorInfo(actorId);
        actorInfo.setBirthplace(birthplace);
        actorInfo.setBioDescription(bioDescription);
        actorInfo.setBirthday(birthday);
        return actorInfo;
    }

    public ActorCover getActorCover() {
        return actor;
    }

    public int getActorId() {
        return actorId;
    }

    public List<MediaCover> getMovies() {
        return movies;
    }

    public void setActor(ActorCover actor) {
        this.actor = actor;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public void setMovies(List<MediaCover> movies) {
        this.movies = movies;
    }

    public ActorCover getActor() {
        return actor;
    }

    public List<MediaCover> getTvShows() {
        return tvShows;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public List<String> getImages() {
        return images;
    }

    public String getBioDescription() {
        return bioDescription;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getPopularity() {
        return popularity;
    }
}
