package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.utils.MapperUtils;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MapperUtilsTest {

    @Test
    public void testConvertToTime(){
        String duration="2 hrs 50 min";
        assertThat(170,is(MapperUtils.convertToRuntime(duration)));
        assertThat(0,is(MapperUtils.convertToRuntime(null)));
        assertThat(50,is(MapperUtils.convertToRuntime("50 min")));
        assertThat(3000,is(MapperUtils.convertToRuntime("50 hrs")));
        assertThat(60,is(MapperUtils.convertToRuntime("1 hr")));
    }

    @Test
    public void testConvertToDuration(){
        int duration=120;
        assertThat("2 hrs ",is(MapperUtils.convertToDuration(duration)));
        assertThat(null,is(MapperUtils.convertToDuration(0)));
        duration=123;
        assertThat("2 hrs 3 min",is(MapperUtils.convertToDuration(duration)));
        duration=60;
        assertThat("1 hr ", is(MapperUtils.convertToDuration(duration)));
    }

    @Test
    public void testBackdropConverting(){
        Movie movie= FakeDataProvider.provideMovieEntity();
        ImageQualityConfiguration qualityConfiguration= Mockito.mock(ImageQualityConfiguration.class);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FakeDataProvider.FAKE_BACKDROP_PATH);
        when(qualityConfiguration.convertBackdrop(anyString())).thenReturn(FakeDataProvider.FAKE_BACKDROP_PATH);

        List<String> strings=MapperUtils.convert(movie.getBackdropImages(),qualityConfiguration);
        List<BackdropImage> backdrops=MapperUtils.convertToBackdrops(strings,qualityConfiguration);

        assertThat(strings.size(),is(backdrops.size()));
        for(int index=0;index<backdrops.size();index++){
            assertThat(strings.get(index),is(backdrops.get(index).getBackdropPath()));
        }
    }

    @Test
    public void testGenreConverting(){
        Movie movie= FakeDataProvider.provideMovieEntity();
        List<String> strings= MapperUtils.convert(movie.getGenres());
        List<Genre> genres= MapperUtils.convertToGenres(strings);

        assertThat(genres.size(),is(strings.size()));
        for(int index=0;index<genres.size();index++){
            assertThat(strings.get(index),is(genres.get(index).getName()));
        }
    }

    @Test
    public void testCreateTvShowCover(){
        assertThat(MapperUtils.createTvShowCover(null),nullValue());

        TvShowInfoEntity entity=FakeDataProvider.provideTvShowInfoEntity();
        TvShow tvShow=MapperUtils.createTvShowCover(entity);
        assertThat(tvShow,notNullValue());
        assertThat(tvShow.getVoteCount(),is(entity.getVoteCount()));
        assertThat(tvShow.getPosterPath(),is(entity.getPosterPath()));
        assertThat(tvShow.getId(),is(entity.getTvShowId()));
        assertThat(tvShow.getBackdropPath(),is(entity.getBackdrop_path()));
        assertThat(tvShow.getFirstAirDate(),is(entity.getFirstAirDate()));
        assertThat(tvShow.getGenreList(),is(entity.getGenres()));
        assertThat(tvShow.getName(),is(entity.getName()));
        assertThat(tvShow.getOverview(),is(entity.getOverview()));
        assertThat(tvShow.getPopularity(),is(entity.getPopularity()));
        assertThat(tvShow.getVoteAverage(),is(entity.getVoteAverage()));
    }
}
