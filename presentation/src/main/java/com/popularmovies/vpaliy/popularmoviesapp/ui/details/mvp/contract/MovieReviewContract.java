package com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;

import java.util.List;

public interface MovieReviewContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showReviews(@NonNull List<Review> reviews);
        void showNoReviewMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int movieID);
        void stop();

    }
}
