package com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.presenter;

import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieDetailsContract;
import rx.subscriptions.CompositeSubscription;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieDetailsContract.View;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;

import javax.inject.Inject;
import android.support.annotation.NonNull;

@ViewScope
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {


    private View view;
    private final IDetailsRepository<MovieDetails> repository;
    private final ICoverRepository<MediaCover> iCoverRepository;
    private final CompositeSubscription subscriptions;
    private final BaseSchedulerProvider schedulerProvider;
    private int movieId;

    @Inject
    public MovieDetailsPresenter(@NonNull IDetailsRepository<MovieDetails> repository,
                                 @NonNull @Movies ICoverRepository<MediaCover> iCoverRepository,
                                 @NonNull BaseSchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.iCoverRepository=iCoverRepository;
        this.subscriptions=new CompositeSubscription();
        this.movieId=-1;
    }

    @Override
    public void start(int ID) {
        this.movieId=ID;
        retrieveCover(ID);
        retrieveDetails(ID);
    }

    private void retrieveCover(int id){
        subscriptions.add(iCoverRepository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                            this::handleErrorMessage,()->{}));
    }

    private void retrieveDetails(int id){
        subscriptions.add(repository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        ()->{}));
    }


    private void processData(@NonNull MovieDetails details){
        MediaCover cover=details.getMovieCover();
        if(cover.getBackdrops()!=null){
            view.showBackdrops(cover.getBackdrops());
        }
        view.showDetails(details);
    }

    private void processData(@NonNull MediaCover movie){
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
    public void make(SortType sortType) {
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void shareWithMovie() {
        subscriptions.add(repository.get(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(view::shareWithMovie,
                           this::handleErrorMessage,
                           ()->{}));
    }
}
