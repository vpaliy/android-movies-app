package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;
import com.popularmovies.vpaliy.domain.model.MovieInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class MovieInfoMapperTest extends MapperTest{


    private MovieInfoMapper mapper;

    @Before
    public void setUp(){
        mapper=new MovieInfoMapper();
    }

    @Test
    public void testMapToMovieInfo(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        MovieInfo movieInfo=mapper.map(movie);
        assertThat(movieInfo.getMovieId(),is(movie.getMovieId()));
        assertThat(movieInfo.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieInfo.getBudget(),is((Long.toString(movie.getBudget()))));
        assertThat(movieInfo.getRevenue(),is(Long.toString(movie.getRevenue())));
        assertThat(movieInfo.getDescription(),is(movie.getOverview()));
        assertThat(movieInfo.getReleaseDate().toString(),is(movie.getReleaseDate()));
    }

    @Test
    public void testReverseMapping(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        MovieInfo movieInfo=mapper.map(movie);
        movie=mapper.reverseMap(movieInfo);
        assertThat(movieInfo.getMovieId(),is(movie.getMovieId()));
        assertThat(movieInfo.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieInfo.getBudget(),is((Long.toString(movie.getBudget()))));
        assertThat(movieInfo.getRevenue(),is(Long.toString(movie.getRevenue())));
        assertThat(movieInfo.getDescription(),is(movie.getOverview()));
        assertThat(movieInfo.getReleaseDate().toString(),is(movie.getReleaseDate()));
    }
}
