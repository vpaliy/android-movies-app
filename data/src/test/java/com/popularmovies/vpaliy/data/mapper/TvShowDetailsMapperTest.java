package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TvShowDetailsMapperTest {

    @Mock
    private Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper;

    @Mock
    private Mapper<MediaCover,TvShow> coverMapper;

    @Mock
    private Mapper<ActorCover,ActorEntity> actorMapper;

    @Mock
    private Mapper<TVShowInfo,TvShowInfoEntity> infoMapper;

    private TvShowDetailsMapper mapper;

    @Before
    public void setUp(){
        mapper=new TvShowDetailsMapper(seasonMapper,actorMapper,infoMapper,coverMapper);
    }

    @Test
    public void mapsFakeToReal(){
        TvShowDetailEntity entity= FakeDataProvider.provideTvShowDetailsEntity();
        mapper.map(entity);

        verify(coverMapper).map(eq(entity.getTvShowCover()));
        verify(actorMapper).map(eq(entity.getCast()));
        verify(infoMapper).map(eq(entity.getInfoEntity()));
        verify(seasonMapper).map(eq(entity.getSeasons()));
    }

    @Test
    public void mapsRealToFake(){
        TVShowDetails details=FakeDataProvider.provideTvShowDetails();
        mapper.reverseMap(details);

        verify(coverMapper).reverseMap(eq(details.getTvShowCover()));
        verify(actorMapper).reverseMap(eq(details.getCast()));
        verify(infoMapper).reverseMap(eq(details.getTvShowInfo()));
        verify(seasonMapper).reverseMap(eq(details.getSeasons()));
    }

    @Test
    public void mapsRealListToFakeList(){
        List<TVShowDetails> detailsList=FakeDataProvider.provideTvShowDetailsList();
        mapper.reverseMap(detailsList);

        verify(coverMapper,times(detailsList.size())).reverseMap(any(MediaCover.class));
        verify(actorMapper,times(detailsList.size())).reverseMap(anyList());
        verify(infoMapper,times(detailsList.size())).reverseMap(any(TVShowInfo.class));
        verify(seasonMapper,times(detailsList.size())).reverseMap(anyList());
    }

    @Test
    public void mapsFakeListToRealList(){
        List<TvShowDetailEntity> entities=FakeDataProvider.provideTvShowDetailsEntityList();
        mapper.map(entities);

        verify(coverMapper,times(entities.size())).map(any(TvShow.class));
        verify(actorMapper,times(entities.size())).map(anyList());
        verify(infoMapper,times(entities.size())).map(any(TvShowInfoEntity.class));
        verify(seasonMapper,times(entities.size())).map(anyList());
    }
}
