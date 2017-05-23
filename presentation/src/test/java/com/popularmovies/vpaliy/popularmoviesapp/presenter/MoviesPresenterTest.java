package com.popularmovies.vpaliy.popularmoviesapp.presenter;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoviesPresenterTest extends BasePresenterTest {

  /*  @Mock
    private MoviesContract.View mockView;

    @Mock
    private IMediaRepository<MovieCover,MovieDetails> mockRepository;

    private MediaPresenter presenter;


    @Before
    public void setUp(){
        presenter=new MediaPresenter(mockRepository, SCHEDULER_PROVIDER);
        presenter.attachView(mockView);
        init();

    }

    @Test
    public void testStartFetchingData(){
        when(mockRepository.getCovers()).thenReturn(Observable.just(FAKE_COVER_LIST));
        presenter.start();

        verify(mockRepository).getCovers();
        verify(mockView).showMedia(FAKE_COVER_LIST);
        verify(mockView).setLoadingIndicator(true);
        verify(mockView).setLoadingIndicator(false);
    }

    @Test
    public void testStartFetchingEmptyData(){
        when(mockRepository.getCovers()).thenReturn(Observable.just(FAKE_EMPTY_COVER_LIST));
        presenter.start();

        verify(mockRepository).getCovers();
        verify(mockView).showEmptyMessage();
        verify(mockView).setLoadingIndicator(true);
        verify(mockView).setLoadingIndicator(false);
    }

    @Test
    public void testStartFetchingDataWithException(){
        when(mockRepository.getCovers()).thenReturn(Observable.error(new Exception()));
        presenter.start();

        verify(mockRepository).getCovers();
        verify(mockView).showErrorMessage();
        verify(mockView).setLoadingIndicator(true);
        verify(mockView).setLoadingIndicator(false);
    }

    @Test
    public void testRequestMoreData(){
        when(mockRepository.requestMoreCovers()).thenReturn(Observable.just(FAKE_COVER_LIST));
        presenter.requestMore();

        verify(mockRepository).requestMoreCovers();
        verify(mockView).appendMedia(FAKE_COVER_LIST);
        verify(mockView).setLoadingIndicator(false);
        verify(mockView).setLoadingIndicator(true);

    }

    @Test
    public void testRequestEmptyData(){
        when(mockRepository.requestMoreCovers()).thenReturn(Observable.just(FAKE_EMPTY_COVER_LIST));
        presenter.requestMore();

        verify(mockRepository).requestMoreCovers();
        verify(mockView,times(0)).appendMedia(FAKE_COVER_LIST);
        verify(mockView,times(0)).appendMedia(FAKE_EMPTY_COVER_LIST);
        verify(mockView).setLoadingIndicator(false);
        verify(mockView).setLoadingIndicator(true);
    }

    @Test
    public void testRequestDataWithException(){
        when(mockRepository.requestMoreCovers()).thenReturn(Observable.error(new Exception()));
        presenter.requestMore();

        verify(mockRepository).requestMoreCovers();
        verify(mockView,times(0)).appendMedia(FAKE_COVER_LIST);
        verify(mockView,times(0)).appendMedia(FAKE_EMPTY_COVER_LIST);
        verify(mockView).showErrorMessage();
        verify(mockView).setLoadingIndicator(false);
        verify(mockView).setLoadingIndicator(true);
    }


    @Test
    public void testSort(){
        when(mockRepository.sortBy(any(ISortConfiguration.SortType.class))).thenReturn(Observable.just(FAKE_COVER_LIST));
        presenter.sort(ISortConfiguration.SortType.TOP_RATED);
        presenter.sort(ISortConfiguration.SortType.POPULAR);
        presenter.sort(ISortConfiguration.SortType.FAVORITE);

        verify(mockRepository).sortBy(ISortConfiguration.SortType.TOP_RATED);
        verify(mockRepository).sortBy(ISortConfiguration.SortType.POPULAR);
        verify(mockRepository).sortBy(ISortConfiguration.SortType.FAVORITE);
        verify(mockView,times(3)).setLoadingIndicator(false);
        verify(mockView,times(3)).setLoadingIndicator(true);
        verify(mockView,times(3)).showMedia(FAKE_COVER_LIST);
    }

    @Test
    public void testSortEmptyData(){
        when(mockRepository.sortBy(any(ISortConfiguration.SortType.class))).thenReturn(Observable.just(FAKE_EMPTY_COVER_LIST));
        presenter.sort(ISortConfiguration.SortType.TOP_RATED);
        presenter.sort(ISortConfiguration.SortType.POPULAR);
        presenter.sort(ISortConfiguration.SortType.FAVORITE);

        verify(mockRepository).sortBy(ISortConfiguration.SortType.TOP_RATED);
        verify(mockRepository).sortBy(ISortConfiguration.SortType.POPULAR);
        verify(mockRepository).sortBy(ISortConfiguration.SortType.FAVORITE);
        verify(mockView,times(3)).setLoadingIndicator(false);
        verify(mockView,times(3)).setLoadingIndicator(true);
        verify(mockView,times(3)).showEmptyMessage();
    }


    @Test
    public void testSortWithException(){
        when(mockRepository.sortBy(any(ISortConfiguration.SortType.class))).thenReturn(Observable.error(new Exception()));
        presenter.sort(ISortConfiguration.SortType.TOP_RATED);
        presenter.sort(ISortConfiguration.SortType.POPULAR);
        presenter.sort(ISortConfiguration.SortType.FAVORITE);

        verify(mockRepository).sortBy(ISortConfiguration.SortType.TOP_RATED);
        verify(mockRepository).sortBy(ISortConfiguration.SortType.POPULAR);
        verify(mockRepository).sortBy(ISortConfiguration.SortType.FAVORITE);
        verify(mockView,times(3)).setLoadingIndicator(false);
        verify(mockView,times(3)).setLoadingIndicator(true);
        verify(mockView,times(3)).showErrorMessage();
    }

    */

}
