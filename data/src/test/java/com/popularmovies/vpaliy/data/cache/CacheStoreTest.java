package com.popularmovies.vpaliy.data.cache;



import com.google.common.cache.Cache;
import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.domain.model.MediaCover;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CacheStoreTest {

    @Mock
    private Cache<String,MediaCover> mockCache;

    @Mock
    private MediaCover mockCover;

    @InjectMocks
    private CacheStore<String,MediaCover> cacheStore;

    @Before
    public void setUp(){
        given(mockCache.getIfPresent(FakeDataProvider.FAKE_MEDIA_ID)).willReturn(mockCover);
        given(mockCache.size()).willReturn(Long.valueOf(1));
        cacheStore.put(FakeDataProvider.FAKE_MEDIA_ID,mockCover);
    }

    @Test
    public void testGetStream(){
        cacheStore.getStream(FakeDataProvider.FAKE_MEDIA_ID)
                .subscribe(movie->assertThat(movie,is(mockCover)));

        verify(mockCache).getIfPresent(FakeDataProvider.FAKE_MEDIA_ID);
    }

    @Test
    public void testIsInCache(){
        assertThat(cacheStore.isInCache(FakeDataProvider.FAKE_MEDIA_ID),is(true));
        assertThat(cacheStore.isInCache(FakeDataProvider.FAKE_ACTOR_ID),is(false));

        verify(mockCache).getIfPresent(FakeDataProvider.FAKE_MEDIA_ID);
        verify(mockCache).getIfPresent(FakeDataProvider.FAKE_ACTOR_ID);
    }

    @Test
    public void testInvalidate(){
        cacheStore.invalidate(FakeDataProvider.FAKE_MEDIA_ID);
        verify(mockCache).invalidate(FakeDataProvider.FAKE_MEDIA_ID);
    }

    @Test
    public void testSize(){
        assertThat(cacheStore.size(),is(1L));
        verify(mockCache).size();
    }
}
