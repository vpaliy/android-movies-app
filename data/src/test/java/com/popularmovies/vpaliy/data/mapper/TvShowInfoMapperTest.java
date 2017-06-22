package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.utils.MapperUtils;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MapperUtils.class)
public class TvShowInfoMapperTest {

    private Mapper<TVShowInfo,TvShowInfoEntity> mapper=new TvShowInfoMapper();

    @Before
    public void setUp(){
        PowerMockito.mockStatic(MapperUtils.class);
    }

    @Test
    public void mapsFakeToReal(){
        TvShowInfoEntity infoEntity= FakeDataProvider.provideTvShowInfoEntity();
        TVShowInfo info=mapper.map(infoEntity);

        assertThatAreEqual(info,infoEntity);

        PowerMockito.verifyStatic();
        MapperUtils.convertToString(any());
    }

    @Test
    public void mapsRealToFake(){
        TVShowInfo info=FakeDataProvider.provideTvShowInfo();
        TvShowInfoEntity entity=mapper.reverseMap(info);

        assertThatAreEqual(info,entity);

        PowerMockito.verifyStatic();
        MapperUtils.convertToNetworks(any());
    }

    @Test
    public void mapsRealListToFakeList(){
        List<TVShowInfo> realList=FakeDataProvider.provideTvShowInfoList();
        List<TvShowInfoEntity> fakeList=mapper.reverseMap(realList);

        assertThatAreEqual(realList,fakeList);

        PowerMockito.verifyStatic(times(fakeList.size()));
        MapperUtils.convertToNetworks(any());
    }

    @Test
    public void mapsFakeListToRealList(){
        List<TvShowInfoEntity> fakeList=FakeDataProvider.provideTvShowInfoEntityList();
        List<TVShowInfo> realList=mapper.map(fakeList);

        assertThatAreEqual(realList,fakeList);

        PowerMockito.verifyStatic(times(fakeList.size()));
        MapperUtils.convertToString(any());
    }

    private void assertThatAreEqual(List<TVShowInfo> first, List<TvShowInfoEntity> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(TVShowInfo info, TvShowInfoEntity entity){
        assertThat(info.getAverageRate(),is(entity.getVoteAverage()));
        assertThat(info.getFirstAirDate(),is(entity.getFirstAirDate()));
        assertThat(info.getLastAirDate(),is(entity.getLastAirDate()));
        assertThat(info.getName(),is(entity.getName()));
        assertThat(info.getNumberOfEpisodes(),is(entity.getNumberOfEpisodes()));
        assertThat(info.getNumberOfSeasons(),is(entity.getNumberOfSeasons()));
        assertThat(info.getOriginalLanguage(),is(entity.getOriginalLanguage()));
        assertThat(info.getOverview(),is(entity.getOverview()));
        assertThat(info.getPopularity(),is(entity.getPopularity()));
        assertThat(info.getStatus(),is(entity.getStatus()));
        assertThat(info.getTvShowId(),is(entity.getTvShowId()));
        assertThat(info.getVoteCount(),is(entity.getVoteCount()));
    }
}
