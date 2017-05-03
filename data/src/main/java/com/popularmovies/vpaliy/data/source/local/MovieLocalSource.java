package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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
        Uri uri= ContentUris.withAppendedId(MoviesContract.MovieEntry.CONTENT_URI,ID);
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
                                MostRatedEntry.CONTENT_URI, null,null,null,null)));
            case FAVORITE:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.FavoriteEntry.CONTENT_URI,
                                null,null,null,null)));
            default:
                return Observable.fromCallable(()->
                        toMovies(contentResolver.query(MoviesContract.
                                MostPopularEntry.CONTENT_URI, null,null,null,null)));
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

        contentResolver.insert(MoviesContract.MovieEntry.CONTENT_URI,values);
        ContentValues configValues=new ContentValues();
        configValues.put(MoviesContract.MovieEntry.MOVIE_ID,item.getMovieId());
        switch (sortConfiguration.getConfiguration()){
            case POPULAR:
                configValues.put(MoviesContract.MostPopularEntry._ID,item.getMovieId());
                contentResolver.insert(MoviesContract.MostPopularEntry.CONTENT_URI,configValues);
                break;
            case TOP_RATED:
                configValues.put(MoviesContract.MostRatedEntry._ID,item.getMovieId());
                contentResolver.insert(MoviesContract.MostRatedEntry.CONTENT_URI,configValues);
                break;
            case FAVORITE:
                configValues.put(MoviesContract.FavoriteEntry._ID,item.getMovieId());
                contentResolver.insert(MoviesContract.FavoriteEntry.CONTENT_URI,configValues);
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
        Uri uri= ContentUris.withAppendedId(MoviesContract.MovieEntry.CONTENT_URI,ID);
        return Observable.fromCallable(()->toMovie(contentResolver.query(uri,null,null,null,null)));
    }

    @Override
    public Observable<List<Movie>> sortBy(@NonNull ISortConfiguration.SortType type) {
        sortConfiguration.saveConfiguration(type);
        return getCovers();
    }

    @Override
    public boolean isFavorite(int movieId) {
        Uri uri=ContentUris.withAppendedId(MoviesContract.FavoriteEntry.CONTENT_URI,movieId);
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
            Uri uri= ContentUris.withAppendedId(MoviesContract.FavoriteEntry.CONTENT_URI,item.getMovieId());
            contentResolver.delete(uri,null,null);
        }else{
            ContentValues values=new ContentValues();
            values.put(MoviesContract.MovieEntry.MOVIE_ID,item.getMovieId());
            values.put(MoviesContract.FavoriteEntry._ID,item.getMovieId());
            contentResolver.insert(MoviesContract.FavoriteEntry.CONTENT_URI,values);
        }
    }
}
