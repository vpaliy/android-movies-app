package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.View;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;


@ViewScope
public class MoviesPresenter implements MoviesContract.Presenter{


    private final IMediaRepository<MovieCover,MovieDetails> iRepository;
    private final BaseSchedulerProvider schedulerProvider;
    private final CompositeSubscription subscriptions;
    private View view;

    @Inject
    public MoviesPresenter(@NonNull IMediaRepository<MovieCover,MovieDetails> iRepository,
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
    public void start(SortType sortType) {
       startLoading(sortType);
    }

    @Override
    public void stop() {
        view=null;
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }

    @Override
    public void requestDataRefresh(@NonNull SortType sortType) {
        startLoading(sortType);
    }

    private void startLoading(SortType sortType){
        subscriptions.clear();
        view.setLoadingIndicator(true);
        subscriptions.add(iRepository.getCovers(sortType)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(movies->processData(sortType,movies),
                        this::handleErrorMessage,
                        this::completeLoading));
    }

    private void processData(SortType sortType, List<MovieCover> movieList){
        if(movieList!=null) {
            if (!movieList.isEmpty()) {
                view.showMovies(sortType,movieList);
                return;
            }
        }
        view.showEmptyMessage();
    }

    @Override
    public void requestMoreData(@NonNull SortType sortType) {
        if(sortType!=SortType.FAVORITE) {
            subscriptions.clear();
            view.setLoadingIndicator(true);
            subscriptions.add(iRepository.requestMoreCovers(sortType)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(movies->appendData(sortType,movies),
                            this::handleErrorMessage,
                            this::completeLoading));
        }
    }

    private void appendData(SortType sortType,@Nullable List<MovieCover> movieList){
        if(movieList!=null) {
            if (!movieList.isEmpty()) {
                view.appendMovies(sortType,movieList);
            }
        }
    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();
        view.setLoadingIndicator(false);
        view.showErrorMessage();
    }

    private void completeLoading(){
        view.setLoadingIndicator(false);
    }

}
