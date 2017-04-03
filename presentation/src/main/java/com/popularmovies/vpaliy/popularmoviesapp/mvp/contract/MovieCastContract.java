package com.popularmovies.vpaliy.popularmoviesapp.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BaseView;
import java.util.List;

public interface MovieCastContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showCast(@NonNull List<ActorCover> cast);

    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int movieId);
        void stop();
    }
}
