package com.popularmovies.vpaliy.domain;

import android.support.annotation.NonNull;

import java.util.List;
import rx.Observable;


/**
 *
 * @param <T> cover
 * @param <D> model's details
 */

public interface IRepository<T,D> {

    Observable<List<T>> getCovers();
    Observable<D> getDetails(int ID);
    Observable<T> getCover(int ID);


    void sort(@NonNull ISortConfiguration.SortType type);

}