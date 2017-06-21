package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ActorMapperTest{

    private Mapper<ActorCover,ActorEntity> mapper;

    @Mock
    private ImageQualityConfiguration qualityConfiguration;

    @Before
    public void setUp(){
        when(qualityConfiguration.convertCover(anyString())).thenReturn(FakeDataProvider.FAKE_AVATAR);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FakeDataProvider.FAKE_AVATAR);
        mapper=new ActorMapper(qualityConfiguration);
    }

    @Test
    public void mapsActorEntityToActorCover(){
        ActorEntity actorEntity= FakeDataProvider.provideActorEntity();
        ActorCover actorCover=mapper.map(actorEntity);
        assertThatActorsAreEqual(actorCover,actorEntity);
    }

    @Test
    public void mapsActorCoverToActorEntity(){
        ActorCover actorCover=FakeDataProvider.provideActorCover();
        ActorEntity actorEntity=mapper.reverseMap(actorCover);
        assertThatActorsAreEqual(actorCover,actorEntity);
    }

    @Test
    public void mapsActorCoverListToActorEntityList(){
        List<ActorCover> coverList=FakeDataProvider.provideActorCoverList();
        List<ActorEntity> entityList=mapper.reverseMap(coverList);
        assertThatListsAreEqual(coverList,entityList);
    }

    @Test
    public void mapsActorEntityListToActorCoverList(){
        List<ActorEntity> entityList=FakeDataProvider.provideActorEntityList();
        List<ActorCover> coverList=mapper.map(entityList);
        assertThatListsAreEqual(coverList,entityList);
    }

    private void assertThatListsAreEqual(List<ActorCover> covers, List<ActorEntity> entities){
        assertThat(covers.size(),is(entities.size()));
        for(int index=0;index<covers.size();index++){
            assertThatActorsAreEqual(covers.get(index),entities.get(index));
        }
    }

    private void assertThatActorsAreEqual(ActorCover actorCover, ActorEntity actorEntity){
        assertThat(actorCover.getMovieId(),is(actorEntity.getMovieId()));
        assertThat(actorCover.getActorAvatar(),is(actorEntity.getActorAvatar()));
        assertThat(actorCover.getName(),is(actorEntity.getName()));
        assertThat(actorCover.getRole(),is(actorEntity.getRole()));
    }
}
