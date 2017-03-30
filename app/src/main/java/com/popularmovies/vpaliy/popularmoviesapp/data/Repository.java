package com.popularmovies.vpaliy.popularmoviesapp.data;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.data.mapper.Mapper;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.DataSource;
import com.popularmovies.vpaliy.popularmoviesapp.domain.IRepository;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Implementation of a repository that was declared by the domain layer
 * @param <Real> real data model
 * @param <Fake> fake data model
 */
@Singleton
public class Repository<Real,Fake> implements IRepository<Real> {


    private final DataSource<Fake> dataSource;
    private final Mapper<Real,Fake> mapper;

    @Inject
    public Repository(@NonNull DataSource<Fake> dataSource,
                      @NonNull Mapper<Real,Fake> mapper){
        this.dataSource=dataSource;
        this.mapper=mapper;
    }

    @Override
    public Observable<List<Real>> getList() {
        return dataSource.getList().map(mapper::map);
    }

    @Override
    public Observable<Real> findById(int ID) {
        return dataSource.findById(ID).map(mapper::map);
    }
}
