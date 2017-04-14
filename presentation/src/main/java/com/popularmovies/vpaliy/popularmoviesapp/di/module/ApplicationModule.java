package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import android.app.Application;
import android.content.Context;

import com.popularmovies.vpaliy.data.utils.SchedulerProvider;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;
import com.squareup.otto.Bus;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import android.support.annotation.NonNull;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application=application;
    }

    @Singleton
    @Provides
    Context provideWithContext(){
        return application;
    }

    @Singleton
    @Provides
    Navigator provideNavigator(){
        return new Navigator();
    }


    @Singleton
    @Provides
    RxBus provideRxBus(){
        return new RxBus();
    }


    @Singleton
    @Provides
    SchedulerProvider provideSchedulers(){
        return new SchedulerProvider();
    }

}
