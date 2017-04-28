package com.popularmovies.vpaliy.data.source.local;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.popularmovies.vpaliy.data.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;
import org.robolectric.res.FsFile;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class MovieProviderTest  {

    private MovieSQLHelper sqlHelper;

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
