package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieReviewPresenterTest extends BasePresenterTest {
/*
    @Mock
    private IMediaRepository<MediaCover,MovieDetails> mockRepository;

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
*/

}
