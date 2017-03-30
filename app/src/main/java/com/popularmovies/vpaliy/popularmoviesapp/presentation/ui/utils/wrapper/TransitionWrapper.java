package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.wrapper;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * A wrapper that will passed around in order to accomplish shared element transition
 */
public class TransitionWrapper {

    private ImageView image;
    private Bundle data;

    private TransitionWrapper(@NonNull ImageView image, @NonNull Bundle data){
        this.image=image;
        this.data=data;
    }

    public Bundle getData() {
        return data;
    }

    public ImageView getImage() {
        return image;
    }


    public static TransitionWrapper wrap(@NonNull ImageView image, @NonNull Bundle data){
        return new TransitionWrapper(image,data);
    }
}
