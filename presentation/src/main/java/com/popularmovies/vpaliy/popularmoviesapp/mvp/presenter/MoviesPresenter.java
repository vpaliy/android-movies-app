package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.data.utils.SchedulerProvider;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.View;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;


@ViewScope
public class MoviesPresenter implements MoviesContract.Presenter{


    private final IMovieRepository<MovieCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;
    private final SchedulerProvider schedulerProvider;
    private View view;

    @Inject
    public MoviesPresenter(@NonNull IMovieRepository<MovieCover,MovieDetails> iRepository,
                           @NonNull SchedulerProvider schedulerProvider){
        this.iRepository=iRepository;
        this.schedulerProvider=schedulerProvider;
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
        view=null;
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }

    @Override
    public void sort(@NonNull ISortConfiguration.SortType sortType) {
        subscriptions.clear();
        view.setLoadingIndicator(true);
        subscriptions.add(iRepository.sortBy(sortType)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        this::completeLoading));
    }

    @Override
    public void requestDataRefresh() {
        start();

    }

    private void startLoading(){
        subscriptions.clear();
        view.setLoadingIndicator(true);
        subscriptions.add(iRepository.getCovers()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        this::completeLoading));
    }

    private void processData(@NonNull List<MovieCover> movieList){;
        if(!movieList.isEmpty()){
            view.showMovies(movieList);
        }else{
            view.showEmptyMessage();
        }
    }

    @Override
    public void requestMoreData() {
        subscriptions.clear();
        view.setLoadingIndicator(true);
        subscriptions.add(iRepository.requestMoreCovers()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::appendData,
                        this::handleErrorMessage,
                        this::completeLoading));
    }

    private void appendData(@NonNull List<MovieCover> movieList){
        if(!movieList.isEmpty()){
            view.appendMovies(movieList);
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
