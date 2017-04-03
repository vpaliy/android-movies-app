package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.View;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

@ViewScope
public class MovieInfoPresenter
        implements MovieInfoContract.Presenter {

    private static final String TAG= MovieInfoPresenter.class.getSimpleName();

    private View view;
    private final IRepository<MovieCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;

    @Inject
    public MovieInfoPresenter(@NonNull IRepository<MovieCover,MovieDetails> iRepository){
        this.iRepository=iRepository;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void start(int movieID) {
        subscriptions.add(iRepository.getDetails(movieID)
                    .subscribe(this::processData,
                               this::handleError,
                            ()->{}));
    }

    private void handleError(@NonNull Throwable throwable){
        Log.e(TAG,throwable.getMessage());


    }

    private void processData(@NonNull MovieDetails details){
        if(details.getMovieInfo()!=null){
            view.showInfo(details.getMovieInfo());
        }
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void stop() {

    }
}
