package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

public final class ColorUtils {

    private ColorUtils(){
        throw new UnsupportedOperationException("Can't be initialized");
    }

    public static Palette.Swatch fetchDominantSwatch(Palette palette){
        if(palette!=null){
            Palette.Swatch swatch=palette.getDarkVibrantSwatch();
            if(swatch==null) swatch=palette.getDominantSwatch();
            return swatch;
        }
        return null;
    }

    public static void setDrawableColor(TextView view, int color){
        Drawable[] drawables=view.getCompoundDrawables();
        for(Drawable drawable:drawables){
            if(drawable!=null){
                drawable.mutate();
                DrawableCompat.setTint(drawable,color);
            }
        }
    }

    public static int dimColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    public static void setDrawableColor(ImageView view, int color) {
        Drawable drawable = view.getDrawable();
        if (drawable != null) {
            drawable.mutate();
            DrawableCompat.setTint(drawable, color);
        }
    }
}
