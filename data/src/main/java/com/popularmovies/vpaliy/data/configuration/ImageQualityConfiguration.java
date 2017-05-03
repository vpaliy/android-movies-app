package com.popularmovies.vpaliy.data.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.popularmovies.vpaliy.data.R;
import com.popularmovies.vpaliy.data.utils.Constants;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageQualityConfiguration implements IImageQualityConfiguration,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final String BACKDROP_QUALITY_KEY;
    private final String COVER_QUALITY_KEY;

    private final String LOW_QUALITY;
    private final String MEDIUM_QUALITY;
    private final String HIGH_QUALITY;

    private ImageQuality backdropQuality;
    private ImageQuality coverQuality;

    private final SharedPreferences sharedPreferences;

    @Inject
    public ImageQualityConfiguration(Context context){
        this.sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        final String[] qualityArray=context.getResources().getStringArray(R.array.ImageQuality);
        this.BACKDROP_QUALITY_KEY=context.getString(R.string.pref_backdrop_quality_key);
        this.COVER_QUALITY_KEY=context.getString(R.string.pref_poster_quality_key);
        this.LOW_QUALITY=qualityArray[0];
        this.MEDIUM_QUALITY=qualityArray[1];
        this.HIGH_QUALITY=qualityArray[2];
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        this.backdropQuality=assignQuality(BACKDROP_QUALITY_KEY,HIGH_QUALITY);
        this.coverQuality=assignQuality(COVER_QUALITY_KEY,LOW_QUALITY);
    }


    private ImageQuality assignQuality(String key, String defValue){
        String quality=sharedPreferences.getString(key,defValue);
        if(quality.equals(MEDIUM_QUALITY)){
            return ImageQuality.MEDIUM;
        }else if(quality.equals(HIGH_QUALITY)){
            return ImageQuality.HIGH;
        }
        return ImageQuality.LOW;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(BACKDROP_QUALITY_KEY.equals(key)){
            this.backdropQuality=assignQuality(BACKDROP_QUALITY_KEY,HIGH_QUALITY);
        }else{
            this.coverQuality=assignQuality(COVER_QUALITY_KEY,LOW_QUALITY);
        }
    }

    @Override
    public void saveBackdropQuality(ImageQuality quality) {
        this.backdropQuality=quality;
        switch (quality){
            case LOW:
                sharedPreferences.edit().putString(BACKDROP_QUALITY_KEY,LOW_QUALITY).apply();
                break;
            case MEDIUM:
                sharedPreferences.edit().putString(BACKDROP_QUALITY_KEY,MEDIUM_QUALITY).apply();
                break;
            case HIGH:
                sharedPreferences.edit().putString(BACKDROP_QUALITY_KEY,HIGH_QUALITY).apply();
                break;
        }
    }

    @Override
    public void saveCoverQuality(ImageQuality quality) {
        this.coverQuality=quality;
        switch (quality){
            case LOW:
                sharedPreferences.edit().putString(COVER_QUALITY_KEY,LOW_QUALITY).apply();
                break;
            case MEDIUM:
                sharedPreferences.edit().putString(COVER_QUALITY_KEY,MEDIUM_QUALITY).apply();
                break;
            case HIGH:
                sharedPreferences.edit().putString(COVER_QUALITY_KEY,HIGH_QUALITY).apply();
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
