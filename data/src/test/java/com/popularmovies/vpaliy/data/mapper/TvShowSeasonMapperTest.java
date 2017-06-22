package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TvShowSeasonMapperTest {

    @Mock
    private Mapper<TVShowEpisode,TvShowEpisodeEntity> episodeMapper;

    private TvShowSeasonMapper mapper;

    @Before
    public void setUp(){
        mapper=new TvShowSeasonMapper(episodeMapper);
    }

    @Test
    public void mapsRealToFake(){
        TVShowSeason season= FakeDataProvider.provideTvShowSeason();
        TvShowSeasonEntity entity=mapper.reverseMap(season);

        assertThatAreEqual(season,entity);
        verify(episodeMapper).reverseMap(eq(season.getEpisodeList()));
    }

    @Test
    public void mapsFakeToReal(){
        TvShowSeasonEntity entity=FakeDataProvider.provideTvShowSeasonEntity();
        TVShowSeason season=mapper.map(entity);

        assertThatAreEqual(season,entity);
        verify(episodeMapper).map(eq(entity.getEpisodes()));
    }

    @Test
    public void mapsFakeListToRealList(){
        List<TvShowSeasonEntity> entities=FakeDataProvider.provideTvShowSeasonEntityList();
        List<TVShowSeason> seasons=mapper.map(entities);

        assertThatAreEqual(seasons,entities);
        verify(episodeMapper,times(seasons.size())).map(anyList());
    }

    @Test
    public void mapsRealListToFakeList(){
        List<TVShowSeason> seasons=FakeDataProvider.provideTvShowSeasonList();
        List<TvShowSeasonEntity> entities=mapper.reverseMap(seasons);

        assertThatAreEqual(seasons,entities);
        verify(episodeMapper,times(seasons.size())).reverseMap(anyList());
    }

    private void assertThatAreEqual(List<TVShowSeason> first, List<TvShowSeasonEntity> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(TVShowSeason season, TvShowSeasonEntity entity){
        assertThat(season.getAirDate(),is(entity.getAirDate()));
        assertThat(season.getPosterPath(),is(entity.getPosterPath()));
        assertThat(season.getSeasonId(),is(entity.getId()));
        assertThat(season.getSeasonName(),is(entity.getName()));
        assertThat(season.getSeasonNumber(),is(entity.getSeasonNumber()));;
    }
}
