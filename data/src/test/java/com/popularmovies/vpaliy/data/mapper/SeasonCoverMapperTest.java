package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.domain.model.SeasonCover;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SeasonCoverMapperTest {


    private SeasonCoverMapper mapper;

    @Mock
    private ImageQualityConfiguration qualityConfiguration;

    @Before
    public void setUp(){
        mapper=new SeasonCoverMapper(qualityConfiguration);
    }

    @Test
    public void mapsRealToFake(){
        SeasonCover season= FakeDataProvider.provideTvShowSeason();
        SeasonEntity entity=mapper.reverseMap(season);

        assertThatAreEqual(season,entity);
    }

    @Test
    public void mapsFakeToReal(){
        SeasonEntity entity=FakeDataProvider.provideTvShowSeasonEntity();
        SeasonCover season=mapper.map(entity);

        assertThatAreEqual(season,entity);
    }

    @Test
    public void mapsFakeListToRealList(){
        List<SeasonEntity> entities=FakeDataProvider.provideTvShowSeasonEntityList();
        List<SeasonCover> seasons=mapper.map(entities);

        assertThatAreEqual(seasons,entities);
    }

    @Test
    public void mapsRealListToFakeList(){
        List<SeasonCover> seasons=FakeDataProvider.provideTvShowSeasonList();
        List<SeasonEntity> entities=mapper.reverseMap(seasons);

        assertThatAreEqual(seasons,entities);
    }

    private void assertThatAreEqual(List<SeasonCover> first, List<SeasonEntity> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(SeasonCover season, SeasonEntity entity){
        assertThat(season.getAirDate(),is(entity.getAirDate()));
        assertThat(season.getPosterPath(),is(entity.getPosterPath()));
        assertThat(season.getSeasonId(),is(entity.getId()));
        assertThat(season.getSeasonName(),is(entity.getName()));
        assertThat(season.getSeasonNumber(),is(entity.getSeasonNumber()));;
    }
}
