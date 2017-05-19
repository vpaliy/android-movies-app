package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieReviewPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieReviewPresenterTest extends BasePresenterTest {

    @Mock
    private IMediaRepository<MovieCover,MovieDetails> mockRepository;

    @Mock
    private MovieReviewContract.View mockView;

    private MovieReviewPresenter presenter;

    @Before
    public void setUp(){
        presenter=new MovieReviewPresenter(mockRepository,SCHEDULER_PROVIDER);
        presenter.attachView(mockView);
        init();
    }

    @Test
    public void testStartFetchingData(){
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        presenter.start(FAKE_MOVIE_ID);

        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
        verify(mockView).showReviews(FAKE_REVIEWS);

    }

    @Test
    public void testStartFetchingEmptyData(){
        when(mockRepository.getDetails(FAKE_MOVIE_ID)).thenReturn(Observable.just(FAKE_MOVIE_DETAILS));
        FAKE_MOVIE_DETAILS.setReviews(null);
        presenter.start(FAKE_MOVIE_ID);

        verify(mockRepository).getDetails(FAKE_MOVIE_ID);
        verify(mockView).showNoReviewMessage();

    }


}
