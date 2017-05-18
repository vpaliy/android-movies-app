package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.data.utils.scheduler.ImmediateSchedulerProvider;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.DetailsMoviePresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dalvik.annotation.TestTargetClass;
import rx.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterTest extends BasePresenterTest {

/*
    @Mock
    private DetailsMovieContract.View mockView;

    @Mock
    private IMovieRepository<MovieCover,MovieDetails> mockRepository;

    private DetailsMoviePresenter presenter;

    @Before
    public void setUp(){
        presenter=new DetailsMoviePresenter(mockRepository,SCHEDULER_PROVIDER);
        presenter.attachView(mockView);
        init();
    }

    @Test
    public void testStartFetchingDetails(){
        when(mockRepository.getCover(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_COVER));
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        presenter.start(FAKE_MOVIE_ID);

        verify(mockRepository).getCover(FAKE_MOVIE_ID);
        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
        verify(mockView).showCover(FAKE_MOVIE_COVER);
        verify(mockView).showBackdrops(FAKE_BACKDROPS);
        verify(mockView).showDetails(FAKE_MOVIE_DETAILS);
    }

    @Test
    public void testStartFetchingDetailsException(){
        when(mockRepository.getCover(FAKE_MOVIE_ID)).thenReturn(Observable.error(new Exception()));
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.error(new Exception()));
        presenter.start(FAKE_MOVIE_ID);

        verify(mockRepository).getCover(FAKE_MOVIE_ID);
        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
    }

    @Test
    public void testMakeFavorite(){
        when(mockRepository.getCover(anyInt())).thenReturn(Observable.just(FAKE_MOVIE_COVER));
        presenter.makeFavorite();
        verify(mockRepository).getCover(anyInt());
        verify(mockRepository).update(FAKE_MOVIE_COVER);
    }

    @Test
    public void testShareWithMovie(){
        when(mockRepository.getDetails(anyInt())).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        presenter.shareWithMovie();
        verify(mockRepository).getDetails(anyInt());
        verify(mockView).shareWithMovie(FAKE_MOVIE_DETAILS);
    }

    */


}
