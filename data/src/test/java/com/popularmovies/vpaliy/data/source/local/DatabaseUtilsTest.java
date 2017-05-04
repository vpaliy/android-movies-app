package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;

import com.google.gson.reflect.TypeToken;
import com.popularmovies.vpaliy.data.BuildConfig;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMNS;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_BUDGET;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_GENRES;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_HOME_PAGE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_IS_FAVORITE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_MOVIE_BACKDROPS;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_OVERVIEW;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_POPULARITY;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_POSTER_PATH;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_RELEASE_DATE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_REVENUE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_RUNTIME;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_TITLE;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MovieEntry.COLUMN_VOTE_COUNT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class DatabaseUtilsTest {

    private  ImageQualityConfiguration qualityConfiguration;

    @Before
    public void setUp(){
        qualityConfiguration=new ImageQualityConfiguration(RuntimeEnvironment.application);
    }

    @Test
    public void testConvertToValues(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        ContentValues contentValues=DatabaseUtils.convertToValues(movie);


        assertThat(contentValues.getAsInteger(_ID), is(movie.getMovieId()));
        assertThat(contentValues.getAsString(COLUMN_ORIGINAL_TITLE),is(movie.getOriginalTitle()));
        assertThat(contentValues.getAsString(COLUMN_OVERVIEW), is(movie.getOverview()));
        assertThat(contentValues.getAsString(COLUMN_RELEASE_DATE), is(movie.getReleaseDate()));
        assertThat(contentValues.getAsString(COLUMN_POSTER_PATH), is(movie.getPosterPath()));
        assertThat(contentValues.getAsDouble(COLUMN_POPULARITY), is(movie.getPopularity()));
        assertThat(contentValues.getAsLong(COLUMN_BUDGET), is(movie.getBudget()));
        assertThat(contentValues.getAsInteger(COLUMN_RUNTIME), is(movie.getRuntime()));
        assertThat(contentValues.getAsLong(COLUMN_REVENUE), is(movie.getRevenue()));
        assertThat(contentValues.getAsString(COLUMN_HOME_PAGE), is(movie.getHomepage()));
        assertThat(contentValues.getAsString(COLUMN_TITLE), is(movie.getTitle()));
        assertThat(contentValues.getAsInteger(COLUMN_IS_FAVORITE)==1, is(movie.isFavorite()));
        assertThat(contentValues.getAsDouble(COLUMN_AVERAGE_VOTE), is(movie.getVoteAverage()));
        assertThat(contentValues.getAsLong(COLUMN_VOTE_COUNT), is(movie.getVoteCount()));
        assertThat(contentValues.getAsString(COLUMN_BACKDROP_PATH),is(movie.getBackdrop_path()));

        String jsonString=contentValues.getAsString(COLUMN_MOVIE_BACKDROPS);
        Type type=new TypeToken<ArrayList<BackdropImage>>(){}.getType();

        List<BackdropImage> backdrops=DatabaseUtils.convertFromJsonString(jsonString,type);

        assertThat(backdrops.size(),is(movie.getBackdropImages().size()));
        Assert.assertArrayEquals(BackdropImage.convert(backdrops,qualityConfiguration).toArray(new String[backdrops.size()]),
                BackdropImage.convert(movie.getBackdropImages(),qualityConfiguration).toArray(new String[backdrops.size()]));

        jsonString=contentValues.getAsString(COLUMN_GENRES);
        type=new TypeToken<ArrayList<Genre>>(){}.getType();
        List<Genre> genreList=DatabaseUtils.convertFromJsonString(jsonString,type);

        assertThat(genreList.size(),is(movie.getGenres().size()));
        Assert.assertArrayEquals(Genre.convert(genreList).toArray(new String[genreList.size()]),
                Genre.convert(movie.getGenres()).toArray(new String[genreList.size()]));

    }


    @Test
    public void testToJsonAndFrom(){
        Movie movie= DataSourceTestUtils.provideFakeMovie();
        Type type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();

        String jsonString=DatabaseUtils.convertToJsonString(movie.getBackdropImages(),type);
        type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        List<BackdropImage> backdropList = DatabaseUtils.convertFromJsonString(jsonString,type);

        assertThat(backdropList.size(),is(movie.getBackdropImages().size()));
        Assert.assertArrayEquals(BackdropImage.convert(backdropList,qualityConfiguration).toArray(new String[backdropList.size()]),
                BackdropImage.convert(movie.getBackdropImages(),qualityConfiguration).toArray(new String[backdropList.size()]));

        jsonString=DatabaseUtils.convertToJsonString(movie.getGenres(),type);
        type=new TypeToken<ArrayList<Genre>>(){}.getType();
        List<Genre> genreList=DatabaseUtils.convertFromJsonString(jsonString,type);

        assertThat(genreList.size(),is(movie.getGenres().size()));
        Assert.assertArrayEquals(Genre.convert(genreList).toArray(new String[genreList.size()]),
                Genre.convert(movie.getGenres()).toArray(new String[genreList.size()]));
    }


    @Test
    public void testConvertToMovies(){
        Movie inputMovie=DataSourceTestUtils.provideFakeMovie();
        Cursor cursor=movieToMockCursor(inputMovie);

        Movie outputMovie=DatabaseUtils.convertToMovie(cursor);

        assertThat(outputMovie.getMovieId(),is(inputMovie.getMovieId()));
        assertThat(outputMovie.getOriginalTitle(),is(inputMovie.getOriginalTitle()));
        assertThat(outputMovie.getBackdrop_path(),is(inputMovie.getBackdrop_path()));
        assertThat(outputMovie.getBackdropImages().size(),is(inputMovie.getBackdropImages().size()));
        assertThat(outputMovie.getGenres().size(),is(inputMovie.getGenres().size()));
        assertThat(outputMovie.getBudget(),is(inputMovie.getBudget()));
        assertThat(outputMovie.getRevenue(),is(inputMovie.getRevenue()));
        assertThat(outputMovie.getHomepage(),is(inputMovie.getHomepage()));
        assertThat(outputMovie.getOverview(),is(inputMovie.getOverview()));
        assertThat(outputMovie.getPopularity(),is(inputMovie.getPopularity()));
        assertThat(outputMovie.getReleaseDate(),is(inputMovie.getReleaseDate()));
        assertThat(outputMovie.getPosterPath(),is(inputMovie.getPosterPath()));
        assertThat(outputMovie.getVoteAverage(),is(inputMovie.getVoteAverage()));
        assertThat(outputMovie.getRuntime(),is(inputMovie.getRuntime()));
        assertThat(outputMovie.isFavorite(),is(inputMovie.isFavorite()));
        assertThat(outputMovie.getTitle(),is(inputMovie.getTitle()));
        assertThat(outputMovie.getVoteAverage(),is(inputMovie.getVoteAverage()));
        assertThat(outputMovie.getVoteCount(),is(inputMovie.getVoteCount()));

    }


    private Cursor movieToMockCursor(Movie movie){

        Cursor cursor= Mockito.mock(Cursor.class);

        for(int index = 0; index< COLUMNS.length; index++) {
            given(cursor.getColumnIndex(COLUMNS[index])).willReturn(index);
        }

        when(cursor.getString(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgument(0);
            String args =COLUMNS[columnIndex];
            Type type=null;
            switch (args){
                case COLUMN_ORIGINAL_TITLE:
                    return movie.getOriginalTitle();
                case COLUMN_TITLE:
                    return movie.getTitle();
                case COLUMN_OVERVIEW:
                    return movie.getOverview();
                case COLUMN_GENRES:
                    type = new TypeToken<ArrayList<Genre>>() {}.getType();
                    return DatabaseUtils.convertToJsonString(movie.getGenres(),type);
                case COLUMN_MOVIE_BACKDROPS:
                    type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
                    return DatabaseUtils.convertToJsonString(movie.getBackdropImages(),type);
                case COLUMN_POSTER_PATH:
                    return DataSourceTestUtils.FAKE_POSTER_PATH;
                case COLUMN_HOME_PAGE:
                    return movie.getHomepage();
                case COLUMN_RELEASE_DATE:
                    return movie.getReleaseDate();
                case COLUMN_BACKDROP_PATH:
                    return DataSourceTestUtils.FAKE_BACKDROP_PATH;
            }
            throw new UnsupportedOperationException();
        });

        when(cursor.getInt(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgument(0);
            String args =COLUMNS[columnIndex];
            switch (args){
                case _ID:
                    return movie.getMovieId();
                case COLUMN_IS_FAVORITE:
                    return movie.isFavorite()?1:0;
                case COLUMN_RUNTIME:
                    return movie.getRuntime();
            }
            throw new UnsupportedOperationException();
        });

        when(cursor.getLong(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgument(0);
            String args =COLUMNS[columnIndex];
            switch (args){
                case COLUMN_REVENUE:
                    return movie.getRevenue();
                case COLUMN_BUDGET:
                    return movie.getBudget();
                case COLUMN_VOTE_COUNT:
                    return movie.getVoteCount();
            }
            throw new UnsupportedOperationException();
        });

        when(cursor.getDouble(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgument(0);
            String args =COLUMNS[columnIndex];
            switch (args){
                case COLUMN_POPULARITY:
                    return movie.getPopularity();
                case COLUMN_AVERAGE_VOTE:
                    return movie.getVoteAverage();

            }
            throw new UnsupportedOperationException();
        });
        return cursor;
    }
}
