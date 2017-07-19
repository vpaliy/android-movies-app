package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.Episode;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;

import java.util.List;

public interface SeasonContract {
    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showPoster(@NonNull String poster);
        void showCover(@NonNull SeasonCover cover);
        void showImages(@NonNull List<String> images);
        void showCast(@NonNull List<ActorCover> cast);
        void showDescription(@NonNull String description);
        void showTrailers(@NonNull List<Trailer> trailers);
        void showEpisodes(@NonNull List<Episode> episodes);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(String id);
        void stop();
    }
}
