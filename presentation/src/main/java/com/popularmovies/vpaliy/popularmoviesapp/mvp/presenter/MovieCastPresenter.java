package com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter;

import com.popularmovies.vpaliy.data.utils.SchedulerProvider;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract.View;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import javax.inject.Inject;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import android.support.annotation.NonNull;

@ViewScope
public class MovieCastPresenter
        implements MovieCastContract.Presenter {


    private View view;
    private final IRepository<MovieCover,MovieDetails> iRepository;
    private final CompositeSubscription subscriptions;
    private final SchedulerProvider schedulerProvider;


    @Inject
    public MovieCastPresenter(@NonNull IRepository<MovieCover,MovieDetails> iRepository,
                              @NonNull SchedulerProvider schedulerProvider){
        this.iRepository=iRepository;
        this.subscriptions=new CompositeSubscription();
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void start(int movieId) {
        subscriptions.clear();
        subscriptions.add(iRepository.getDetails(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                          this::handleError,
                          subscriptions::clear));
    }

    @Override
    public void stop() {
        view=null;
        subscriptions.clear();
    }

    private void handleError(@NonNull Throwable throwable){
        throwable.printStackTrace();
    }

    private void processData(@NonNull MovieDetails details){
        if(details.getCast()!=null){
            if(!details.getCast().isEmpty()) {
                view.showCast(details.getCast());
                return;
            }
        }
        view.showNoCastMessage();
    }

}
