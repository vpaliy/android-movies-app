package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import javax.inject.Inject;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;

import java.util.Arrays;
import java.util.List;

public class TvShowsFragment extends MediaFragment{

    @Override
    List<SortType> getSortTypes() {
        return Arrays.asList(SortType.POPULAR, SortType.LATEST,
                SortType.NOW_PLAYING, SortType.UPCOMING,SortType.TOP_RATED);
    }

    @Override
    String getTitle(SortType sortType) {
        switch (sortType){
            case POPULAR:
                return getString(R.string.sortByPopularity);
            case LATEST:
                return getString(R.string.sortByPopularity);
            case NOW_PLAYING:
                return getString(R.string.sortByPopularity);
            case UPCOMING:
                return getString(R.string.sortByPopularity);
            case TOP_RATED:
                return getString(R.string.sortByPopularity);
            default:
                throw new IllegalArgumentException();
        }

    }

    @Inject
    @TV
    @Override
    public void attachPresenter(@NonNull @TV MediaContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}
