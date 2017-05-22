package com.popularmovies.vpaliy.data.repository;


import android.content.Context;

import com.popularmovies.vpaliy.data.entity.HasId;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;

import rx.Observable;

import javax.inject.Inject;
import android.support.annotation.NonNull;

public class DetailsRepository<T,F extends HasId> extends AbstractRepository<F>
        implements IDetailsRepository<T> {

    private final Mapper<T,F> mapper;
    private final DetailsDataSource<F> localSource;
    private final DetailsDataSource<F> remoteSource;

    @Inject
    public DetailsRepository(@NonNull Context context,
                             @NonNull DetailsDataSource<F> localSource,
                             @NonNull DetailsDataSource<F> remoteSource,
                             @NonNull Mapper<T,F> mapper){
        super(context,50);
        this.localSource=localSource;
        this.remoteSource=remoteSource;
        this.mapper=mapper;
    }

    @Override
    public Observable<T> get(int id) {
        if(!isCached(id)){
            if(isNetworkConnection()){
                return remoteSource.get(id)
                        .doOnNext(this::saveToDisk)
                        .doOnNext(this::cache)
                        .map(mapper::map);
            }
            return localSource.get(id)
                    .doOnNext(this::cache)
                    .map(mapper::map);
        }
        return fromCache(id).map(mapper::map);
    }

    private void saveToDisk(F fakeDetails){
        if(fakeDetails!=null){
            localSource.insert(fakeDetails);
        }
    }

    private void cache(F fakeDetails){
        if (fakeDetails != null) {
            cache(fakeDetails.id(),fakeDetails);
        }
    }
}
