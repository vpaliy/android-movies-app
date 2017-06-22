package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class TvShowEpisodeMapperTest {

    private TvShowEpisodeMapper mapper;

    @Before
    public void setUp(){
        mapper=new TvShowEpisodeMapper();
    }

    @Test
    public void mapsRealToFake(){
        TVShowEpisode episode= FakeDataProvider.provideTvShowEpisode();
        TvShowEpisodeEntity entity=mapper.reverseMap(episode);

        assertThatAreEqual(episode,entity);
    }

    @Test
    public void mapsFakeToReal(){
        TvShowEpisodeEntity episodeEntity=FakeDataProvider.provideTvEpisodeEntity();
        TVShowEpisode episode=mapper.map(episodeEntity);

        assertThatAreEqual(episode,episodeEntity);
    }

    @Test
    public void mapsRealListToFakeList(){
        List<TVShowEpisode> episodes=FakeDataProvider.provideTvShowEpisodeList();
        List<TvShowEpisodeEntity> entities=mapper.reverseMap(episodes);

        assertThatAreEqual(episodes,entities);
    }

    @Test
    public void mapsFakeListToRealList(){
        List<TvShowEpisodeEntity> entities=FakeDataProvider.provideTvShowEpisodeEntityList();
        List<TVShowEpisode> episodes=mapper.map(entities);

        assertThatAreEqual(episodes,entities);
    }

    private void assertThatAreEqual(List<TVShowEpisode> first, List<TvShowEpisodeEntity> second){
        assertThat(first.size(),is(second.size()));
        for(int index=0;index<first.size();index++){
            assertThatAreEqual(first.get(index),second.get(index));
        }
    }

    private void assertThatAreEqual(TVShowEpisode episode, TvShowEpisodeEntity entity){
        assertThat(episode.getVoteCount(),is(entity.getVoteCount()));
        assertThat(episode.getVoteAverage(),is(entity.getVoteAverage()));
        assertThat(episode.getEpisodeId(),is(entity.getId()));
        assertThat(episode.getEpisodeName(),is(entity.getName()));
        assertThat(episode.getEpisodeNumber(),is(entity.getEpisodeNumber()));
        assertThat(episode.getEpisodeOverview(),is(entity.getOverview()));
    }
}
