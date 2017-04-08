package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import android.util.Log;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.View;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::processData,
                               this::handleError,
                            ()->{}));
    }


    private void processData(@NonNull MovieDetails details){
        if(details.getMovieInfo()!=null){
            view.showGeneralInfo(details.getMovieInfo());
        }
        //
        Log.d(TAG,Boolean.toString(details.getSimilarMovies()==null));
        if(details.getSimilarMovies()!=null){
            Log.d(TAG,Integer.toString(details.getSimilarMovies().size()));
            view.showSimilarMovies(details.getSimilarMovies());
        }
    }

    private void handleError(@NonNull Throwable throwable){
        Log.e(TAG,throwable.getMessage());
    }


    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void stop() {

    }
}
