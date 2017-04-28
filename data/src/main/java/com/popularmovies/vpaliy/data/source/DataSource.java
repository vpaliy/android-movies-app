package com.popularmovies.vpaliy.data.source;


import com.popularmovies.vpaliy.domain.IMovieRepository;

public abstract class DataSource<T,D> implements IMovieRepository<T,D> {
   public abstract void insert(T item);
   public abstract void insertDetails(D details);

}
