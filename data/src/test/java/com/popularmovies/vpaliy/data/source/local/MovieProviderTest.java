package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;

import com.popularmovies.vpaliy.data.BuildConfig;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class MovieProviderTest  {

/*
    private static final Movie FAKE_MOVIE=DataSourceTestUtils.provideFakeMovie();
    private static final Uri MOVIE_URI= MoviesContract.MovieEntry.CONTENT_URI;
    private static final Uri MOST_POPULAR_URI=MoviesContract.MostPopularEntry.CONTENT_URI;
    private static final Uri MOST_RATED_URI=MoviesContract.MostRatedEntry.CONTENT_URI;
    private static final Uri MOVIE_BY_ID_URI=ContentUris.withAppendedId(MOVIE_URI,FAKE_MOVIE.getMovieId());
    private static final Uri MOVIE_POPULAR_BY_ID_URI=ContentUris.withAppendedId(MOST_POPULAR_URI,FAKE_MOVIE.getMovieId());
    private static final Uri MOVIE_RATED_BY_ID_URI=ContentUris.withAppendedId(MOST_RATED_URI,FAKE_MOVIE.getMovieId());

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private MovieSQLHelper sqlHelper;

    @Mock
    private SQLiteDatabase mockDatabase;


    @InjectMocks
    private MovieProvider movieProvider;


    @Test
    public void testProviderInsertMovie(){
        final Movie movie=DataSourceTestUtils.provideFakeMovie();
        when(sqlHelper.getWritableDatabase()).thenReturn(mockDatabase);
        ContentValues values=DataSourceTestUtils.provideFakeValues(movie);
        movieProvider.insert(MOVIE_URI,values);


        values.put(MoviesContract.MostPopularEntry._ID,movie.getMovieId());
        values.put(MoviesContract.MovieEntry.MOVIE_ID,movie.getMovieId());
        movieProvider.insert(MOST_POPULAR_URI, values);

        values.put(MoviesContract.MostRatedEntry._ID,movie.getMovieId());
        movieProvider.insert(MOST_RATED_URI,values);
        verify(sqlHelper,times(3)).getWritableDatabase();
        verify(mockDatabase,times(3)).insertWithOnConflict(anyString(),any(),any(ContentValues.class),anyInt());
    }

    @Test
    public void testProviderUpdateMovie(){
        final Movie movie=DataSourceTestUtils.provideFakeMovie();
        ContentValues values=DataSourceTestUtils.provideFakeValues(movie);
        when(sqlHelper.getWritableDatabase()).thenReturn(mockDatabase);
        movieProvider.update(MOVIE_URI,values,null,null);

        values.put(MoviesContract.MostPopularEntry._ID,movie.getMovieId());
        values.put(MoviesContract.MovieEntry.MOVIE_ID,movie.getMovieId());
        movieProvider.update(MOST_POPULAR_URI,values,null,null);

        values.put(MoviesContract.MostRatedEntry._ID,movie.getMovieId());
        movieProvider.update(MOST_RATED_URI,values,null,null);


        verify(sqlHelper,times(3)).getWritableDatabase();
        verify(mockDatabase,times(3)).update(any(),any(),any(),any());
    }


    @Test
    public void testProviderDelete(){
        ContentValues values=DataSourceTestUtils.provideFakeValues(FAKE_MOVIE);
        when(sqlHelper.getWritableDatabase()).thenReturn(mockDatabase);

        movieProvider.insert(MOVIE_URI,values);
        movieProvider.delete(MOVIE_URI,null,null);
        movieProvider.insert(MOVIE_URI,values);
        movieProvider.delete(MOVIE_BY_ID_URI,null,null);

        values.put(MoviesContract.MostPopularEntry._ID,FAKE_MOVIE.getMovieId());
        values.put(MoviesContract.MovieEntry.MOVIE_ID,FAKE_MOVIE.getMovieId());
        movieProvider.insert(MOST_POPULAR_URI, values);
        movieProvider.delete(MOST_POPULAR_URI,null,null);
        movieProvider.insert(MOST_POPULAR_URI,values);
        movieProvider.delete(MOVIE_POPULAR_BY_ID_URI,null,null);

        values.put(MoviesContract.MostRatedEntry._ID,FAKE_MOVIE.getMovieId());
        movieProvider.insert(MOST_RATED_URI,values);
        movieProvider.delete(MOST_RATED_URI,null,null);
        movieProvider.insert(MOST_RATED_URI,values);
        movieProvider.delete(MOVIE_RATED_BY_ID_URI,null,null);


        verify(sqlHelper,times(12)).getWritableDatabase();
        verify(mockDatabase,times(6)).delete(anyString(),any(),any());


    }

    @Test
    public void testProviderQuery(){
        ContentValues values=DataSourceTestUtils.provideFakeValues(FAKE_MOVIE);
        when(sqlHelper.getWritableDatabase()).thenReturn(mockDatabase);
        when(sqlHelper.getReadableDatabase()).thenReturn(mockDatabase);
        movieProvider.insert(MOVIE_URI,values);

        movieProvider.query(MOVIE_URI,null,null,null,null);
        movieProvider.query(MOVIE_BY_ID_URI,null,null,null,null);
        values.put(MoviesContract.MostPopularEntry._ID,FAKE_MOVIE.getMovieId());
        values.put(MoviesContract.MovieEntry.MOVIE_ID,FAKE_MOVIE.getMovieId());
        movieProvider.insert(MOST_POPULAR_URI,values);
        movieProvider.query(MOST_POPULAR_URI,null,null,null,null);
        movieProvider.query(MOVIE_POPULAR_BY_ID_URI,null,null,null,null);

        values.put(MoviesContract.MostRatedEntry._ID,FAKE_MOVIE.getMovieId());
        movieProvider.insert(MOST_RATED_URI,values);
        movieProvider.query(MOST_RATED_URI,null,null,null,null);
        movieProvider.query(MOVIE_RATED_BY_ID_URI,null,null,null,null);

        verify(sqlHelper,times(6)).getReadableDatabase();
        verify(sqlHelper,times(3)).getWritableDatabase();

        verify(mockDatabase,times(2)).query(anyString(),any(),any(),any(),any(),any(),any());
    }

    */

}
