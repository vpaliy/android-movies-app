package com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.presenter;

import android.util.Log;

import com.popularmovies.vpaliy.popularmoviesapp.domain.IRepository;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.DetailsMovieContract.View;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.scope.ViewScope;

import rx.subscriptions.CompositeSubscription;

@ViewScope
public class DetailsMoviePresenter implements DetailsMovieContract.Presenter {

    private static final String TAG=DetailsMoviePresenter.class.getSimpleName();

    private View view;
    private final IRepository<Movie> repository;
    private final CompositeSubscription subscriptions;

    @Inject
    public DetailsMoviePresenter(@NonNull IRepository<Movie> repository){
        this.repository=repository;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void start(int ID) {
        subscriptions.add(repository.findById(ID)
            .subscribe(this::processData,
                       this::handleErrorMessage,()->{}));
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

    private void processData(@NonNull Movie movie){
        view.showDetails(movie);
    }

    private void handleErrorMessage(Throwable throwable){
        Log.e(TAG,throwable.getMessage());

    }


}
