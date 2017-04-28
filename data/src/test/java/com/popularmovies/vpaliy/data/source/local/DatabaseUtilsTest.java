package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentValues;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DatabaseUtilsTest {

    @Test
    public void testConvertToValues(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        ContentValues contentValues=DatabaseUtils.convertToValues(movie);

        assertThat(contentValues.getAsInteger(MoviesContract.MovieEntry._ID), is(movie.getMovieId()));
        assertThat(contentValues.getAsString(MoviesContract.MovieEntry.COLUMN_TITLE), is(movie.getTitle()));
        assertThat(contentValues.getAsDouble(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE), is(movie.getVoteAverage()));
        assertThat(contentValues.getAsString(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH),is(movie.getBackdrop_path()));
        assertThat(contentValues.getAsInteger(MoviesContract.MovieEntry.COLUMN_IS_FAVORITE)==1, is(movie.isFavorite()));
        assertThat(contentValues.getAsString(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE), is(movie.getOriginalTitle()));
        assertThat(contentValues.getAsString(MoviesContract.MovieEntry.COLUMN_OVERVIEW), is(movie.getOverview()));
        assertThat(contentValues.getAsDouble(MoviesContract.MovieEntry.COLUMN_POPULARITY), is(movie.getPopularity()));
        assertThat(contentValues.getAsString(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE), is(movie.getReleaseDate()));
        assertThat(contentValues.getAsLong(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT), is(movie.getVoteCount()));
        assertThat(contentValues.getAsString(MoviesContract.MovieEntry.COLUMN_POSTER_PATH), is(movie.getPosterPath()));
    }
}
