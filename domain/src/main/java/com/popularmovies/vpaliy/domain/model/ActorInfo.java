package com.popularmovies.vpaliy.domain.model;


import java.util.Date;
import java.util.List;

public class ActorInfo {

    private int actorId;
    private Date birthDate;
    private String birthplace;
    private String bioDescription;
    private List<String> imagePaths;

    public ActorInfo(int actorId){
        this.actorId=actorId;
    }

    public int getActorId() {
        return actorId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
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

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
