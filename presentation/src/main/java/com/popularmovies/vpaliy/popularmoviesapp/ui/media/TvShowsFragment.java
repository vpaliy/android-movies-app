package com.popularmovies.vpaliy.popularmoviesapp.ui.media;


import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;

import java.util.Arrays;
import java.util.List;
import android.graphics.Color;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.MediaType;
import javax.inject.Inject;

public class TvShowsFragment extends MediaFragment {

    @Override @Inject @TV
    public void attachPresenter(@NonNull @TV MediaContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public List<SortType> getSortTypes() {
        return Arrays.asList(SortType.POPULAR, SortType.LATEST,
                SortType.NOW_PLAYING, SortType.UPCOMING,SortType.TOP_RATED);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.TV_SHOWS;
    }

    @Override
    public int getColor(SortType sortType) {
        return Color.parseColor("#42a5f5");
    }

    @Override
    public String getTitle(SortType sortType) {
        switch (sortType){
            case POPULAR:
                return getString(R.string.popular_media);
            case LATEST:
                return getString(R.string.latest_media);
            case NOW_PLAYING:
                return getString(R.string.on_the_air_tv);
            case UPCOMING:
                return getString(R.string.today_airing_tv);
            case TOP_RATED:
                return getString(R.string.top_rated_media);
            default:
                throw new IllegalArgumentException();
        }
    }
}
