package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.utils.MapperUtils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ActorMapperTest.class,
            MovieDetailsMapperTest.class,
            MovieInfoMapperTest.class,
            ReviewMapperTest.class,
            TrailerMapperTest.class,
            TvShowMapperTest.class,
            TvShowInfoMapperTest.class,
            TvShowSeasonMapperTest.class,
            TvShowDetailsMapperTest.class,
            TvShowEpisodeMapperTest.class,
            MapperUtilsTest.class,
            MovieMapperTest.class})
public class MapperTests {
}
