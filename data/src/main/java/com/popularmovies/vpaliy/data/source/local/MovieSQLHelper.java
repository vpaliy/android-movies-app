package com.popularmovies.vpaliy.data.source.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class MovieSQLHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="movies.db";
    private static final int DATABASE_VERSION=0;

    public MovieSQLHelper(@NonNull Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MoviesContract.MovieEntry.SQL_CREATE_TABLE);
        db.execSQL(MoviesContract.MostPopularEntry.SQL_CREATE_TABLE);
        db.execSQL(MoviesContract.MostRatedEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MoviesContract.MovieEntry.SQL_DROP_IF_EXISTS);
        db.execSQL(MoviesContract.MostPopularEntry.SQL_DROP_IF_EXISTS);
        db.execSQL(MoviesContract.MostRatedEntry.SQL_DROP_IF_EXISTS);
        onCreate(db);
    }
}
