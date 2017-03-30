package com.popularmovies.vpaliy.popularmoviesapp.presentation.di.component;

import android.content.Context;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.module.DataModule;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.activity.BaseActivity;
import com.squareup.otto.Bus;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    Context context();
    Bus eventBus();
}
