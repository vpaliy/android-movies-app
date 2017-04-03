package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract.View;

import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

@ViewScope
public class MovieCastPresenter
        implements MovieCastContract.Presenter {

    private static final String TAG=MovieCastPresenter.class.getSimpleName();

    private View view;
    private final IRepository<MovieCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;

    @Inject
    public MovieCastPresenter(@NonNull IRepository<MovieCover,MovieDetails> iRepository){
        Log.d(TAG,"Created");
        this.iRepository=iRepository;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void start(int movieId) {
        Log.d(TAG,"start()");
        subscriptions.add(iRepository.getDetails(movieId)
                .subscribe(this::processData,
                          this::handleError,
                        ()->{}));
    }

    @Override
    public void stop() {

    }

    private void handleError(@NonNull Throwable throwable){
        throwable.printStackTrace();
    }

    private void processData(@NonNull MovieDetails details){
        Log.d(TAG,"processData");
        if(details.getCast()!=null){
            if(!details.getCast().isEmpty()){
                view.showCast(details.getCast());
            }else{
                Log.e(TAG,"EMPTY");
            }
        }else{
            Log.e(TAG,"Is NULL");
        }
    }

}
