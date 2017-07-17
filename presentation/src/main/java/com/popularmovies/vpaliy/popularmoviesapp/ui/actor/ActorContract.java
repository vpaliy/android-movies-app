package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;
import java.util.List;

public interface ActorContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showImages(@NonNull List<String> images);
        void showProfilePhoto(@NonNull String profilePhoto);
        void showBioDetails(@NonNull ActorDetails entity);
        void showBackground(@NonNull String backdropPath);
        void showMovieCredits(@NonNull List<MediaCover> movies);
        void showTvShowCredits(@NonNull List<MediaCover> tvShows);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int id);
        void stop();
    }
}
