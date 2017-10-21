package com.popularmovies.vpaliy.popularmoviesapp.ui.details;


import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.Trailer;

import java.util.List;
import rx.subscriptions.CompositeSubscription;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsContract.View;

@SuppressWarnings("WeakerAccess")
public abstract class DetailsPresenter<T> implements MediaDetailsContract.Presenter {

    protected View view;
    protected final IDetailsRepository<T> repository;
    protected final ICoverRepository<MediaCover> iCoverRepository;
    protected final CompositeSubscription subscriptions;
    protected final BaseSchedulerProvider schedulerProvider;
    protected String mediaId;

    public DetailsPresenter(@NonNull IDetailsRepository<T> repository,
                            @NonNull @Movies ICoverRepository<MediaCover> iCoverRepository,
                            @NonNull BaseSchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.iCoverRepository=iCoverRepository;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void stop() {
        view=null;
        subscriptions.clear();
    }

    @Override
    public void make(SortType sortType) {

    }

    @Override
    public void start(String mediaId) {
        this.mediaId =mediaId;
        retrieveDetails(mediaId);
        retrieveCover(mediaId);
    }

    private void retrieveCover(String id){
        subscriptions.add(iCoverRepository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processCover,
                        this::handleErrorMessage,()->{}));
    }

    private void retrieveDetails(String id){
        subscriptions.add(repository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,
                        this::handleErrorMessage,
                        ()->{}));
    }

    protected void showRecommendations(List<MediaCover> recommendations){
        if(!isEmpty(recommendations)){
            view.showRecommendations(recommendations);
        }
    }

    protected void showCast(List<ActorCover> cast){
        if(!isEmpty(cast)){
            view.showCast(cast);
        }
    }

    protected void showSimilar(List<MediaCover> similar){
        if(!isEmpty(similar)){
            view.showSimilar(similar);
        }
    }

    protected void showTrailers(List<Trailer> trailers){
        if(!isEmpty(trailers)){
            view.showTrailers(trailers);
        }
    }

    protected void showBackdrops(List<String> backdrops){
        if(!isEmpty(backdrops)){
            view.showBackdrops(backdrops);
        }
    }

    public abstract void processData(@NonNull T details);

    <V> boolean isEmpty(List<V> list){
        return list==null || list.isEmpty();
    }

    private void processCover(@NonNull MediaCover movie){
        view.showCover(movie);
    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();
    }

}
