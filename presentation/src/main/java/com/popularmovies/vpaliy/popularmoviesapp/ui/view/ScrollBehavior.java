package com.popularmovies.vpaliy.popularmoviesapp.ui.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ScrollBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private static final String TAG=ScrollBehavior.class.getSimpleName();

    public ScrollBehavior(){

    }

    public ScrollBehavior(@NonNull Context context,@NonNull AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        Log.d(TAG,dependency.getClass().getSimpleName());
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {
        Log.d(TAG,"onDependentViewChanged()");
        return false;
    }
}
