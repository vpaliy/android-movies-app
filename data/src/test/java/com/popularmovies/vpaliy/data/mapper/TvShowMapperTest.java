package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MapperUtils.class)
public class TvShowMapperTest {

    @Mock
    private ImageQualityConfiguration qualityConfiguration;

    @InjectMocks
    private TvShowMapper mapper;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(MapperUtils.class);
        when(qualityConfiguration.convertCover(anyString())).thenReturn(FakeDataProvider.FAKE_POSTER_PATH);
        when(qualityConfiguration.convertBackdrop(anyString())).thenReturn(FakeDataProvider.FAKE_BACKDROP_PATH);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FakeDataProvider.FAKE_POSTER_PATH);
    }

    @Test
    public void mapsMediaCoverToMovie(){
        MediaCover mediaCover=FakeDataProvider.provideMediaCover(true);
        TvShow tvShow=mapper.reverseMap(mediaCover);
        assertThatAreEqual(mediaCover,tvShow);
        verify(qualityConfiguration).extractPath(eq(mediaCover.getPosterPath()));

        PowerMockito.verifyStatic();
        MapperUtils.convertToGenres(anyList());
        MapperUtils.convertToBackdrops(anyList(),eq(qualityConfiguration));
    }

    @Test
    public void mapsMovieToMediaCover(){
        TvShow tvShow=FakeDataProvider.provideTvShowEntity();
        MediaCover cover=mapper.map(tvShow);
        assertThatAreEqual(cover,tvShow);
        verify(qualityConfiguration).convertCover(eq(tvShow.getPosterPath()));

        PowerMockito.verifyStatic();
        MapperUtils.convert(anyList());
        MapperUtils.convertToYear(anyString());
        MapperUtils.convert(anyList(),eq(qualityConfiguration));
    }

    @Test
    public void mapsMediaCoverListToMovieList(){
        List<MediaCover> coverList=FakeDataProvider.provideMediaCoverList(true);
        List<TvShow> movieList=mapper.reverseMap(coverList);
        assertThatAreEqual(coverList,movieList);

        verify(qualityConfiguration,times(movieList.size())).extractPath(anyString());

        PowerMockito.verifyStatic(times(movieList.size()));
        MapperUtils.convertToGenres(anyList());
        MapperUtils.convertToBackdrops(anyList(),eq(qualityConfiguration));
    }

    @Test
    public void mapsMovieListToMediaCoverList(){
        List<TvShow> list=FakeDataProvider.provideTvShowList();
        List<MediaCover> coverList=mapper.map(list);
        assertThatAreEqual(coverList,list);

        verify(qualityConfiguration,times(list.size())).convertCover(anyString());

        PowerMockito.verifyStatic(times(list.size()));
        MapperUtils.convert(anyList());
        MapperUtils.convertToDuration(anyInt());
        MapperUtils.convertToYear(anyString());
        MapperUtils.convert(anyList(),eq(qualityConfiguration));
    }

    private void assertThatAreEqual(List<MediaCover> first, List<TvShow> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(MediaCover cover, TvShow tvShow){
        assertThat(cover.getMediaId(),is(tvShow.getId()));
        assertThat(cover.isTvShow(),is(true));
        assertThat(cover.getAverageVote(),is(tvShow.getVoteAverage()));
        assertThat(cover.getMovieTitle(),is(tvShow.getName()));
        assertThat(cover.getPosterPath(),is(tvShow.getPosterPath()));
        assertThat(cover.getReleaseDate(),is(tvShow.getFirstAirDate()));
        assertThat(cover.isFavorite(),is(tvShow.isFavorite()));
        assertThat(cover.isWatched(),is(tvShow.isMustWatch()));
        assertThat(cover.isMustWatch(),is(tvShow.isWatched()));
    }
}
