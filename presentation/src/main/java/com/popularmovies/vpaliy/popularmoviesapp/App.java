package com.popularmovies.vpaliy.popularmoviesapp;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Application
 */

public class App extends MultiDexApplication {

    private ApplicationComponent applicationComponent;
    private static App INSTANCE;
    private final static String ROBOTO_SLAB = "fonts/RobotoSlab-Regular.ttf";

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
//        configureDefaultFont(ROBOTO_SLAB);
        INSTANCE=this;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    @NonNull
    public static App appInstance(){
        return INSTANCE;
    }

    public ApplicationComponent appComponent(){
        return applicationComponent;
    }
}
