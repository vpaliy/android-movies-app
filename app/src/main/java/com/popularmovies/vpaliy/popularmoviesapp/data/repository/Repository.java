package com.popularmovies.vpaliy.popularmoviesapp.data.repository;


import com.popularmovies.vpaliy.popularmoviesapp.data.entity.MovieEntity;
import com.popularmovies.vpaliy.popularmoviesapp.data.mapper.Mapper;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.DataSource;
import com.popularmovies.vpaliy.popularmoviesapp.domain.IRepository;
import com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rx.Observable;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration.SortType;

/**
 * Implementation of a repository that was declared by the domain layer
 */

@Singleton
public class Repository implements IRepository<Movie> {


    private final DataSource<MovieEntity> dataSource;
    private final Mapper<Movie,MovieEntity> mapper;
    private final ISortConfiguration sortConfiguration;

    private List<Movie> inMemoryCache;

    @Inject
    public Repository(@NonNull DataSource<MovieEntity> dataSource,
                      @NonNull Mapper<Movie,MovieEntity> mapper,
                      @NonNull ISortConfiguration sortConfiguration){
        this.dataSource=dataSource;
        this.mapper=mapper;
        this.sortConfiguration=sortConfiguration;
    }

    @Override
    public Observable<List<Movie>> getList() {
        if(inMemoryCache==null) {
            return queryRemoteSource();
        }
        return Observable.just(inMemoryCache);

    }

    @Override
    public Observable<Movie> findById(int ID) {
        return dataSource.findById(ID).map(mapper::map);
    }

    private Observable<List<Movie>> queryRemoteSource(){
        return dataSource.getList()
                .map(mapper::map)
                .doOnNext(data->inMemoryCache=new ArrayList<>(data));
    }

    @Override
    public void sort(@NonNull SortType type) {
        sortConfiguration.saveConfiguration(type);
        switch (type){
            case POPULAR:
                Collections.sort(inMemoryCache,(o1, o2) -> o1.getAverageVote()<o2.getAverageVote()?1:
                        (o1.getAverageVote()!=o2.getAverageVote()?-1:0));
                break;
            case LATEST:
                Collections.sort(inMemoryCache,(o1, o2) -> o1.getReleaseDate().after(o2.getReleaseDate())?1:
                        (o1.getReleaseDate().before(o2.getReleaseDate())?-1:0));
                break;

        }
    }
}
