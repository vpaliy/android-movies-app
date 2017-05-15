package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.google.gson.reflect.TypeToken;
import com.popularmovies.vpaliy.data.BuildConfig;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class MovieLocalStorageTest {

    /*
    private static final Scheduler UI= AndroidSchedulers.mainThread();
    private static final Scheduler IO=Schedulers.io();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Context context;

    @Mock
    private ContentResolver contentResolver;

    @Mock
    private ISortConfiguration configuration;

    private MovieLocalSource localSource;

    @Before
    public void setUp(){
        when(context.getContentResolver()).thenReturn(contentResolver);
        localSource=new MovieLocalSource(context,configuration);
    }

    @Test
    public void testGetMovieCovers(){
        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.POPULAR);
        localSource.getCovers()
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.TOP_RATED);
        localSource.getCovers()
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.FAVORITE);
        localSource.getCovers()
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);

        verify(configuration,times(3)).getConfiguration();
        verify(contentResolver).query(MoviesContract.MostPopularEntry.CONTENT_URI, null,null,null,null);
        verify(contentResolver).query(MoviesContract.MostRatedEntry.CONTENT_URI, null,null,null,null);

    }


    @Test
    public void testGetMovieById(){
        Uri CONTENT_URI_WITH_ID= ContentUris.withAppendedId(MoviesContract.MovieEntry.CONTENT_URI,123);
        localSource.getCover(123)
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);
        verify(contentResolver).query(CONTENT_URI_WITH_ID, null,null,null,null);

    }

    @Test
    public void testSortBy(){
        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.POPULAR);
        localSource.sortBy(configuration.getConfiguration())
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.TOP_RATED);
        localSource.sortBy(configuration.getConfiguration())
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.FAVORITE);
        localSource.sortBy(configuration.getConfiguration())
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(this::shouldBeNull);

        verify(configuration,times(6)).getConfiguration();
        verify(contentResolver).query(MoviesContract.MostPopularEntry.CONTENT_URI, null,null,null,null);
        verify(contentResolver).query(MoviesContract.MostRatedEntry.CONTENT_URI, null,null,null,null);
    }

    @Test
    public void testUpdateMovie(){
        Movie movie=DataSourceTestUtils.provideFakeMovie();
        movie.setFavorite(true);
        localSource.update(movie);
        movie.setFavorite(false);
        localSource.update(movie);
        verify(contentResolver).delete(ContentUris.withAppendedId(MoviesContract.FavoriteEntry.CONTENT_URI,movie.getMovieId()),null,null);
        verify(contentResolver).insert(eq(MoviesContract.FavoriteEntry.CONTENT_URI),any(ContentValues.class));
    }

    @Test
    public void testInsertMovie(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.POPULAR);
        localSource.insert(movie);

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.FAVORITE);
        localSource.insert(movie);

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.TOP_RATED);
        localSource.insert(movie);

        verify(contentResolver,times(3)).insert(eq(MoviesContract.MovieEntry.CONTENT_URI),any());
        verify(contentResolver).insert(eq(MoviesContract.MostPopularEntry.CONTENT_URI),any());
        verify(contentResolver).insert(eq(MoviesContract.MostRatedEntry.CONTENT_URI),any());

    }

    @Test
    public void testIfFavorite(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        movie.setFavorite(false);
        localSource.update(movie);
        localSource.isFavorite(movie.getMovieId());
        movie.setFavorite(true);
        localSource.isFavorite(movie.getMovieId());
        verify(contentResolver,times(2)).query(eq(ContentUris.withAppendedId(MoviesContract.FavoriteEntry.CONTENT_URI,
                movie.getMovieId())),any(),any(),any(),any());
    }

    @Test
    public void testRequestMoreCovers(){
        assertThat(localSource.requestMoreCovers()!=null,is(false));
    }

    @Test
    public void testGetDetails(){
        localSource.getDetails(123)
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(list->assertThat(list==null,is(true)));
        Movie movie=DataSourceTestUtils.provideFakeMovie();
        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.POPULAR);
        localSource.insert(movie);
        localSource.getDetails(movie.getMovieId())
                .subscribeOn(UI)
                .observeOn(UI)
                .subscribe(list->assertThat(list==null,is(false)));


    }


    private void shouldBeNull(Object object){
        assertThat(object!=null,is(false));
    }

    */

}
