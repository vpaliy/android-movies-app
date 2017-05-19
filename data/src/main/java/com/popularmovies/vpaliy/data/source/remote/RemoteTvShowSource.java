package com.popularmovies.vpaliy.data.source.remote;


import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class RemoteTvShowSource extends MediaDataSource<TvShow,TvShowDetailEntity> {

    private final MovieDatabaseAPI movieDatabaseAPI;
    private final BaseSchedulerProvider schedulerProvider;
    private final Map<SortType,MediaStream> pageMap;

    @Inject
    public RemoteTvShowSource(@NonNull MovieDatabaseAPI movieDatabaseAPI,
                              @NonNull BaseSchedulerProvider schedulerProvider){
        this.movieDatabaseAPI=movieDatabaseAPI;
        this.schedulerProvider=schedulerProvider;
        this.pageMap=new HashMap<>();
    }

    @Override
    public Observable<List<TvShow>> getCovers(@NonNull SortType type) {
        switch (type){
            case POPULAR:
                return null;
            case TOP_RATED:
                return null;
            case UPCOMING:
                return null;
            case NOW_PLAYING:
                return null;
        }
        return null;
    }

    @Override
    public Observable<TvShow> getCover(int id) {
        return null;
    }

    @Override
    public Observable<List<TvShow>> requestMoreCovers(@NonNull SortType type) {
        return null;
    }

    @Override
    public boolean isType(int id, SortType sortType) {
        return false;
    }

    @Override
    public void insert(TvShow item, SortType sortType) {

    }

    @Override
    public void insertDetails(TvShowDetailEntity details) {

    }

    @Override
    public Observable<TvShowDetailEntity> getDetails(int id) {
        return null;
    }

    @Override
    public void update(TvShow item, @NonNull SortType sortType) {

    }

    private class MediaStream {
        private int currentPage;
        private int totalPages;
    }
}
