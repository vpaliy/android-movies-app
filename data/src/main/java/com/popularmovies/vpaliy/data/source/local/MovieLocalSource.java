package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import rx.Observable;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.PopularMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.FavoriteMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.TopRatedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.UpcomingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.LatestMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.NowPlayingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.WatchedhMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MustWatchMedia;

import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import android.support.annotation.NonNull;
import javax.inject.Inject;

public class MovieLocalSource extends DataSource<Movie,MovieDetailEntity>{


    private static final String TAG= MovieLocal.class.getSimpleName();

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
    public Observable<List<Movie>> requestMoreCovers() { return null; }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {
        return Observable.fromCallable(()->
                MoviesHandler.start(contentResolver)
                    .queryById(ID)
                    .appendCast(ID)
                    .appendReviews(ID)
                    .appendTrailers(ID)
                    .buildDetails());
    }

    @Override
    public Observable<List<Movie>> getCovers() {
        switch (sortConfiguration.getConfiguration()){
            case TOP_RATED:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                        .queryAll(TopRatedMedia.CONTENT_URI));
            case FAVORITE:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(FavoriteMedia.CONTENT_URI));
            case WATCHED:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(WatchedhMedia.CONTENT_URI));
            case MUST_WATCH:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(MustWatchMedia.CONTENT_URI));
            case UPCOMING:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(UpcomingMedia.CONTENT_URI));
            case NOW_PLAYING:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(NowPlayingMedia.CONTENT_URI));
            case LATEST:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(LatestMedia.CONTENT_URI));
            default:
                return Observable.fromCallable(()->
                        MoviesHandler.start(contentResolver)
                                .queryAll(PopularMedia.CONTENT_URI));
        }
    }

    @Override
    public void insert(Movie item) {
        final ContentValues values=DatabaseUtils.convertToValues(item);

        contentResolver.insert(Movies.CONTENT_URI,values);
        ContentValues configValues=new ContentValues();
        switch (sortConfiguration.getConfiguration()){
            case POPULAR:
                configValues.put(PopularMedia.COLLECTION_MEDIA_ID,item.getMovieId());
                contentResolver.insert(PopularMedia.CONTENT_URI,configValues);
                break;
            case TOP_RATED:
                configValues.put(TopRatedMedia.COLLECTION_MEDIA_ID,item.getMovieId());
                contentResolver.insert(TopRatedMedia.CONTENT_URI,configValues);
                break;
            case FAVORITE:
                configValues.put(FavoriteMedia.COLLECTION_MEDIA_ID,item.getMovieId());
                contentResolver.insert(FavoriteMedia.CONTENT_URI,configValues);
                break;
        }

    }



    @Override
    public Observable<Movie> getCover(int ID) {
        return Observable.fromCallable(()->
                MoviesHandler.start(contentResolver)
                    .queryById(ID)
                    .build(ID));
    }

    @Override
    public Observable<List<Movie>> sortBy(@NonNull ISortConfiguration.SortType type) {
        sortConfiguration.saveConfiguration(type);
        return getCovers();
    }

    @Override
    public boolean isFavorite(int movieId) {
        Uri uri=FavoriteMedia.buildFavoriteMediaUri(Integer.toString(movieId));
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if(cursor==null||!cursor.moveToFirst()){
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public void insertDetails(MovieDetailEntity details) {

    }

    @Override
    public void update(Movie item) {
        if(item.isFavorite()){
            Uri uri=FavoriteMedia.buildFavoriteMediaUri(Integer.toString(item.getMovieId()));
            contentResolver.delete(uri,null,null);
        }else{
            ContentValues values=new ContentValues();
            values.put(FavoriteMedia.COLLECTION_MEDIA_ID,item.getMovieId());
            contentResolver.insert(FavoriteMedia.CONTENT_URI,values);
        }
    }
}
