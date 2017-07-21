package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;
import java.util.List;

public interface MediaDetailsContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showBackdrops(@NonNull List<String> backdrops);
        void shareWithMovie(@NonNull MovieDetails details);
        void showSeasons(@NonNull List<SeasonCover> seasons);
        void showDescription(@NonNull String description);
        void showDuration(@NonNull String duration);
        void showCover(@NonNull MediaCover cover);
        void showSimilar(@NonNull List<MediaCover> covers);
        void showCollection(@NonNull MediaCollection collection);
        void showTrailers(@NonNull List<Trailer> trailers);
        void showCast(@NonNull List<ActorCover> actorCovers);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void make(SortType sortType);
        void shareWithMovie();
        void start(String id);
        void stop();
    }
}
