package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.Presenter;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import javax.inject.Inject;

public class MoreMoviesFragment extends MoreMediaFragment  {

    @Override
    @Inject
    @Movies
    public void attachPresenter(@NonNull @Movies Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }


    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    String getTitle(@NonNull SortType sortType) {
        return null;
    }
}
