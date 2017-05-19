package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import java.util.List;
import android.net.Uri;
import rx.Observable;

import static com.popularmovies.vpaliy.data.source.local.MoviesContract.PopularMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.FavoriteMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.TopRatedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.UpcomingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.LatestMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.NowPlayingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.WatchedhMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MustWatchMedia;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MovieLocalSourceMovie extends MediaDataSource<Movie,MovieDetailEntity> {

    private final ContentResolver contentResolver;

    @Inject
    public MovieLocalSourceMovie(@NonNull Context context){
        this.contentResolver=context.getContentResolver();
    }

    /* No more movies */
    @Override
    public Observable<List<Movie>> requestMoreCovers(@NonNull SortType sortType) { return null; }

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
    public Observable<List<Movie>> getCovers(@NonNull SortType sortType) {
        switch (sortType){
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
    public void insert(Movie item, SortType sortType) {
        Uri collectionUri=PopularMedia.CONTENT_URI;
        switch (sortType){
            case TOP_RATED:
                collectionUri=TopRatedMedia.CONTENT_URI;
                break;
            case FAVORITE:
                collectionUri=FavoriteMedia.CONTENT_URI;
                break;
            case LATEST:
                collectionUri=LatestMedia.CONTENT_URI;
                break;
            case NOW_PLAYING:
                collectionUri=NowPlayingMedia.CONTENT_URI;
                break;
            case UPCOMING:
                collectionUri=UpcomingMedia.CONTENT_URI;
                break;
            case MUST_WATCH:
                collectionUri=MustWatchMedia.CONTENT_URI;
                break;
            case WATCHED:
                collectionUri=WatchedhMedia.CONTENT_URI;
                break;
        }

        MoviesHandler.start(contentResolver)
                .insert(item)
                .insertInCollection(collectionUri,item);
    }

    @Override
    public Observable<Movie> getCover(int ID) {
        return Observable.fromCallable(()->
                MoviesHandler.start(contentResolver)
                    .queryById(ID)
                    .build(ID));
    }


    @Override
    public boolean isType(int movieId, SortType sortType) {
        return false;
    }

    @Override
    public void insertDetails(MovieDetailEntity details) {
        MoviesHandler.start(contentResolver)
                .insertDetails(details);
    }

    @Override
    public void update(Movie item, @NonNull SortType sortType) {
        switch (sortType) {
            case FAVORITE:
                if (item.isFavorite()) {
                    Uri uri = FavoriteMedia.buildFavoriteMediaUri(Integer.toString(item.getMovieId()));
                    contentResolver.delete(uri, null, null);
                } else {
                    ContentValues values = new ContentValues();
                    values.put(FavoriteMedia.COLLECTION_MEDIA_ID, item.getMovieId());
                    contentResolver.insert(FavoriteMedia.CONTENT_URI, values);
                }
        }
    }
}
