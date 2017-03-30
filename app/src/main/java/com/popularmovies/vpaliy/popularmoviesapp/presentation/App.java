package com.popularmovies.vpaliy.popularmoviesapp.presentation;


import android.app.Application;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.component.ApplicationComponent;

/**
 * Application
 */
public class App extends Application {

    private ApplicationComponent mAppComponent;
    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
    }

    private void initializeComponent(){

    }

    @NonNull
    public static App appInstance(){
        return INSTANCE;
    }

    public ApplicationComponent appComponent(){
        return mAppComponent;
    }
}
