package com.popularmovies.vpaliy.popularmoviesapp.data.source.remote;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.data.entity.MovieEntity;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.DataSource;
import com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class RemoteSource extends DataSource<MovieEntity>{

    private ISortConfiguration sortConfiguration;

    @Inject
    public RemoteSource(@NonNull ISortConfiguration sortConfiguration){
        this.sortConfiguration=sortConfiguration;
    }

    @Override
    public Observable<List<MovieEntity>> getList() {
        return null;
    }

    @Override
    public Observable<MovieEntity> findById(int ID) {
        return null;
    }
}
