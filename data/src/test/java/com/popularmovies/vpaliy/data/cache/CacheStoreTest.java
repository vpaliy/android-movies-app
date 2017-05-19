package com.popularmovies.vpaliy.data.cache;



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

   /* private static final int FAKE_KEY=123;

    @Mock
    private Cache<Integer,MovieCover> mockCache;

    @Mock
    private MovieCover mockCover;

    @InjectMocks
    private CacheStore<Integer,MovieCover> cacheStore;

    @Before
    public void setUp(){
        given(mockCache.getIfPresent(FAKE_KEY)).willReturn(mockCover);
        given(mockCache.size()).willReturn(Long.valueOf(1));
        cacheStore.put(FAKE_KEY,mockCover);
    }

    @Test
    public void testGetStream(){
        cacheStore.getStream(FAKE_KEY)
                .subscribe(movie->assertThat(movie,is(mockCover)));

        verify(mockCache).getIfPresent(FAKE_KEY);
    }

    @Test
    public void testIsInCache(){
        assertThat(cacheStore.isInCache(FAKE_KEY),is(true));
        assertThat(cacheStore.isInCache(-1),is(false));

        verify(mockCache).getIfPresent(FAKE_KEY);
        verify(mockCache).getIfPresent(-1);
    }

    @Test
    public void testInvalidate(){
        cacheStore.invalidate(FAKE_KEY);
        verify(mockCache).invalidate(FAKE_KEY);
    }

    @Test
    public void testSize(){
        assertThat(cacheStore.size(),is(1L));
        verify(mockCache).size();
    }   */
}
