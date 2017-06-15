package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.data.utils.scheduler.SchedulerProvider;
import com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

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
    BaseSchedulerProvider provideSchedulers(){
        return new SchedulerProvider();
    }

    @Singleton
    @Provides
    PresentationConfiguration providesPresentationConfiguration(@NonNull Context context){
        return new PresentationConfiguration(context);
    }

}
