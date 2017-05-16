package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class RemoteSourceTest {

    private static final int FAKE_ID=123;

    @Mock
    private ISortConfiguration mockSortConfiguration;

    @Mock
    private MovieDatabaseAPI mockMovieDataBaseAPI;


    @InjectMocks
    private RemoteSourceMovie remoteSource;

    @Before
    public void setUp(){
        given(mockMovieDataBaseAPI.getPopularMovies(1))
                .willReturn(Observable.just(Mockito.mock(MovieWrapper.class)));
        given(mockMovieDataBaseAPI.getTopRatedMovies(1))
                .willReturn(Observable.just(Mockito.mock(MovieWrapper.class)));
        given(mockMovieDataBaseAPI.getMovieDetails(Integer.toString(FAKE_ID)))
                .willReturn(Observable.just(Mockito.mock(Movie.class)));
        given(mockMovieDataBaseAPI.getBackdrops(Integer.toString(FAKE_ID)))
                .willReturn(Observable.just(Mockito.mock(BackdropsWrapper.class)));
        given(mockMovieDataBaseAPI.getMovieCast(Integer.toString(FAKE_ID)))
                .willReturn(Observable.just(Mockito.mock(CastWrapper.class)));
        given(mockMovieDataBaseAPI.getSimilarMovies(Integer.toString(FAKE_ID)))
                .willReturn(Observable.just(Mockito.mock(MovieWrapper.class)));
    }

    @Test
    public void testGetPopularCovers(){
        given(mockSortConfiguration.getConfiguration()).willReturn(ISortConfiguration.SortType.POPULAR);

        remoteSource.getCovers();
        verify(mockMovieDataBaseAPI).getPopularMovies(1);
        verify(mockSortConfiguration).getConfiguration();


    }

    @Test
    public void testGetTopRatedCovers(){
        given(mockSortConfiguration.getConfiguration()).willReturn(ISortConfiguration.SortType.TOP_RATED);

        remoteSource.getCovers();
        verify(mockMovieDataBaseAPI).getTopRatedMovies(1);
        verify(mockSortConfiguration).getConfiguration();

    }

    @Test
    public void testGetDetails(){
        remoteSource.getDetails(FAKE_ID);

        verify(mockMovieDataBaseAPI).getBackdrops(Integer.toString(FAKE_ID));
        verify(mockMovieDataBaseAPI).getMovieDetails(Integer.toString(FAKE_ID));
        verify(mockMovieDataBaseAPI).getMovieCast(Integer.toString(FAKE_ID));
        verify(mockMovieDataBaseAPI).getSimilarMovies(Integer.toString(FAKE_ID));
    }


    @Test
    public void testGetCover(){

        remoteSource.getDetails(FAKE_ID);
        verify(mockMovieDataBaseAPI).getMovieDetails(Integer.toString(FAKE_ID));
    }


}
