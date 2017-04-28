package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.util.Log;

import com.popularmovies.vpaliy.data.BuildConfig;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.DataSourceTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_RELEASE_DATE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_POSTER_PATH;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_BACKDROP_PATH;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_BACKDROPS;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_BUDGET;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_GENRES;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_HOMEPAGE;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_IS_FAVORITE;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_ORIGINAL_LANGUAGE;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_OVERVIEW;
import static com.popularmovies.vpaliy.data.source.DataSourceTestUtils.FAKE_TITLE;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP,
        manifest = "/src/main/AndroidManifest.xml")
public class MovieDatabaseTest {

    private MovieSQLHelper sqlHelper;

    private static final String MOVIE_SELECTION_BY_ID=
            MoviesContract.MovieEntry.TABLE_NAME+"."+ MoviesContract.MovieEntry._ID+"=?";

    @Before
    public void setUp(){
        sqlHelper=new MovieSQLHelper(RuntimeEnvironment.application);
        clearDatabase();
        createDatabase();


    }

    @After
    public void  tearDown() {
        clearDatabase();
    }


    @Test
    public void testMovieQuery(){
        SQLiteDatabase database=sqlHelper.getWritableDatabase();

        final Movie movie=DataSourceTestUtils.provideFakeMovie();
        database.insert(MoviesContract.MovieEntry.TABLE_NAME,null,
                DataSourceTestUtils.provideFakeValues(movie));

        //query all
        Cursor cursor=database.query(MoviesContract.MovieEntry.TABLE_NAME,
                null,null,null,null,null,null);
        assertThat(cursor.moveToFirst(),is(true));
        assertThatMovieIsEqualToCursor(cursor,movie,1,true);

    }

    @Test
    public void testFavoriteQuery(){
        final SQLiteDatabase database=sqlHelper.getWritableDatabase();
        final Movie firstMovie=DataSourceTestUtils.provideFakeMovie();
        firstMovie.setFavorite(true);
        final Movie secondMovie=DataSourceTestUtils.provideFakeMovie();
        secondMovie.setFavorite(true);
        secondMovie.setMovieId(1);
        final Movie thirdMovie=DataSourceTestUtils.provideFakeMovie();
        thirdMovie.setMovieId(0);
        thirdMovie.setFavorite(true);
        final Movie fourthMovie=DataSourceTestUtils.provideFakeMovie();
        fourthMovie.setMovieId(2);
        fourthMovie.setFavorite(false);

        final Movie[] movies={firstMovie,secondMovie,thirdMovie,fourthMovie};
        for(Movie movie:movies) {
            database.insert(MoviesContract.MovieEntry.TABLE_NAME,null,
                    DataSourceTestUtils.provideFakeValues(movie));

        }
        //test favorite query
        String selection= MoviesContract.MovieEntry.COLUMN_IS_FAVORITE+" LIKE ?";
        String[] selectionArgs={Integer.toString(1)};
        String order=MoviesContract.MovieEntry._ID+" ASC";
        Cursor cursor=database.query(MoviesContract.MovieEntry.TABLE_NAME,
                null,selection,selectionArgs,null,null,order);
        assertThat(cursor.moveToFirst(),is(true));

        //Cursor reads rows in the ascending order by its id
        assertThatMovieIsEqualToCursor(cursor,thirdMovie,3,false);
        assertThat(cursor.moveToNext(),is(true));
        assertThatMovieIsEqualToCursor(cursor,secondMovie,3,false);
        assertThat(cursor.moveToNext(),is(true));
        assertThatMovieIsEqualToCursor(cursor,firstMovie,3,true);
    }


    @Test
    public void testInsertAndReplace(){
        final SQLiteDatabase database=sqlHelper.getWritableDatabase();
        final Movie movie= DataSourceTestUtils.provideFakeMovie();
        database.insert(MoviesContract.MovieEntry.TABLE_NAME,null,DataSourceTestUtils.provideFakeValues(movie));
        Cursor cursor=database.query(MoviesContract.MovieEntry.TABLE_NAME,null,null,null,null,null,null);
        assertThat(cursor.moveToFirst(),is(true));
        assertThatMovieIsEqualToCursor(cursor,movie,1,true);


        //change the data in the same row
        movie.setPosterPath(null);
        movie.setFavorite(false);
        movie.setVoteCount(0);
        movie.setVoteAverage(0);
        movie.setBackdropPath(null);
        movie.setHomepage(null);
        movie.setReleaseDate(null);
        movie.setTitle(null);
        movie.setOverview(null);

        database.insertWithOnConflict(MoviesContract.MovieEntry.TABLE_NAME,null,
                DataSourceTestUtils.provideFakeValues(movie),SQLiteDatabase.CONFLICT_REPLACE);

        cursor=database.query(MoviesContract.MovieEntry.TABLE_NAME,null,null,null,null,null,null);
        assertThat(cursor.moveToFirst(),is(true));
        assertThatMovieIsEqualToCursor(cursor,movie,1,true);


    }


    private void assertThatMovieIsEqualToCursor(Cursor cursor, Movie movie, int count, boolean close){
        assertThat(cursor.getCount(), is(count));
        assertThat(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry._ID)), is(movie.getMovieId()));
        assertThat(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE)), is(movie.getTitle()));
        assertThat(cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE)), is(movie.getVoteAverage()));
        assertThat(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH)),is(movie.getBackdrop_path()));
        assertThat(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_IS_FAVORITE))==1, is(movie.isFavorite()));
        assertThat(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE)), is(movie.getOriginalTitle()));
        assertThat(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW)), is(movie.getOverview()));
        assertThat(cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY)), is(movie.getPopularity()));
        assertThat(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)), is(movie.getReleaseDate()));
        assertThat(cursor.getLong(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT)), is(movie.getVoteCount()));
        assertThat(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_PATH)), is(movie.getPosterPath()));
        if(close) {
            assertThat(cursor.isClosed(), is(false));
            cursor.close();
            assertThat(cursor.isClosed(), is(true));
        }
    }


    @Test
    public void testMovieUpdate(){
        final SQLiteDatabase database=sqlHelper.getWritableDatabase();
        final Movie movie= DataSourceTestUtils.provideFakeMovie();
        database.insert(MoviesContract.MovieEntry.TABLE_NAME,null,DataSourceTestUtils.provideFakeValues(movie));
        movie.setTitle(FAKE_TITLE+FAKE_TITLE);
        movie.setReleaseDate(FAKE_RELEASE_DATE+FAKE_RELEASE_DATE);
        movie.setOverview(FAKE_OVERVIEW+FAKE_OVERVIEW);
        movie.setHomepage(FAKE_HOMEPAGE+FAKE_HOMEPAGE);
        movie.setBackdropPath(FAKE_BACKDROP_PATH+FAKE_BACKDROP_PATH);
        movie.setFavorite(!FAKE_IS_FAVORITE);

        final int rowsUpdated=database.update(MoviesContract.MovieEntry.TABLE_NAME,DataSourceTestUtils.provideFakeValues(movie),null,null);
        assertThat(rowsUpdated,is(1));
        String[] selectionArgs = new String[]{Long.toString(movie.getMovieId())};
        Cursor cursor=database.query(MoviesContract.MovieEntry.TABLE_NAME,
                null,MOVIE_SELECTION_BY_ID,selectionArgs,null,null,null);
        assertThat(cursor.moveToFirst(),is(true));
        assertThatMovieIsEqualToCursor(cursor,movie,1,true);

    }


    @Test
    public void testMovieDelete(){
        final SQLiteDatabase database=sqlHelper.getWritableDatabase();
        final Movie movie= DataSourceTestUtils.provideFakeMovie();
        database.insert(MoviesContract.MovieEntry.TABLE_NAME,null,DataSourceTestUtils.provideFakeValues(movie));

        String[] selectionArgs = new String[]{Long.toString(movie.getMovieId())};
        final int rowsDeleted=database.delete(MoviesContract.MovieEntry.TABLE_NAME,MOVIE_SELECTION_BY_ID,selectionArgs);
        assertThat(rowsDeleted,is(1));

        Cursor cursor=database.query(MoviesContract.MovieEntry.TABLE_NAME,
                null,MOVIE_SELECTION_BY_ID,selectionArgs,null,null,null);
        assertThat(cursor.getCount(),is(0));
        if(!cursor.isClosed()) cursor.close();

    }


    private Cursor fetchFromTable(String tableName, String[] projection, String selection,
                                  String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        sqLiteQueryBuilder.setTables(
                tableName + " INNER JOIN " + MoviesContract.MovieEntry.TABLE_NAME +
                        " ON " + tableName + "." + MoviesContract.MovieEntry.MOVIE_ID +
                        " = " + MoviesContract.MovieEntry.TABLE_NAME + "." + MoviesContract.MovieEntry._ID
        );

        return sqLiteQueryBuilder.query(sqlHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private void createDatabase(){
        if(sqlHelper!=null) {
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            db.execSQL(MoviesContract.MovieEntry.SQL_CREATE_TABLE);
            db.execSQL(MoviesContract.MostPopularEntry.SQL_CREATE_TABLE);
            db.execSQL(MoviesContract.MostRatedEntry.SQL_CREATE_TABLE);
        }
    }

    private void clearDatabase(){
        if(sqlHelper!=null) {
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            db.execSQL(MoviesContract.MovieEntry.SQL_DROP_IF_EXISTS);
            db.execSQL(MoviesContract.MostPopularEntry.SQL_DROP_IF_EXISTS);
            db.execSQL(MoviesContract.MostRatedEntry.SQL_DROP_IF_EXISTS);
        }
    }


}
