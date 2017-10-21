package com.popularmovies.vpaliy.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends MultiDexApplication {

    private ApplicationComponent applicationComponent;
    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        INSTANCE=this;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
