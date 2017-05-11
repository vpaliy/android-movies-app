package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import com.google.common.annotations.VisibleForTesting;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import rx.Observable;


import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Actors;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Genres;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Trailers;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Reviews;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.PopularMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.FavoriteMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.TopRatedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.UpcomingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.LatestMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.RecommendedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.NowPlayingMedia;

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
        Uri uri= Movies.buildMovieWithDetailsUri(Integer.toString(ID));
        return Observable.fromCallable(()->{
           Movie movie=toMovie(contentResolver.query(uri,null,null,null,null));
           if(movie!=null){
               MovieDetailEntity detailEntity=new MovieDetailEntity();
               detailEntity.setMovie(movie);
               detailEntity.setBackdropImages(movie.getBackdropImages());
               return detailEntity;
           }
           return null;
        });
    }

    @Override
    public Observable<List<Movie>> getCovers() {
        switch (sortConfiguration.getConfiguration()){
            case TOP_RATED:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.
                                TopRatedMedia.CONTENT_URI, null,null,null,null)));
            case FAVORITE:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(TopRatedMedia.CONTENT_URI,
                                null,null,null,null)));
            default:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.
                                PopularMedia.CONTENT_URI, null,null,null,null)));
        }
    }


    private List<Movie> toMovies(Cursor cursor){
        if(cursor!=null){

            if(cursor.moveToFirst()){
                List<Movie> movies=new ArrayList<>(cursor.getCount());
                do{
                    movies.add(DatabaseUtils.convertToMovie(cursor));
                }while(cursor.moveToNext());
                return movies;
            }

            if(!cursor.isClosed()){
                cursor.close();
            }
        }
        return new ArrayList<>();
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


    private Movie toMovie(Cursor cursor){
        if(cursor!=null){
            if(cursor.moveToFirst()){
                Movie movie=DatabaseUtils.convertToMovie(cursor);
                if(!cursor.isClosed()) cursor.close();
                return movie;
            }
        }
        return null;
    }

    @Override
    public Observable<Movie> getCover(int ID) {
        Uri uri= Movies.buildMovieUri(Integer.toString(ID));
        return Observable.fromCallable(()->toMovie(contentResolver.query(uri,null,null,null,null)));
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
