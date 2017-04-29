package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
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
import com.popularmovies.vpaliy.domain.ISortConfiguration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
        localSource.update(DataSourceTestUtils.provideFakeMovie());
        verify(contentResolver).update(any(),any(),any(),any());
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
    public void testRequestMoreCovers(){
        assertThat(localSource.requestMoreCovers()!=null,is(false));
    }

    @Test
    public void testGetDetails(){
        assertThat(localSource.getDetails(123)!=null,is(false));
    }

    private void shouldBeNull(Object object){
        assertThat(object!=null,is(false));
    }

}
