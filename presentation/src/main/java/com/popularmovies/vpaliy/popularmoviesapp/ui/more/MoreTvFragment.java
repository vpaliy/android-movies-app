package com.popularmovies.vpaliy.popularmoviesapp.ui.more;


import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.Presenter;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreMediaFragment;

import android.support.annotation.NonNull;
import javax.inject.Inject;

public class MoreTvFragment extends MoreMediaFragment {

    @Override @Inject @TV
    public void attachPresenter(@NonNull @TV Presenter presenter) {
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
