package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;
import android.util.Log;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract.View;

@ViewScope
public class DetailsMoviePresenter implements DetailsMovieContract.Presenter {

    private static final String TAG=DetailsMoviePresenter.class.getSimpleName();

    private View view;
    private final IRepository<MovieCover,MovieDetails> repository;
    private final CompositeSubscription subscriptions;

    @Inject
    public DetailsMoviePresenter(@NonNull IRepository<MovieCover,MovieDetails> repository){
        this.repository=repository;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void start(int ID) {
        retrieveCover(ID);
        retrieveDetails(ID);
    }

    private void retrieveCover(int ID){
        subscriptions.add(repository.getCover(ID)
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        ()->{}));
    }

    private void retrieveDetails(int ID){
        subscriptions.add(repository.getDetails(ID)
                    .subscribe(this::processData,
                               this::handleErrorMessage,
                               ()->{}));
    }
    @Override
    public void stop() {
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    private void processData(@NonNull MovieCover movie){
        view.showCover(movie);
    }

    private void processData(@NonNull MovieDetails details){
        view.showDetails(details);
    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();

    }



}
