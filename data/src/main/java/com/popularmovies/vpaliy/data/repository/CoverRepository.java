package com.popularmovies.vpaliy.data.repository;


import android.content.Context;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.HasId;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.qualifier.Local;
import com.popularmovies.vpaliy.data.source.qualifier.Remote;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * A repository that provides covers for movies, tv shows and people.
 * @param <T> domain data
 * @param <F> fake data
 */

@Singleton
public class CoverRepository<T,F extends HasId> extends AbstractRepository<F>
        implements ICoverRepository<T>{

    private final Mapper<T,F> mapper;

    private final CoverDataSource<F> localSource;
    private final CoverDataSource<F> remoteSource;

    @Inject
    public CoverRepository(@NonNull Context context,
                           @NonNull @Local CoverDataSource<F> localSource,
                           @NonNull @Remote CoverDataSource<F> remoteSource,
                           @NonNull Mapper<T, F> coverMapper){
        super(context);
        this.mapper=coverMapper;
        this.localSource=localSource;
        this.remoteSource=remoteSource;
    }

    @Override
    public Observable<List<T>> get(SortType type) {
        if(isNetworkConnection()){
            return remoteSource.get(type)
                    .doOnNext(list->save(list,type))
                    .doOnNext(this::cacheData)
                    .map(mapper::map);
        }
        return localSource.get(type)
                .doOnNext(this::cacheData)
                .map(mapper::map);
    }

    private void cacheData(List<F> list){
        if(list!=null){
            list.forEach(fake->cache(fake.id(),fake));
        }
    }


    private void save(List<F> covers, SortType type){
        if(covers!=null){
            covers.forEach(cover->localSource.insert(cover,type));
        }
    }

    @Override
    public Observable<T> get(int id) {
        if(!isCached(id)){
            if(isNetworkConnection()){
                return remoteSource.get(id)
                        .doOnNext(fake->cache(fake.id(),fake))
                        .map(mapper::map);
            }
            return localSource.get(id)
                    .doOnNext(fake->cache(fake.id(),fake))
                    .map(mapper::map);
        }
        return fromCache(id).map(mapper::map);
    }

    @Override
    public void update(T item,SortType type) {
        localSource.update(mapper.reverseMap(item),type);
    }

    @Override
    public Observable<List<T>> requestMore(SortType type) {
        if(isNetworkConnection()){
            return remoteSource.requestMore(type)
                    .doOnNext(this::cacheData)
                    .doOnNext(list->save(list,type))
                    .map(mapper::map);
        }
        return localSource.requestMore(type)
                .doOnNext(this::cacheData)
                .map(mapper::map);
    }
}
