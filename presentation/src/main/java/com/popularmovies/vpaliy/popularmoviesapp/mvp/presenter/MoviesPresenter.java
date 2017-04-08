package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;


import android.util.Log;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.View;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


@ViewScope
public class MoviesPresenter implements MoviesContract.Presenter{

    private static final String TAG=MoviesPresenter.class.getSimpleName();

    private View view;
    private final IRepository<MovieCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;

    @Inject
    public MoviesPresenter(@NonNull IRepository<MovieCover,MovieDetails> iRepository){
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
    public void sort(@NonNull ISortConfiguration.SortType sortType) {
       startLoading();
    }

    @Override
    public void requestDataRefresh() {
        startLoading();
    }

    private void startLoading(){
        view.setLoadingIndicator(true);
        subscriptions.add(iRepository.getCovers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processData,
                           this::handleErrorMessage,
                           this::completeLoading));
    }

   private void processData(@NonNull List<MovieCover> movieList){
       view.showErrorMessage();
        Log.d(TAG,Integer.toString(movieList.size()));
        if(!movieList.isEmpty()){
            view.showMovies(movieList);
        }else{
            view.showEmptyMessage();
        }
    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();
        view.showErrorMessage();
    }

    private void completeLoading(){
        view.setLoadingIndicator(false);
    }

}
