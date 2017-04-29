package com.popularmovies.vpaliy.data.configuration;

import com.popularmovies.vpaliy.data.utils.Constants;

public class ImageQualityConfiguration {

    private Quality imageQuality;

    public ImageQualityConfiguration(){}

    public String getPosterImagePath(String imagePath){
        return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W185+"/"+imagePath;
    }

    public String getBackdropImagePath(String backdropPath){
        return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W185+"/"+backdropPath;
    }

    public String extractPath(String imagePath){
        if(imagePath!=null){
            if(imagePath.contains(Constants.BASE_MOVIE_URL)){
                return imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length());
            }
            return imagePath;
        }
        return imagePath;
    }

    enum Quality{
        LOW,
        MEDIUM,
        HIGH
    }
}
