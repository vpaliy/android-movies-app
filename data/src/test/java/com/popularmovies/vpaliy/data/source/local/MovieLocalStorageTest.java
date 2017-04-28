package com.popularmovies.vpaliy.data.source.local;

import android.content.Context;

import com.popularmovies.vpaliy.domain.ISortConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class MovieLocalStorageTest {

    @Mock
    private Context context;

    @Mock
    private ISortConfiguration configuration;


    @InjectMocks
    private MovieLocalSource localSource;


    @Before
    public void setUp(){

    }

    @Test
    public void testGetMovieCovers(){

    }
}
