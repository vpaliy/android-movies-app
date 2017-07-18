package com.popularmovies.vpaliy.data.source.remote;

import android.util.Log;

import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.SeasonService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class RemoteSeasonSource implements DetailsDataSource<SeasonEntity>{

    private SeasonService seasonService;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteSeasonSource(SeasonService seasonService, BaseSchedulerProvider schedulerProvider){
        this.seasonService=seasonService;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public Observable<SeasonEntity> get(String id) {
        String tvId=id.substring(0,id.indexOf("/"));
        String seasonNumber=id.substring(id.indexOf("/")+1,id.length());
        Observable<SeasonEntity> seasonObservable=seasonService.querySeason(tvId,seasonNumber)
                .subscribeOn(schedulerProvider.multi());
        Observable<CastWrapper> castObservable=seasonService.queryCast(tvId,seasonNumber)
                .subscribeOn(schedulerProvider.multi());
        Observable<BackdropsWrapper> backdropObservable=seasonService.queryImages(tvId,seasonNumber)
                .subscribeOn(schedulerProvider.multi());
        Observable<TrailerWrapper> trailerObservable=seasonService.queryVideos(tvId,seasonNumber)
                .subscribeOn(schedulerProvider.multi());
        return Observable.zip(seasonObservable,castObservable,backdropObservable,trailerObservable,
                (seasonEntity, castWrapper, backdropsWrapper, trailerWrapper) ->{
                    seasonEntity.setImages(backdropsWrapper.getBackdropImages());
                    seasonEntity.setCast(castWrapper.getCast());
                    seasonEntity.setVideos(trailerWrapper.getTrailers());
                   return seasonEntity;
                });
    }

    @Override
    public void insert(SeasonEntity item) { /* Empty */}
}
