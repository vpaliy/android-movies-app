package com.popularmovies.vpaliy.data.configuration;

import android.content.Context;
import android.content.SharedPreferences;

import com.popularmovies.vpaliy.data.utils.Constants;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageQualityConfiguration implements IImageQualityConfiguration {

    private static final String BACKDROPS_KEY="backdropKey";
    private static final String COVER_KEY="coverKey";

    private static final int LOW_QUALITY=0;
    private static final int MEDIUM_QUALITY=1;
    private static final int HIGH_QUALITY=2;

    private ImageQuality backdropQuality;
    private ImageQuality coverQuality;

    private final SharedPreferences sharedPreferences;

    @Inject
    public ImageQualityConfiguration(Context context){
        this.sharedPreferences=context.getSharedPreferences("imageQuality", Context.MODE_PRIVATE);
        this.backdropQuality=init(BACKDROPS_KEY);
        this.coverQuality=init(COVER_KEY);
    }

    private ImageQuality init(String key){
        int quality=sharedPreferences.getInt(key,-1);
        switch (quality){
            case MEDIUM_QUALITY:
                return ImageQuality.MEDIUM;
            case HIGH_QUALITY:
                return ImageQuality.HIGH;
            default:
                return ImageQuality.LOW;
        }
    }

    @Override
    public void saveBackdropQuality(ImageQuality quality) {
        this.backdropQuality=quality;
        switch (quality){
            case LOW:
                sharedPreferences.edit().putInt(BACKDROPS_KEY,LOW_QUALITY).apply();
                break;
            case MEDIUM:
                sharedPreferences.edit().putInt(BACKDROPS_KEY,MEDIUM_QUALITY).apply();
                break;
            case HIGH:
                sharedPreferences.edit().putInt(BACKDROPS_KEY,HIGH_QUALITY).apply();
                break;
        }
    }

    @Override
    public void saveCoverQuality(ImageQuality quality) {
        this.coverQuality=quality;
        switch (quality){
            case LOW:
                sharedPreferences.edit().putInt(COVER_KEY,LOW_QUALITY).apply();
                break;
            case MEDIUM:
                sharedPreferences.edit().putInt(COVER_KEY,MEDIUM_QUALITY).apply();
                break;
            case HIGH:
                sharedPreferences.edit().putInt(COVER_KEY,HIGH_QUALITY).apply();
                break;
        }
    }


    @Override
    public String convertBackdrop(String imagePath) {
        switch (backdropQuality){
            case LOW:
                return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W185+"/"+imagePath;
            case MEDIUM:
                return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W342+"/"+imagePath;
            default:
                return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W780+"/"+imagePath;
        }
    }

    @Override
    public String convertCover(String imagePath) {
        switch (coverQuality){
            case HIGH:
                return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W780+"/"+imagePath;
            case MEDIUM:
                return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W342+"/"+imagePath;
            default:
                return Constants.BASE_MOVIE_URL+"/"+Constants.IMAGE_SIZE_W185+"/"+imagePath;
        }
    }

    public String extractPath(String imagePath){
        if(imagePath!=null){
            if(imagePath.contains(Constants.BASE_MOVIE_URL)){
                return imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length());
            }
            return imagePath;
        }
        return null;
    }

}
