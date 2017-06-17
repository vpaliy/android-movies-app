package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;


public class StringUtils {

    private StringUtils(){
        throw new UnsupportedOperationException();
    }

    public static CharSequence appendBullet(String text){
        String bullet="\u25CF"+" ";
        return text!=null?bullet+text:bullet;
    }
}
