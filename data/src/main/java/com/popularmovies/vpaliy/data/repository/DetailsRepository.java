package com.popularmovies.vpaliy.data.repository;


import android.content.Context;

import com.popularmovies.vpaliy.data.entity.HasId;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.qualifier.Local;
import com.popularmovies.vpaliy.data.source.qualifier.Remote;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import rx.Completable;
import rx.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class DetailsRepository<T,F extends HasId> extends AbstractRepository<F>
        implements IDetailsRepository<T> {

    private final Mapper<T,F> mapper;
    private final DetailsDataSource<F> localSource;
    private final DetailsDataSource<F> remoteSource;
    private final BaseSchedulerProvider schedulerProvider;
    @Inject
    public DetailsRepository(@NonNull @Local DetailsDataSource<F> localSource,
                             @NonNull @Remote DetailsDataSource<F> remoteSource,
                             @NonNull Context context,
                             @NonNull Mapper<T,F> mapper,
                             @NonNull BaseSchedulerProvider schedulerProvider){
        super(context,50);
        this.localSource=localSource;
        this.remoteSource=remoteSource;
        this.mapper=mapper;
        this.schedulerProvider=schedulerProvider;
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
            Completable.fromAction(()->localSource.insert(fakeDetails))
                    .subscribeOn(schedulerProvider.multi())
                    .subscribe();
        }
    }

    private void cache(F fakeDetails){
        if (fakeDetails != null) {
            cache(fakeDetails.id(),fakeDetails);
        }
    }
}
