package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.domain.model.Trailer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
public class TrailerMapperTest {

    private Mapper<Trailer,TrailerEntity> mapper=new TrailerMapper();

    @Test
    public void mapsTrailerToTrailerEntity(){
        Trailer trailer= FakeDataProvider.provideTrailer();
        TrailerEntity entity=mapper.reverseMap(trailer);
        assertThatAreEqual(trailer,entity);
    }

    @Test
    public void mapsTrailerEntityToTrailer(){
        TrailerEntity entity=FakeDataProvider.provideTrailerEntity();
        Trailer trailer=mapper.map(entity);
        assertThatAreEqual(trailer,entity);
    }

    @Test
    public void mapsTrailerListToTrailerEntityList(){
        List<Trailer> trailers=FakeDataProvider.provideTrailerList();
        List<TrailerEntity> entities=mapper.reverseMap(trailers);
        assertThatAreEqual(trailers,entities);
    }

    @Test
    public void mapsTrailerEntityListToTrailerList(){
        List<TrailerEntity> entities=FakeDataProvider.provideTrailerEntityList();
        List<Trailer> trailerList=mapper.map(entities);
        assertThatAreEqual(trailerList,entities);
    }

    private void assertThatAreEqual(List<Trailer> trailers, List<TrailerEntity> entities){
        assertThat(trailers.size(),is(entities.size()));
        for(int index=0;index<trailers.size();index++){
            assertThatAreEqual(trailers.get(index),entities.get(index));
        }
    }

    private void assertThatAreEqual(Trailer trailer,TrailerEntity entity){
        assertThat(trailer.getTrailerUrl(),is(entity.getTrailerUrl()));
        assertThat(trailer.getMovieId(),is(entity.getMovieId()));
        assertThat(trailer.getTrailerTitle(),is(entity.getTrailerTitle()));
    }
}

