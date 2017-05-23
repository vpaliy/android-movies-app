package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieInfoPresenterTest extends BasePresenterTest {

    /*@Mock
    private IMediaRepository<MediaCover,MovieDetails> mockRepository;

    @Mock
    private MovieInfoContract.View mockView;


    private MovieInfoPresenter presenter;

    @Before
    public void setUp(){
        presenter=new MovieInfoPresenter(mockRepository,SCHEDULER_PROVIDER);
        presenter.attachView(mockView);
        init();
    }

    @Test
    public void testStartFetchingData(){
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        presenter.start(FAKE_MOVIE_ID);

        verify(mockView).showTrailers(FAKE_TRAILERS);
        verify(mockView).showSimilarMovies(FAKE_COVER_LIST);
        verify(mockView).showGeneralInfo(FAKE_MOVIE_INFO);
        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
    }

    @Test
    public void testStartFetchingEmptyData(){
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        FAKE_MOVIE_DETAILS.setMovieInfo(null);
        presenter.start(FAKE_MOVIE_ID);

        verify(mockView).showTrailers(FAKE_TRAILERS);
        verify(mockView).showSimilarMovies(FAKE_COVER_LIST);
        verify(mockView).showNoInfoMessage();
        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
    }   */
}
