package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ActorMapperTest extends MapperTest {


    private Mapper<ActorCover,ActorEntity> mapper;


    @Before
    public void setUp(){
        when(qualityConfiguration.convertCover(anyString())).thenReturn(FAKE_PATH);
        when(qualityConfiguration.extractPath(anyString())).thenReturn(FAKE_PATH);
        mapper=new ActorMapper(qualityConfiguration);
    }


    @Test
    public void testMapToActor(){
        ActorEntity actorEntity=new ActorEntity();
        actorEntity.setActorId(123);
        actorEntity.setRole("Role");
        actorEntity.setActorAvatar("path");
        actorEntity.setName("name");

        ActorCover actorCover=mapper.map(actorEntity);
        assertThat(actorCover.getMovieId(),is(actorEntity.getMovieId()));
        assertThat(actorCover.getActorAvatar(),is(actorEntity.getActorAvatar()));
        assertThat(actorCover.getName(),is(actorEntity.getName()));
        assertThat(actorCover.getRole(),is(actorEntity.getRole()));

    }

    @Test
    public void testReverseMapping(){
        ActorCover actorCover=new ActorCover(123,124);
        actorCover.setName("name");
        actorCover.setActorAvatar("path");
        actorCover.setRole("role");

        ActorEntity actorEntity=mapper.reverseMap(actorCover);

        assertThat(actorCover.getActorAvatar(),is(actorEntity.getActorAvatar()));
        assertThat(actorCover.getName(),is(actorEntity.getName()));
        assertThat(actorCover.getRole(),is(actorEntity.getRole()));
    }


}
