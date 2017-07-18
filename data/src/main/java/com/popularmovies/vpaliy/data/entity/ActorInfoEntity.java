package com.popularmovies.vpaliy.data.entity;

import java.util.Date;
import java.util.List;

public class ActorInfoEntity {

    private int actorId;
    private String birthDate;
    private String birthplace;
    private String bioDescription;
    private List<String> imagePaths;

    public String getActorId() {
        return Integer.toString(actorId);
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public String getBioDescription() {
        return bioDescription;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setActorId(String actorId) {
        this.actorId = Integer.parseInt(actorId);
    }

    public void setBioDescription(String bioDescription) {
        this.bioDescription = bioDescription;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
