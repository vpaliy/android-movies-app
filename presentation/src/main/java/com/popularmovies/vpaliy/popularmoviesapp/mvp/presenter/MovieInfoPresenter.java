package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.View;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;


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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::processData,
                               this::handleError,
                            ()->{}));
    }


    private void processData(@NonNull MovieDetails details){
        subscriptions.clear();
        if(details.getMovieInfo()!=null){
            view.showGeneralInfo(details.getMovieInfo());
        }

        if(details.getSimilarMovies()!=null){
            view.showSimilarMovies(details.getSimilarMovies());
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
