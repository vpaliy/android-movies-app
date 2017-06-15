package com.popularmovies.vpaliy.popularmoviesapp.presenter;


import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.View;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MediaPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class MediaCoverPresenterTest extends BasePresenterTest {

    @Mock
    private ICoverRepository<MediaCover> mockRepository;

    @Mock
    private BaseSchedulerProvider mockScheduler;

    @Mock
    private View mockView;

    @InjectMocks
    private MediaPresenter presenter;

    @Test
    public void assignsAndChecksIfViewIsNotNull(){
        presenter.attachView(mockView);
    }

    @Test
    public void loadsMediaAndPopulatesView(){
        prepareRegularLoad();
        //run the start() method for every possible case
        for(SortType value:SortType.values()) {
            presenter.start(value);
            //verify it's been called
            verify(mockRepository).get(eq(value));
            verify(mockView).showMedia(eq(value), eq(FAKE_COVER_LIST));
        }
        int totalTimes=SortType.values().length;
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void showsErrorMessageWhenMediaLoadThrowsError(){
        prepareErrorLoad();
        //run start() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.start(value);
            //verification
            verify(mockRepository).get(eq(value));
        }
        int totalTimes=SortType.values().length;
        verify(mockView,times(totalTimes)).showErrorMessage();
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void showsEmptyMessageWhenLoadedDataIsEmpty(){
        prepareEmptyLoad();
        //run start() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.start(value);
            //verification
            verify(mockRepository).get(eq(value));
        }
        int totalTimes=SortType.values().length;
        verify(mockView,times(totalTimes)).showEmptyMessage();
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void requestsRefreshAndShowsDataToView(){
        prepareRegularLoad();
        //run requestRefresh() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.requestRefresh(value);
            //verification
            verify(mockRepository).get(eq(value));
            verify(mockView).showMedia(eq(value), eq(FAKE_COVER_LIST));
        }
        int totalTimes=SortType.values().length;
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void requestsRefreshAndShowsEmptyMessage(){
        prepareEmptyLoad();
        //run the requestRefresh() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.requestRefresh(value);
            //verification
            verify(mockRepository).get(eq(value));
        }
        int totalTimes=SortType.values().length;
        verify(mockView,times(totalTimes)).showEmptyMessage();
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void requestsRefreshAndShowsErrorMessage(){
        prepareErrorLoad();
        //run the requestRefresh() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.requestRefresh(value);
            //verification
            verify(mockRepository).get(eq(value));
        }
        int totalTimes=SortType.values().length;
        verify(mockView,times(totalTimes)).showErrorMessage();
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void requestsMoreAndAppendsDataToView(){
        prepareRegularRequest();
        //run requestMore() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.requestMore(value);
            //verification
            verify(mockRepository).requestMore(eq(value));
            verify(mockView).appendMedia(eq(value), eq(FAKE_COVER_LIST));
        }
        int totalTimes=SortType.values().length;
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    @Test
    public void requestsMoreAndShowsErrorMessage(){
        prepareErrorRequest();
        //run requestMore() method for every possible sort type
        for(SortType value:SortType.values()){
            presenter.requestMore(value);
            //verification
            verify(mockRepository).requestMore(eq(value));
        }
        int totalTimes=SortType.values().length;
        verify(mockView,times(totalTimes)).showErrorMessage();
        verify(mockScheduler,times(totalTimes)).io();
        verify(mockScheduler,times(totalTimes)).ui();
    }

    private void prepareErrorRequest(){
        when(mockRepository.requestMore(any(SortType.class))).thenReturn(Observable.error(new Exception()));
        when(mockScheduler.io()).thenReturn(Schedulers.immediate());
        when(mockScheduler.ui()).thenReturn(Schedulers.immediate());
    }

    private void prepareRegularRequest(){
        when(mockRepository.requestMore(any(SortType.class))).thenReturn(Observable.just(FAKE_COVER_LIST));
        when(mockScheduler.io()).thenReturn(Schedulers.immediate());
        when(mockScheduler.ui()).thenReturn(Schedulers.immediate());
    }

    private void prepareErrorLoad(){
        when(mockRepository.get(any(SortType.class))).thenReturn(Observable.error(new Exception()));
        when(mockScheduler.io()).thenReturn(Schedulers.immediate());
        when(mockScheduler.ui()).thenReturn(Schedulers.immediate());
    }

    private void prepareRegularLoad(){
        when(mockRepository.get(any(SortType.class))).thenReturn(Observable.just(FAKE_COVER_LIST));
        when(mockScheduler.io()).thenReturn(Schedulers.immediate());
        when(mockScheduler.ui()).thenReturn(Schedulers.immediate());
    }

    private void prepareEmptyLoad(){
        when(mockRepository.get(any(SortType.class))).thenReturn(Observable.just(FAKE_EMPTY_COVER_LIST));
        when(mockScheduler.io()).thenReturn(Schedulers.immediate());
        when(mockScheduler.ui()).thenReturn(Schedulers.immediate());
    }
}
