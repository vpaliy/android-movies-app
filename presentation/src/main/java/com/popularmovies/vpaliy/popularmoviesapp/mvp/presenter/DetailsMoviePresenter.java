package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.data.utils.SchedulerProvider;
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

@ViewScope
public class DetailsMoviePresenter implements DetailsMovieContract.Presenter {


    private View view;
    private final IRepository<MovieCover,MovieDetails> repository;
    private final CompositeSubscription subscriptions;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public DetailsMoviePresenter(@NonNull IRepository<MovieCover,MovieDetails> repository,
                                 @NonNull SchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void start(int ID) {
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
                .subscribeOn(schedulerProvider.io())    //was multi
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        ()->{}));
    }
    @Override
    public void stop() {
        view=null;
        if(subscriptions.hasSubscriptions()){
            subscriptions.clear();
        }
    }


    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    private void processData(@NonNull MovieCover movie){
        view.showCover(movie);

    }

    private void processData(@NonNull MovieDetails details){
        MovieCover cover=details.getMovieCover();
        if(cover.getBackdrops()!=null){
            view.showBackdrops(cover.getBackdrops());
        }
        view.showDetails(details);
    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();

    }



}
