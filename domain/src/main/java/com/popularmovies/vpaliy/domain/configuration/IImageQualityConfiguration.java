package com.popularmovies.vpaliy.domain.configuration;


public interface IImageQualityConfiguration {

    void saveCoverQuality(ImageQuality quality);
    void saveBackdropQuality(ImageQuality quality);

    String convertCover(String imagePath);
    String  convertBackdrop(String imagePath);

    enum ImageQuality {
        LOW,
        MEDIUM,
        HIGH
    }
}
