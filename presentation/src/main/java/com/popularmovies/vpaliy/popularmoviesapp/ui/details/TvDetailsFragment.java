package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class TvDetailsFragment extends MediaDetailsFragment{

    @Inject @TV @Override
    public void attachPresenter(@NonNull @TV MediaDetailsContract.Presenter presenter) {
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
