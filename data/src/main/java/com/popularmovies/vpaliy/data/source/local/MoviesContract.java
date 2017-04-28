package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MoviesContract {

    public static final String CONTENT_AUTHORITY="com.popularmovies.vpaliy";
    public static final String PREFIX="content://";
    public static final Uri BASE_CONTENT_URI=Uri.parse(PREFIX+CONTENT_AUTHORITY);

    public static final String PATH_MOVIE="movie";
    public static final String PATH_MOVIE_DETAILS=PATH_MOVIE+"/"+"movieDetails";
    public static final String PATH_MOST_POPULAR=PATH_MOVIE+"/"+"mostPopular";
    public static final String PATH_HIGHEST_RATED=PATH_MOVIE+"/"+"highestRated";
    public static final String PATH_FAVORITE=PATH_MOVIE+"/"+"favorite";


    private MoviesContract(){
        throw new UnsupportedOperationException("Can't create a class instance");
    }



    public static class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_MOVIE;

        public static final String MOVIE_ID="movieId";

        public static final String TABLE_NAME="movies";
        public static final String COLUMN_ORIGINAL_TITLE="originalTitle";
        public static final String COLUMN_OVERVIEW="overview";
        public static final String COLUMN_POPULARITY="popularity";
        public static final String COLUMN_MOVIE_BACKDROPS="backdrops";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IS_FAVORITE="isFavorite";
        public static final String COLUMN_AVERAGE_VOTE = "voteAverage";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_BACKDROP_PATH = "backdropPath";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_ORIGINAL_TITLE + " TEXT, " +
                        COLUMN_OVERVIEW + " TEXT, " +
                        COLUMN_RELEASE_DATE + " TEXT, " +
                        COLUMN_POSTER_PATH + " TEXT, " +
                        COLUMN_POPULARITY + " REAL, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_IS_FAVORITE+" INTEGER, "+
                        COLUMN_AVERAGE_VOTE + " REAL, " +
                        COLUMN_VOTE_COUNT + " INTEGER," +
                        COLUMN_BACKDROP_PATH + " TEXT, " +
                        COLUMN_MOVIE_BACKDROPS + " TEXT " +
                        " );";

        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;


    }




    public static class MostPopularEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOST_POPULAR).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + "/" + PATH_MOVIE+"/"+PATH_MOST_POPULAR;

        public static final String TABLE_NAME="mostPopular";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieEntry.MOVIE_ID + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + MovieEntry.MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") " + " );";
        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;

    }



    public static class MostRatedEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_HIGHEST_RATED).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE
                        +"/"+PATH_HIGHEST_RATED;

        public static final String TABLE_NAME="favorites";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieEntry.MOVIE_ID + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + MovieEntry.MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") " + " );";
        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;

    }


}
