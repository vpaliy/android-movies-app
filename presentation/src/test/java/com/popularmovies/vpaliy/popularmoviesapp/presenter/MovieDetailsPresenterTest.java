package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterTest extends BasePresenterTest {

/*
    @Mock
    private MovieDetailsContract.View mockView;

    @Mock
    private IMediaRepository<MovieCover,MovieDetails> mockRepository;

    private MovieDetailsPresenter presenter;

    @Before
    public void setUp(){
        presenter=new MovieDetailsPresenter(mockRepository,SCHEDULER_PROVIDER);
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
        presenter.share();
        verify(mockRepository).getDetails(anyInt());
        verify(mockView).share(FAKE_MOVIE_DETAILS);
    }

    */


}
