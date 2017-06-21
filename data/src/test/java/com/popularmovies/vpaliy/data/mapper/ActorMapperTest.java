package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;
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
        assertThat(actorCover.getMovieId(),is(actorEntity.getMovieId()));
        assertThat(actorCover.getActorAvatar(),is(actorEntity.getActorAvatar()));
        assertThat(actorCover.getName(),is(actorEntity.getName()));
        assertThat(actorCover.getRole(),is(actorEntity.getRole()));
    }

    @Test
    public void mapsActorCoverToActorEntity(){
        ActorCover actorCover=FakeDataProvider.provideActorCover();
        ActorEntity actorEntity=mapper.reverseMap(actorCover);
        assertThat(actorCover.getActorAvatar(),is(actorEntity.getActorAvatar()));
        assertThat(actorCover.getName(),is(actorEntity.getName()));
        assertThat(actorCover.getRole(),is(actorEntity.getRole()));
    }
}
