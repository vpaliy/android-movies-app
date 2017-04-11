package com.popularmovies.vpaliy.data.repository;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Collections;
import rx.Observable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryTest {

    private static final int FAKE_MOVIE_ID = 123;


    @Mock
    private Mapper<MovieCover, Movie> mockMovieMapper;
    @Mock
    private Mapper<MovieDetails, MovieDetailEntity> mockMovieDetailsMapper;
    @Mock
    private DataSource<Movie, MovieDetailEntity> mockRemoteDataSource;

    @InjectMocks
    private MovieRepository movieRepository;

    @Before
    public void setUp(){
        given(mockRemoteDataSource.getDetails(FAKE_MOVIE_ID))
                .willReturn(Observable.just(Mockito.mock(MovieDetailEntity.class)));
        given(mockRemoteDataSource.getCover(FAKE_MOVIE_ID))
                .willReturn(Observable.just(Mockito.mock(Movie.class)));
        given(mockRemoteDataSource.getCovers())
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movie.class))));
        given(mockRemoteDataSource.requestMoreCovers())
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movie.class))));
        given(mockRemoteDataSource.sortBy(ISortConfiguration.SortType.POPULAR))
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movie.class))));
        given(mockRemoteDataSource.sortBy(ISortConfiguration.SortType.TOP_RATED))
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movie.class))));

    }

    @Test
    public void testGetMovieDetails(){
        movieRepository.getDetails(FAKE_MOVIE_ID);
        verify(mockRemoteDataSource).getDetails(FAKE_MOVIE_ID);

    }

    @Test
    public void testGetMovieCovers(){
        movieRepository.getCovers();
        verify(mockRemoteDataSource).getCovers();
    }

    @Test
    public void testGetMovieCoverByID(){
        movieRepository.getCover(FAKE_MOVIE_ID);
        verify(mockRemoteDataSource).getCover(FAKE_MOVIE_ID);
    }

    @Test
    public void testSortMovies(){
        movieRepository.sortBy(ISortConfiguration.SortType.POPULAR);
        verify(mockRemoteDataSource).sortBy(ISortConfiguration.SortType.POPULAR);

        movieRepository.sortBy(ISortConfiguration.SortType.TOP_RATED);
        verify(mockRemoteDataSource).sortBy(ISortConfiguration.SortType.TOP_RATED);
    }

    @Test
    public void testRequestMoreCovers(){
        movieRepository.requestMoreCovers();
        verify(mockRemoteDataSource).requestMoreCovers();
    }
}
