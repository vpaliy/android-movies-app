package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;


import android.content.res.Resources;

public class PresentationUtils {

    private PresentationUtils(){
        throw new UnsupportedOperationException();
    }

    public static CharSequence appendBullet(String text){
        String bullet="\u25CF"+" ";
        return text!=null?bullet+text:bullet;
    }

    public static int getStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
