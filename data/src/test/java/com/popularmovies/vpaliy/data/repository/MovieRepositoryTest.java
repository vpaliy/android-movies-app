package com.popularmovies.vpaliy.data.repository;

import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryTest {
/*
    private static final int FAKE_MOVIE_ID = 123;


    @Mock
    private Mapper<MovieCover, Movies> mockMovieMapper;

    @Mock
    private Mapper<MovieDetails, MovieDetailEntity> mockMovieDetailsMapper;

    @Mock
    private MediaDataSource<Movies, MovieDetailEntity> mockRemoteDataSource;

    @Mock
    private MediaDataSource<Movies,MovieDetailEntity> mockLocalDataSource;

    @InjectMocks
    private MovieRepository movieRepository;

    @Before
    public void setUp(){
        given(mockRemoteDataSource.getDetails(FAKE_MOVIE_ID))
                .willReturn(Observable.just(Mockito.mock(MovieDetailEntity.class)));
        given(mockRemoteDataSource.getCover(FAKE_MOVIE_ID))
                .willReturn(Observable.just(Mockito.mock(Movies.class)));
        given(mockRemoteDataSource.getCovers())
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));
        given(mockRemoteDataSource.requestMoreCovers())
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));
        given(mockRemoteDataSource.sortBy(ISortConfiguration.SortType.POPULAR))
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));
        given(mockRemoteDataSource.sortBy(ISortConfiguration.SortType.TOP_RATED))
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));

        given(mockLocalDataSource.getDetails(FAKE_MOVIE_ID))
                .willReturn(Observable.just(Mockito.mock(MovieDetailEntity.class)));
        given(mockLocalDataSource.getCover(FAKE_MOVIE_ID))
                .willReturn(Observable.just(Mockito.mock(Movies.class)));
        given(mockLocalDataSource.getCovers())
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));
        given(mockLocalDataSource.requestMoreCovers())
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));
        given(mockLocalDataSource.sortBy(ISortConfiguration.SortType.POPULAR))
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));
        given(mockLocalDataSource.sortBy(ISortConfiguration.SortType.TOP_RATED))
                .willReturn(Observable.just(Collections.singletonList(Mockito.mock(Movies.class))));

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
    */
}
