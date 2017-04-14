package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract;

import java.util.List;

import static com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract.View;
import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MovieReviewPresenter
        implements MovieReviewContract.Presenter{

    private View view;
    private IRepository<MovieCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;

    @Inject
    public MovieReviewPresenter(@NonNull IRepository<MovieCover,MovieDetails> iRepository){
        this.iRepository=iRepository;
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processData,
                           this::handleError,
                           this::completeLoading));

    }

    private void handleError(Throwable error){
        error.printStackTrace();
    }

    private void completeLoading(){

    }

    private void processData(@NonNull MovieDetails details){
        List<Review> reviews=details.getReviews();
        if(reviews!=null && !reviews.isEmpty()){
            view.showReviews(reviews);
        }else{
            //TODO show that the movie does have reviews
        }
    }

    @Override
    public void stop() {
        subscriptions.clear();
    }
}
