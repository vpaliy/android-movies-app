package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import android.content.Context;

import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, MapperModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
    Context context();
    RxBus rxEventBus();
    Navigator navigator();
}
