package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.SeasonService;
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
        return null;
    }

    @Override
    public void insert(SeasonEntity item) {

    }
}
