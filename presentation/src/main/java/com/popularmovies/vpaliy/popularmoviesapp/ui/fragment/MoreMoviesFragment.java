package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.Presenter;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import javax.inject.Inject;

public class MoreMoviesFragment extends MoreMediaFragment  {

    @Override @Inject @Movies
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
        switch (sortType){
            case POPULAR:
                return getString(R.string.popular_media);
            case LATEST:
                return getString(R.string.latest_media);
            case NOW_PLAYING:
                return getString(R.string.now_playing_media);
            case UPCOMING:
                return getString(R.string.upcoming_media);
            case TOP_RATED:
                return getString(R.string.top_rated_media);
            default:
                throw new IllegalArgumentException();
        }
    }
}
