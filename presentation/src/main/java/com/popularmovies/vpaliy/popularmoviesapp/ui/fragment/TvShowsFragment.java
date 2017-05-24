package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.source.qualifier.TV;
import javax.inject.Inject;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;

import java.util.Arrays;
import java.util.List;

public class TvShowsFragment extends MediaFragment{

    @Override
    @Inject
    @TV
    public void attachPresenter(@NonNull @TV MediaContract.Presenter presenter) {
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
    List<SortType> getSortTypes() {
        return Arrays.asList(SortType.POPULAR, SortType.LATEST,
                SortType.NOW_PLAYING, SortType.UPCOMING,SortType.TOP_RATED);
    }

    @Override
    String getTitle(SortType sortType) {
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
