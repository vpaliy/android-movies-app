package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;
import java.util.List;

public interface ActorContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showImages(@NonNull List<String> images);
        void showProfilePhoto(@NonNull String profilePhoto);
        void showBioDetails(@NonNull ActorDetailEntity entity);
        void showBackground(@NonNull String backdropPath);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int id);
        void stop();
    }
}
