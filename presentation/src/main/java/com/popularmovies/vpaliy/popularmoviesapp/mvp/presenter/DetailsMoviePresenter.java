package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;

import javax.inject.Inject;

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
      /*  subscriptions.add(repository.findById(ID)
            .subscribe(this::processData,
                       this::handleErrorMessage,()->{}));   */
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

    }

    private void handleErrorMessage(Throwable throwable){
        Log.e(TAG,throwable.getMessage());

    }


}
