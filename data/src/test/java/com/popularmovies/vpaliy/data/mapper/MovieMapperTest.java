package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;
import com.popularmovies.vpaliy.domain.model.MovieCover;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class MovieMapperTest extends MapperTest {


    private MovieMapper mapper;

    @Before
    public void setUp(){
        when(qualityConfiguration.convertBackdrop(anyString())).thenReturn(FAKE_PATH);
        when(qualityConfiguration.convertCover(anyString())).thenReturn(FAKE_PATH);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FAKE_PATH);
        mapper=new MovieMapper(qualityConfiguration);
    }


    @Test
    public void testMapToMovieCover(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        MovieCover movieCover=mapper.map(movie);

        assertThat(movieCover.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieCover.getPosterPath(),is(convert(movie.getPosterPath())));
        assertThat(movieCover.getMovieId(),is(movie.getMovieId()));
        assertThat(movieCover.getMovieTitle(),is(movie.getTitle()));
    }

    @Test
    public void testReverseMapping(){
        MovieCover movieCover=mapper.map(DataSourceTestUtils.provideFakeMovie());
        Movie movie=mapper.reverseMap(movieCover);

        assertThat(movieCover.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieCover.getPosterPath(),is(convert(movie.getPosterPath())));
        assertThat(movieCover.getMovieId(),is(movie.getMovieId()));
        assertThat(movieCover.getMovieTitle(),is(movie.getTitle()));
    }

    @Test
    public void testConvertToRuntime(){
        String duration="2 hrs 50 min";
        assertThat(170,is(mapper.convertToRuntime(duration)));
        assertThat(0,is(mapper.convertToRuntime(null)));
        assertThat(50,is(mapper.convertToRuntime("50 min")));
        assertThat(3000,is(mapper.convertToRuntime("50 hrs")));
        assertThat(60,is(mapper.convertToRuntime("1 hr")));
    }

    @Test
    public void testConvertToDuration(){
        int duration=120;
        assertThat("2 hrs ",is(mapper.convertToDuration(duration)));
        assertThat(null,is(mapper.convertToDuration(0)));
        duration=123;
        assertThat("2 hrs 3 min",is(mapper.convertToDuration(duration)));
        duration=60;
        assertThat("1 hr ", is(mapper.convertToDuration(duration)));
    }

    @Test
    public void testBackdropConverting(){
        Movie movie=DataSourceTestUtils.provideFakeMovie();

        List<String> strings=BackdropImage.convert(movie.getBackdropImages(),qualityConfiguration);
        List<BackdropImage> backdrops=BackdropImage.convertToBackdrops(strings,qualityConfiguration);

        assertThat(strings.size(),is(backdrops.size()));
        for(int index=0;index<backdrops.size();index++){
            assertThat(strings.get(index),is(backdrops.get(index).getBackdropPath()));
        }

    }


    @Test
    public void testGenreConverting(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();

        List<String> strings= Genre.convert(movie.getGenres());
        List<Genre> genres=Genre.convertToGenres(strings);

        assertThat(genres.size(),is(strings.size()));
        for(int index=0;index<genres.size();index++){
            assertThat(strings.get(index),is(genres.get(index).getName()));
        }
    }


    private String convert(String path){
        return qualityConfiguration.convertBackdrop(path);
    }

}
