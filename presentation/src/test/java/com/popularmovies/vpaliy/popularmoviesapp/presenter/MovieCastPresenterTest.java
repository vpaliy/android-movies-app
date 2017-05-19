package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieCastPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieCastPresenterTest extends BasePresenterTest{


    @Mock
    private MovieCastContract.View mockView;

    @Mock
    private IMediaRepository<MovieCover,MovieDetails> mockRepository;

    private MovieCastPresenter presenter;


    @Before
    public void setUp(){
        presenter=new MovieCastPresenter(mockRepository,SCHEDULER_PROVIDER);
        presenter.attachView(mockView);
        init();
    }

    @Test
    public void testStartFetchingData(){
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        presenter.start(FAKE_MOVIE_ID);

        verify(mockView).showCast(FAKE_ACTORS);
        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
    }

    @Test
    public void testStartFetchingEmptyData(){
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        FAKE_MOVIE_DETAILS.setCast(null);
        presenter.start(FAKE_MOVIE_ID);
        verify(mockView).showNoCastMessage();
        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
    }

}
