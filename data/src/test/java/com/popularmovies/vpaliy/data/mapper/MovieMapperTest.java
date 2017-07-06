package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.utils.MapperUtils;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MapperUtils.class)
public class MovieMapperTest  {

    @Mock
    private ImageQualityConfiguration qualityConfiguration;

    @InjectMocks
    private MovieMapper mapper;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(MapperUtils.class);
        when(qualityConfiguration.convertCover(anyString())).thenReturn(FakeDataProvider.FAKE_POSTER_PATH);
        when(qualityConfiguration.convertBackdrop(anyString())).thenReturn(FakeDataProvider.FAKE_BACKDROP_PATH);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FakeDataProvider.FAKE_POSTER_PATH);
    }

    @Test
    public void mapsMediaCoverToMovie(){
        MediaCover mediaCover=FakeDataProvider.provideMediaCover(false);
        Movie movie=mapper.reverseMap(mediaCover);
        assertThatAreEqual(mediaCover,movie);
        verify(qualityConfiguration,times(2)).extractPath(eq(mediaCover.getPosterPath()));

        PowerMockito.verifyStatic();
        MapperUtils.convertToGenres(anyList());
        MapperUtils.convertToRuntime(anyString());
        MapperUtils.convertToBackdrops(anyList(),eq(qualityConfiguration));
    }

    @Test
    public void mapsMovieToMediaCover(){
        Movie movie=FakeDataProvider.provideMovieEntity();
        MediaCover cover=mapper.map(movie);
        assertThatAreEqual(cover,movie);
        verify(qualityConfiguration).convertCover(eq(movie.getPosterPath()));

        PowerMockito.verifyStatic();
        MapperUtils.convert(anyList());
        MapperUtils.convertToDuration(anyInt());
        MapperUtils.convertToYear(anyString());
        MapperUtils.convert(anyList(),eq(qualityConfiguration));
    }

    @Test
    public void mapsMediaCoverListToMovieList(){
        List<MediaCover> coverList=FakeDataProvider.provideMediaCoverList(false);
        List<Movie> movieList=mapper.reverseMap(coverList);
        assertThatAreEqual(coverList,movieList);

        verify(qualityConfiguration,times(2*movieList.size())).extractPath(anyString());

        PowerMockito.verifyStatic(times(movieList.size()));
        MapperUtils.convertToGenres(anyList());
        MapperUtils.convertToRuntime(anyString());
        MapperUtils.convertToBackdrops(anyList(),eq(qualityConfiguration));
    }

    @Test
    public void mapsMovieListToMediaCoverList(){
        List<Movie> movieList=FakeDataProvider.provideMovieList();
        List<MediaCover> coverList=mapper.map(movieList);
        assertThatAreEqual(coverList,movieList);

        verify(qualityConfiguration,times(movieList.size())).convertCover(anyString());

        PowerMockito.verifyStatic(times(movieList.size()));
        MapperUtils.convert(anyList());
        MapperUtils.convertToDuration(anyInt());
        MapperUtils.convertToYear(anyString());
        MapperUtils.convert(anyList(),eq(qualityConfiguration));
    }

    private void assertThatAreEqual(List<MediaCover> first, List<Movie> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(MediaCover cover, Movie movie){
        assertThat(cover.getMediaId(),is(movie.getMovieId()));
        assertThat(cover.getAverageVote(),is(movie.getVoteAverage()));
        assertThat(cover.getMovieTitle(),is(movie.getTitle()));
        assertThat(cover.getPosterPath(),is(movie.getPosterPath()));
        assertThat(cover.getReleaseDate(),is(movie.getReleaseDate()));
        assertThat(cover.isFavorite(),is(movie.isFavorite()));
        assertThat(cover.isWatched(),is(movie.isWatched()));
        assertThat(cover.isMustWatch(),is(movie.isMustWatch()));
    }
}
