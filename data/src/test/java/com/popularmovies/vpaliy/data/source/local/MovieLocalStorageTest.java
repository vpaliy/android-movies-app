package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;

import com.popularmovies.vpaliy.data.BuildConfig;
import com.popularmovies.vpaliy.domain.ISortConfiguration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class MovieLocalStorageTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Context context;

    @Mock
    private ContentResolver contentResolver;

    @Mock
    private ISortConfiguration configuration;


    @Before
    public void setUp(){
        when(context.getContentResolver()).thenReturn(contentResolver);
    }

    @Test
    public void testGetMovieCovers(){
        MovieLocalSource localSource=new MovieLocalSource(context,configuration);
        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.POPULAR);
        localSource.getCovers();

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.TOP_RATED);
        localSource.getCovers();

        when(configuration.getConfiguration()).thenReturn(ISortConfiguration.SortType.FAVORITE);
        localSource.getCovers();

        verify(configuration,times(3)).getConfiguration();
        verify(contentResolver).query(MoviesContract.MostPopularEntry.CONTENT_URI, null,null,null,null);
        verify(contentResolver).query(MoviesContract.MostRatedEntry.CONTENT_URI, null,null,null,null);

    }
}
