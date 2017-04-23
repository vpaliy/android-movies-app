package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentResolver;
import android.content.Context;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import java.util.List;
import android.database.Cursor;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import rx.Observable;

public class MovieLocalSource extends DataSource<Movie,MovieDetailEntity>{

    private final ContentResolver contentResolver;
    private final ISortConfiguration sortConfiguration;

    @Inject
    public MovieLocalSource(@NonNull Context context,
                            @NonNull ISortConfiguration sortConfiguration){
        this.contentResolver=context.getContentResolver();
        this.sortConfiguration=sortConfiguration;
    }


    /* No more movies */
    @Override
    public Observable<List<Movie>> requestMoreCovers() { return null;}

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getCovers() {
        switch (sortConfiguration.getConfiguration()){
            case POPULAR:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.
                                MostPopularEntry.CONTENT_URI, null,null,null,null)));
            case TOP_RATED:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.
                                MostRatedEntry.CONTENT_URI, null,null,null,null)));
            case FAVORITE:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.
                                FavoriteEntry.CONTENT_URI, null,null,null,null)));
        }
        return null;
    }

    private List<Movie> toMovies(Cursor cursor){
        if(cursor!=null){

        }
        return null;
    }

    private Movie toMovie(Cursor cursor){
        if(cursor!=null){

        }
        return null;
    }

    @Override
    public Observable<Movie> getCover(int ID) {
        switch (sortConfiguration.getConfiguration()){
            case POPULAR:
                return Observable.fromCallable(()->
                        toMovie(contentResolver.query(MoviesContract.
                                MostPopularEntry.CONTENT_URI, null,null,null,null)));
            case TOP_RATED:
                return Observable.fromCallable(()->
                        toMovie(contentResolver.query(MoviesContract.
                                MostRatedEntry.CONTENT_URI, null,null,null,null)));
            case FAVORITE:
                return Observable.fromCallable(()->
                        toMovie(contentResolver.query(MoviesContract.
                                FavoriteEntry.CONTENT_URI, null,null,null,null)));
        }
        return null;
    }

    @Override
    public Observable<List<Movie>> sortBy(@NonNull ISortConfiguration.SortType type) {
        sortConfiguration.saveConfiguration(type);
        return getCovers();
    }
}
