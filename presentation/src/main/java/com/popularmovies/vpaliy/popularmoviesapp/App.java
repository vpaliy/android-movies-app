package com.popularmovies.vpaliy.popularmoviesapp;


import android.app.Application;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Application
 */

public class App extends Application {

    private ApplicationComponent applicationComponent;
    private static App INSTANCE;
    private final static String ROBOTO_SLAB = "fonts/RobotoSlab-Regular.ttf";

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        configureDefaultFont(ROBOTO_SLAB);
        INSTANCE=this;

    }

    private void configureDefaultFont(String robotoSlab) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(robotoSlab)
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void initializeComponent(){
       applicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }


    @NonNull
    public static App appInstance(){
        return INSTANCE;
    }

    public ApplicationComponent appComponent(){
        return applicationComponent;
    }
}
