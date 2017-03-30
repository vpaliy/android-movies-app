package com.popularmovies.vpaliy.popularmoviesapp.data.source.remote;

import com.popularmovies.vpaliy.popularmoviesapp.data.entity.MovieEntity;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.DataSource;
import java.util.List;
import rx.Observable;

public class RemoteSource extends DataSource<MovieEntity>{

    @Override
    public Observable<List<MovieEntity>> getList() {
        return null;
    }

    @Override
    public Observable<MovieEntity> findById(int ID) {
        return null;
    }
}
