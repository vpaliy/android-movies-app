package com.popularmovies.vpaliy.data.source;


import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

public abstract class MovieDataSource<T,D> implements IMovieRepository<T,D> {
   public abstract void insert(T item, SortType sortType);
   public abstract void insertDetails(D details);

}
