package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.SeasonDetails;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

@ViewScope
public class SeasonPresenter implements SeasonContract.Presenter{

    private IDetailsRepository<SeasonDetails> detailsRepository;
    private BaseSchedulerProvider schedulerProvider;
    private SeasonContract.View view;

    @Inject
    public SeasonPresenter(IDetailsRepository<SeasonDetails> detailsRepository,
                           BaseSchedulerProvider schedulerProvider){
        this.detailsRepository=detailsRepository;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public void attachView(@NonNull SeasonContract.View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void start(String id) {
        detailsRepository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,this::catchError);
    }

    private void processData(SeasonDetails details){
        if(details!=null){
            SeasonCover cover=details.getSeasonCover();
            view.showImages(details.getImages());
            view.showPoster(cover.getPosterPath());
            view.showCover(cover);

            if(details.getVideos()!=null){
                view.showTrailers(details.getVideos());
            }

            if(details.getCast()!=null){
                view.showCast(details.getCast());
            }

        }
    }

    private void catchError(Throwable error){
        error.printStackTrace();
    }

    @Override
    public void stop() {
        view=null;
    }
}
