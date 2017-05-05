package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract.View;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.util.Log;

@ViewScope
public class DetailsMoviePresenter implements DetailsMovieContract.Presenter {


    private static final String TAG=DetailsMoviePresenter.class.getSimpleName();

    private View view;
    private final IMovieRepository<MovieCover,MovieDetails> repository;
    private final CompositeSubscription subscriptions;
    private final BaseSchedulerProvider schedulerProvider;
    private int movieId;

    @Inject
    public DetailsMoviePresenter(@NonNull IMovieRepository<MovieCover,MovieDetails> repository,
                                 @NonNull BaseSchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.subscriptions=new CompositeSubscription();
        this.movieId=-1;
    }

    @Override
    public void start(int ID) {
        this.movieId=ID;
        retrieveCover(ID);
        retrieveDetails(ID);
    }

    private void retrieveCover(int ID){
        subscriptions.add(repository.getCover(ID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        ()->{}));
    }

    private void retrieveDetails(int ID){
        subscriptions.add(repository.getDetails(ID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        ()->{}));
    }

    private void processData(@NonNull MovieDetails details){
        MovieCover cover=details.getMovieCover();
        if(cover.getBackdrops()!=null){
            view.showBackdrops(cover.getBackdrops());
        }
        view.showDetails(details);
    }

    private void processData(@NonNull MovieCover movie){
        view.showCover(movie);

    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();

    }

    @Override
    public void stop() {
        view=null;
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }


    @Override
    public void makeFavorite() {
        subscriptions.add(repository.getCover(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(repository::update));
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }


    @Override
    public void shareWithMovie() {
        subscriptions.add(repository.getDetails(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(view::shareWithMovie,
                           this::handleErrorMessage,
                           ()->{}));
    }




}
