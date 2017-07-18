package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
public class MovieDetailsFragment extends MediaDetailsFragment{

    @Inject @Movies @Override
    public void attachPresenter(@NonNull @Movies MediaDetailsContract.Presenter presenter) {
        this.presenter=checkNotNull(presenter);
        this.presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }
}
