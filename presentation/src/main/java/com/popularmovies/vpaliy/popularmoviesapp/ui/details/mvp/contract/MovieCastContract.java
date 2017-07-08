package com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;
import java.util.List;

public interface MovieCastContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showCast(@NonNull List<ActorCover> cast);
        void showNoCastMessage();

    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int movieId);
        void stop();
    }
}
