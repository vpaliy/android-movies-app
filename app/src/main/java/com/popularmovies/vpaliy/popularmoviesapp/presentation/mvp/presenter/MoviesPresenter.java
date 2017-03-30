package com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.presenter;


import com.popularmovies.vpaliy.popularmoviesapp.domain.IRepository;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MoviesContract;
import java.util.List;
import rx.subscriptions.CompositeSubscription;
import android.util.Log;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.scope.ViewScope;
import javax.inject.Inject;

import static com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MoviesContract.View;

@ViewScope
public class MoviesPresenter implements MoviesContract.Presenter{

    private static final String TAG=MoviesPresenter.class.getSimpleName();

    private View view;
    private final IRepository<Movie> iRepository;
    private final CompositeSubscription subscriptions;

    @Inject
    public MoviesPresenter(@NonNull IRepository<Movie> iRepository){
        this.iRepository=iRepository;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void start() {
        startLoading();
    }

    @Override
    public void stop() {
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }

    @Override
    public void requestDataRefresh() {
        startLoading();
    }

    private void startLoading(){
        view.setLoadingIndicator(true);
    }

    private void processData(@NonNull List<Movie> movieList){
        if(!movieList.isEmpty()){
            view.showMovies(movieList);
        }else{
            view.showEmptyMessage();
        }
    }

    private void handleErrorMessage(Throwable throwable){
        Log.e(TAG,throwable.getMessage());
        view.showErrorMessage();
    }

    private void completeLoading(){
        view.setLoadingIndicator(false);
    }

}
