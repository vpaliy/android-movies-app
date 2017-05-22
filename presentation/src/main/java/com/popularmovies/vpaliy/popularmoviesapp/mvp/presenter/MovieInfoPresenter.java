package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.View;
import rx.subscriptions.CompositeSubscription;

import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;


@ViewScope
public class MovieInfoPresenter
        implements MovieInfoContract.Presenter {


    private View view;
    private final IDetailsRepository<MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;
    private final BaseSchedulerProvider schedulerProvider;

    @Inject
    public MovieInfoPresenter(@NonNull IDetailsRepository<MovieDetails> iRepository,
                              @NonNull BaseSchedulerProvider schedulerProvider){
        this.iRepository=iRepository;
        this.schedulerProvider=schedulerProvider;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void start(int movieID) {
        subscriptions.add(iRepository.get(movieID)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(this::processData,
                               this::handleError,
                               subscriptions::clear));
    }


    private void processData(@NonNull MovieDetails details){
        if(details.getMovieInfo()!=null){
            view.showGeneralInfo(details.getMovieInfo());
        }else{
            view.showNoInfoMessage();
        }

        if(details.getSimilarMovies()!=null){
            view.showSimilarMovies(details.getSimilarMovies());
        }

        if(details.getTrailers()!=null){
            view.showTrailers(details.getTrailers());
        }
    }

    private void handleError(@NonNull Throwable throwable){
        throwable.printStackTrace();
    }


    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void stop() {
        view=null;
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }
}
