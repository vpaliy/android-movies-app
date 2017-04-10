package com.popularmovies.vpaliy.popularmoviesapp;


import android.app.Application;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Application
 */
public class App extends Application {

    private ApplicationComponent applicationComponent;
    private static App INSTANCE;

    private RefWatcher watcher;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        INSTANCE=this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        watcher=LeakCanary.install(this);
    }

    private void initializeComponent(){
       applicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public void watch(Object object){
        watcher.watch(object);
    }

    @NonNull
    public static App appInstance(){
        return INSTANCE;
    }

    public ApplicationComponent appComponent(){
        return applicationComponent;
    }
}
