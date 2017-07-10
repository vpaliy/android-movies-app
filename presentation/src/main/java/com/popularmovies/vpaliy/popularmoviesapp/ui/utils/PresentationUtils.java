package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.util.IntProperty;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationUtils {

    private PresentationUtils(){
        throw new UnsupportedOperationException();
    }

    private static final Map<String, Typeface> sTypefaceCache = new HashMap<>();

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

    public static int[] getPaletteColors(Palette palette){
        int colors[]=new int[2];
        colors[1]=-1;
        Palette.Swatch dominant=palette.getDominantSwatch();
        Palette.Swatch result=dominant;
        if(palette.getDarkVibrantSwatch()!=null){
            result=palette.getDarkVibrantSwatch();
        }
        if(palette.getDarkMutedSwatch()!=null){
            if(result!=dominant){
                colors[0]=result.getRgb();
                colors[1]=palette.getDarkMutedSwatch().getRgb();
                return colors;
            }
            result=palette.getDarkMutedSwatch();
        }
        colors[0]=dominant.getRgb();
        //check now
        if(result!=dominant){
            colors[1]=result.getRgb();
        }else{
            colors[1]=getSecondaryColor(palette);
        }
        return colors;

    }

    public static int getDominantColor(Palette palette){
        if (palette != null) {
            Palette.Swatch result=palette.getDominantSwatch();
            if(palette.getDarkVibrantSwatch()!=null){
                result=palette.getDarkVibrantSwatch();
            }
            else if(palette.getDarkMutedSwatch()!=null){
                result=palette.getDarkMutedSwatch();
            }
            return result.getRgb();
        }
        return Color.WHITE;
    }

    public static int getSecondaryColor(Palette palette){
        if(palette!=null){
            Palette.Swatch lightVibrantSwatch   = palette.getLightVibrantSwatch();
            Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();

            Palette.Swatch tabBackground=lightMutedSwatch!=null?lightMutedSwatch
                    :(lightVibrantSwatch!=null?lightVibrantSwatch:palette.getDominantSwatch());
            return tabBackground.getRgb();
        }
        return Color.WHITE;
    }


    public static Typeface get(Context context, String font) {
        synchronized (sTypefaceCache) {
            if (!sTypefaceCache.containsKey(font)) {
                Typeface tf = Typeface.createFromAsset(
                        context.getApplicationContext().getAssets(), "fonts/" + font + ".ttf");
                sTypefaceCache.put(font, tf);
            }
            return sTypefaceCache.get(font);
        }
    }


    private static List<Boolean> setAncestralClipping(
            @NonNull View view, boolean clipChildren, List<Boolean> was) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            was.add(group.getClipChildren());
            group.setClipChildren(clipChildren);
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            setAncestralClipping((ViewGroup) parent, clipChildren, was);
        }
        return was;
    }

    public static boolean isNavBarOnBottom(@NonNull Context context) {
        final Resources res= context.getResources();
        final Configuration cfg = context.getResources().getConfiguration();
        final DisplayMetrics dm =res.getDisplayMetrics();
        boolean canMove = (dm.widthPixels != dm.heightPixels &&
                cfg.smallestScreenWidthDp < 600);
        return(!canMove || dm.widthPixels < dm.heightPixels);
    }

    public static @CheckResult
    @ColorInt int modifyAlpha(@ColorInt int color,
                              @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public static @CheckResult @ColorInt int modifyAlpha(@ColorInt int color,
                                                         @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }


    public static void restoreAncestralClipping(@NonNull View view, List<Boolean> was) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            group.setClipChildren(was.remove(0));
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            restoreAncestralClipping((ViewGroup) parent, was);
        }
    }

    private static Interpolator fastOutSlowIn;

    public static Interpolator getFastOutSlowInInterpolator(Context context) {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                    android.R.interpolator.fast_out_slow_in);
        }
        return fastOutSlowIn;
    }

    public static abstract class IntProp<T> {

        public final String name;

        public IntProp(String name) {
            this.name = name;
        }

        public abstract void set(T object, int value);
        public abstract int get(T object);
    }


    public static <T> Property<T, Integer> createIntProperty(final IntProp<T> impl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new IntProperty<T>(impl.name) {
                @Override
                public Integer get(T object) {
                    return impl.get(object);
                }

                @Override
                public void setValue(T object, int value) {
                    impl.set(object, value);
                }
            };
        } else {
            return new Property<T, Integer>(Integer.class, impl.name) {
                @Override
                public Integer get(T object) {
                    return impl.get(object);
                }

                @Override
                public void set(T object, Integer value) {
                    impl.set(object, value);
                }
            };
        }
    }
}
