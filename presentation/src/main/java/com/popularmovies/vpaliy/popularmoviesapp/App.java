package com.popularmovies.vpaliy.popularmoviesapp;

import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent;

public class App extends MultiDexApplication {

    private ApplicationComponent applicationComponent;
    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
    }

    @NonNull
    public static App appInstance(){
        return INSTANCE;
    }

    public ApplicationComponent appComponent(){
        return applicationComponent;
    }
}
