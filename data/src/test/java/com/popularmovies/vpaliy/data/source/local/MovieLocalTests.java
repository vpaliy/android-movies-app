package com.popularmovies.vpaliy.data.source.local;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DatabaseUtilsTest.class,
                     MovieDatabaseTest.class,
                     MovieLocalStorageTest.class,
                     MovieProviderTest.class})
public class MovieLocalTests {
}
