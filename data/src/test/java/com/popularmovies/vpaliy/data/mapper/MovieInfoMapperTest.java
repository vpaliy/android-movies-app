package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
public class MovieInfoMapperTest {

    private MovieInfoMapper mapper;

    @Before
    public void setUp(){
        mapper=new MovieInfoMapper();
    }

    @Test
    public void mapsMovieToMovieInfo(){
        Movie movie= FakeDataProvider.provideMovieEntity();
        MovieInfo movieInfo=mapper.map(movie);
        assertThatAreEqual(movie,movieInfo);
    }

    @Test
    public void mapsMovieInfoToMovie(){
        MovieInfo movieInfo=FakeDataProvider.provideMovieInfo();
        Movie movie=mapper.reverseMap(movieInfo);
        assertThatAreEqual(movie,movieInfo);
    }

    @Test
    public void mapsMovieListToMovieInfoList(){
        List<MovieInfo> infoList=FakeDataProvider.provideMovieInfoList();
        List<Movie> movies=mapper.reverseMap(infoList);
        assertThatAreEqual(movies,infoList);
    }

    @Test
    public void mapsMovieInfoListToMovieList(){
        List<Movie> movies=FakeDataProvider.provideMovieList();
        List<MovieInfo> infoList=mapper.map(movies);
        assertThatAreEqual(movies,infoList);
    }

    private void assertThatAreEqual(List<Movie> first, List<MovieInfo> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(Movie movie, MovieInfo movieInfo){
        assertThat(movieInfo.getMovieId(),is(movie.getMovieId()));
        assertThat(movieInfo.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieInfo.getBudget(),is((Long.toString(movie.getBudget()))));
        assertThat(movieInfo.getRevenue(),is(Long.toString(movie.getRevenue())));
        assertThat(movieInfo.getDescription(),is(movie.getOverview()));
        assertThat(movieInfo.getReleaseDate(),is(movie.getReleaseDate()));
    }
}
