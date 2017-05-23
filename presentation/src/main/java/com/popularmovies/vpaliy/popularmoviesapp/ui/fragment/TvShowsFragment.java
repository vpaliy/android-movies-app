package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import javax.inject.Inject;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import java.util.List;

public class TvShowsFragment extends MediaFragment{

    @Override
    String getTitle(SortType sortType) {
        return null;
    }

    @Override
    List<SortType> getSortTypes() {
        return null;
    }

    @Inject
    @TV
    @Override
    public void attachPresenter(@NonNull @TV MediaContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}
