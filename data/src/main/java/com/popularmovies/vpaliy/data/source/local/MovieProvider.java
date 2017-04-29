package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

    private SQLiteOpenHelper sqlHelper;

    private static final int MOVIES=100;
    private static final int MOVIES_WITH_ID=101;
    private static final int MOST_RATED=200;
    private static final int MOST_RATED_WITH_ID=201;
    private static final int MOST_POPULAR=300;
    private static final int MOST_POPULAR_WITH_ID=301;

    private static final String MOVIE_SELECTION_BY_ID=
            MoviesContract.MovieEntry.TABLE_NAME+"."+ MoviesContract.MovieEntry._ID+"=?";

    private static final String MOST_RATED_SELECTION_BY_ID=
            MoviesContract.MostRatedEntry.TABLE_NAME+"."+ MoviesContract.MovieEntry.MOVIE_ID+"=?";

    private static final String MOST_POPULAR_SELECTION_BY_ID=
            MoviesContract.MostPopularEntry.TABLE_NAME+"."+ MoviesContract.MovieEntry.MOVIE_ID+"=?";


    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String authority=MoviesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority,MoviesContract.PATH_MOVIE,MOVIES);
        uriMatcher.addURI(authority,MoviesContract.PATH_MOVIE+"/#",MOVIES_WITH_ID);
        uriMatcher.addURI(authority,MoviesContract.PATH_MOST_POPULAR,MOST_POPULAR);
        uriMatcher.addURI(authority,MoviesContract.PATH_HIGHEST_RATED,MOST_RATED);
        uriMatcher.addURI(authority,MoviesContract.PATH_MOST_POPULAR+"/#",MOST_POPULAR_WITH_ID);
        uriMatcher.addURI(authority,MoviesContract.PATH_HIGHEST_RATED+"/#",MOST_RATED_WITH_ID);

        return uriMatcher;
    }

    private final UriMatcher URI_MATCHER=buildUriMatcher();

    @Override
    public boolean onCreate() {
        if(getContext()!=null) {
            sqlHelper = new MovieSQLHelper(getContext());
            return sqlHelper.getWritableDatabase()!=null;
        }
        return false;

    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match=URI_MATCHER.match(uri);
        Cursor cursor=null;
        long movieId=0;
        switch (match){
            case MOVIES:
                cursor=sqlHelper.getReadableDatabase()
                        .query(MoviesContract.MovieEntry.TABLE_NAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
                break;
            case MOVIES_WITH_ID:
                movieId=ContentUris.parseId(uri);
                cursor=sqlHelper.getReadableDatabase()
                        .query(MoviesContract.MovieEntry.TABLE_NAME,
                                projection,
                                MOVIE_SELECTION_BY_ID,
                                new String[]{Long.toString(movieId)},
                                null,
                                null,
                                sortOrder);
                break;
            case MOST_POPULAR:
                cursor=DatabaseUtils.fetchFromMovieTable(MoviesContract.MostPopularEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder,sqlHelper);
                break;
            case MOST_POPULAR_WITH_ID:
                movieId=ContentUris.parseId(uri);
                cursor=DatabaseUtils.fetchFromMovieTable(MoviesContract.MostPopularEntry.TABLE_NAME,
                                projection,
                                MOST_POPULAR_SELECTION_BY_ID,
                                new String[]{Long.toString(movieId)},
                                sortOrder,sqlHelper);
                break;
            case MOST_RATED:
                cursor=DatabaseUtils.fetchFromMovieTable(MoviesContract.MostRatedEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder,sqlHelper);
                break;
            case MOST_RATED_WITH_ID:
                movieId=ContentUris.parseId(uri);
                cursor=DatabaseUtils.fetchFromMovieTable(MoviesContract.MostRatedEntry.TABLE_NAME,
                        projection,
                        MOST_RATED_SELECTION_BY_ID,
                        new String[]{Long.toString(movieId)},
                        sortOrder,sqlHelper);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match=URI_MATCHER.match(uri);
        int rowsUpdated=0;
        switch (match){
            case MOVIES:
                rowsUpdated=sqlHelper.getWritableDatabase()
                        .update(MoviesContract.MovieEntry.TABLE_NAME,
                                values,selection,selectionArgs);
                break;
            case MOST_POPULAR:
                rowsUpdated=sqlHelper.getWritableDatabase()
                        .update(MoviesContract.MostPopularEntry.TABLE_NAME,
                                values,selection,selectionArgs);
                break;
            case MOST_RATED:
                rowsUpdated=sqlHelper.getWritableDatabase()
                        .update(MoviesContract.MostRatedEntry.TABLE_NAME,
                                values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if(rowsUpdated!=0){
            if(getContext()!=null){
                getContext().getContentResolver().notifyChange(uri,null);
            }
        }
        return rowsUpdated;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match=URI_MATCHER.match(uri);
        int rowsDeleted=0;
        long movieId=0;
        switch (match){
            case MOVIES:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOVIES_WITH_ID:
                movieId= ContentUris.parseId(uri);
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MovieEntry.TABLE_NAME,
                                MOVIE_SELECTION_BY_ID,
                                new String[] {Long.toString(movieId)});
                break;
            case MOST_POPULAR:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MostPopularEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOST_POPULAR_WITH_ID:
                movieId=ContentUris.parseId(uri);
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MostPopularEntry.TABLE_NAME,
                                MOST_POPULAR_SELECTION_BY_ID, new String[]{Long.toString(movieId)});
                break;
            case MOST_RATED:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MostRatedEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOST_RATED_WITH_ID:
                movieId=ContentUris.parseId(uri);
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MostRatedEntry.TABLE_NAME,
                                MOST_RATED_SELECTION_BY_ID,new String[]{Long.toString(movieId)});
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if(rowsDeleted!=0){
            if(getContext()!=null){
                getContext().getContentResolver().notifyChange(uri,null);
            }
        }
        return rowsDeleted;
    }

    @Override
    public void shutdown() {
        sqlHelper.close();
        super.shutdown();
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match=URI_MATCHER.match(uri);
        Uri resultUri=null;
        long id=0;
        switch (match){
            case MOVIES:
                id=sqlHelper.getWritableDatabase()
                        .insertWithOnConflict(MoviesContract.MovieEntry.TABLE_NAME,null,values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                if(id>0){
                    resultUri=ContentUris.withAppendedId(MoviesContract.MovieEntry.CONTENT_URI,id);
                }
                break;
            case MOST_POPULAR:
                id=sqlHelper.getWritableDatabase()
                        .insertWithOnConflict(MoviesContract.MostPopularEntry.TABLE_NAME,null,values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                if(id>0){
                    resultUri= MoviesContract.MostPopularEntry.CONTENT_URI;
                }
                break;
            case MOST_RATED:
                id=sqlHelper.getWritableDatabase()
                        .insertWithOnConflict(MoviesContract.MostRatedEntry.TABLE_NAME,null,values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                if(id>0){
                    resultUri= MoviesContract.MostRatedEntry.CONTENT_URI;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);

        }
        return resultUri;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match=URI_MATCHER.match(uri);
        switch (match){
            case MOVIES:
                return MoviesContract.MovieEntry.CONTENT_DIR_TYPE;
            case MOVIES_WITH_ID:
                return MoviesContract.MovieEntry.CONTENT_ITEM_TYPE;
            case MOST_POPULAR:
                return MoviesContract.MostPopularEntry.CONTENT_DIR_TYPE;
            case MOST_RATED:
                return MoviesContract.MostRatedEntry.CONTENT_DIR_TYPE;
        }
        return null;
    }


}
