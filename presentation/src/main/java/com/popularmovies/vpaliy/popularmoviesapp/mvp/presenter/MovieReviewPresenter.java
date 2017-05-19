package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;


import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract;
import java.util.List;
import rx.subscriptions.CompositeSubscription;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;

import static com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract.View;

@ViewScope
public class MovieReviewPresenter
        implements MovieReviewContract.Presenter{

    private View view;
    private final IMediaRepository<MediaCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;
    private final BaseSchedulerProvider schedulerProvider;

    @Inject
    public MovieReviewPresenter(@NonNull IMediaRepository<MediaCover,MovieDetails> iRepository,
                                @NonNull BaseSchedulerProvider schedulerProvider){
        this.iRepository=iRepository;
        this.schedulerProvider=schedulerProvider;
        this.subscriptions=new CompositeSubscription();
    }


    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void start(int movieID) {
        subscriptions.clear();
        subscriptions.add(iRepository.getDetails(movieID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                           this::handleError,
                           this::completeLoading));

    }

    private void handleError(Throwable error){
        error.printStackTrace();
    }

    private void completeLoading(){}

    private void processData(@NonNull MovieDetails details){
        List<Review> reviews=details.getReviews();
        if(reviews!=null){
            if(!reviews.isEmpty()){
                view.showReviews(reviews);
                return;
            }
        }
        view.showNoReviewMessage();

    }

    @Override
    public void stop() {
        subscriptions.clear();
    }
}
