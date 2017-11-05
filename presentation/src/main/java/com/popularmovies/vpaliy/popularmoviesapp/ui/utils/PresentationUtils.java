package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Property;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import java.util.HashMap;
import java.util.Map;

public class PresentationUtils {

    private PresentationUtils(){
        throw new UnsupportedOperationException();
    }

    private static final Map<String, Typeface> sTypefaceCache = new HashMap<>();

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
