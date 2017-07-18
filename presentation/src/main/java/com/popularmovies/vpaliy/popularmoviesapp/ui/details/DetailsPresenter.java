package com.popularmovies.vpaliy.popularmoviesapp.ui.details;


import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import java.util.List;
import rx.subscriptions.CompositeSubscription;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsContract.View;

public abstract class DetailsPresenter<T> implements MediaDetailsContract.Presenter {

    protected View view;
    private final IDetailsRepository<T> repository;
    private final ICoverRepository<MediaCover> iCoverRepository;
    private final CompositeSubscription subscriptions;
    private final BaseSchedulerProvider schedulerProvider;
    private int mediaId;

    public DetailsPresenter(@NonNull IDetailsRepository<T> repository,
                            @NonNull @Movies ICoverRepository<MediaCover> iCoverRepository,
                            @NonNull BaseSchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.iCoverRepository=iCoverRepository;
        this.subscriptions=new CompositeSubscription();
        this.mediaId =-1;
    }

    @Override
    public void shareWithMovie() {

    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void stop() {
        subscriptions.clear();
    }

    @Override
    public void make(SortType sortType) {

    }

    @Override
    public void start(int mediaId) {
        this.mediaId =mediaId;
        retrieveDetails(mediaId);
        retrieveCover(mediaId);
    }

    private void retrieveCover(int id){
        subscriptions.add(iCoverRepository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processCover,
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

    public abstract void processData(@NonNull T details);

    protected <T> boolean isEmpty(List<T> list){
        return list==null || list.isEmpty();
    }

    protected void processCover(@NonNull MediaCover movie){
        view.showCover(movie);
    }

    private void handleErrorMessage(Throwable throwable){
        throwable.printStackTrace();
    }

}
