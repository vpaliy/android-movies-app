package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;

import static com.popularmovies.vpaliy.data.FakeDataProvider.FAKE_POSTER_PATH;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class MovieMapperTest  {

    private MovieMapper mapper;

    @Mock
    private ImageQualityConfiguration qualityConfiguration;

    @Before
    public void setUp(){
        when(qualityConfiguration.convertBackdrop(anyString())).thenReturn(FAKE_POSTER_PATH);
        when(qualityConfiguration.convertCover(anyString())).thenReturn(FAKE_POSTER_PATH);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FAKE_POSTER_PATH);
        mapper=new MovieMapper(qualityConfiguration);
    }

    @Test
    public void testMapToMovieCover(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        MediaCover movieCover=mapper.map(movie);

        assertThat(movieCover.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieCover.getPosterPath(),is(convert(movie.getPosterPath())));
        assertThat(movieCover.getMediaId(),is(movie.getMovieId()));
        assertThat(movieCover.getMovieTitle(),is(movie.getTitle()));
    }

    @Test
    public void testReverseMapping(){
        MediaCover movieCover=mapper.map(DataSourceTestUtils.provideFakeMovie());
        Movie movie=mapper.reverseMap(movieCover);

        assertThat(movieCover.getAverageRate(),is(movie.getVoteAverage()));
        assertThat(movieCover.getPosterPath(),is(convert(movie.getPosterPath())));
        assertThat(movieCover.getMediaId(),is(movie.getMovieId()));
        assertThat(movieCover.getMovieTitle(),is(movie.getTitle()));
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
