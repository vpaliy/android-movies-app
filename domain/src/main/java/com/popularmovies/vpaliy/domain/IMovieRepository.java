package com.popularmovies.vpaliy.domain;

import android.support.annotation.NonNull;
import java.util.List;
import rx.Observable;

import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

public interface IMovieRepository<T,D>  {
    Observable<List<T>> requestMoreCovers(@NonNull SortType type);
    Observable<List<T>> getCovers(@NonNull SortType type);
    Observable<D> getDetails(int id);
    Observable<T> getCover(int id);
    void update(T item, @NonNull SortType sortType);
    boolean isType(int id, SortType sortType);

}
