package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class PersonalFragment extends MediaFragment{


    @Inject
    @Override
    @Movies
    public void attachPresenter(@NonNull @Movies MediaContract.Presenter presenter) {
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
                return getString(R.string.ByPopularity);
            case LATEST:
                return getString(R.string.ByLatest);
            case NOW_PLAYING:
                return getString(R.string.ByNowPlaying);
            case UPCOMING:
                return getString(R.string.ByUpcoming);
            case TOP_RATED:
                return getString(R.string.ByTopRated);
            default:
                throw new IllegalArgumentException();
        }

    }
}
