package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

    private SQLiteOpenHelper sqlHelper;

    private static final int MOVIES=100;
    private static final int MOVIES_WITH_ID=101;
    private static final int MOST_RATED=200;
    private static final int MOST_POPULAR=300;
    private static final int FAVORITES=400;

    private static final String MOVIE_SELECTION_BY_ID=
            MoviesContract.MovieEntry.TABLE_NAME+"."+ MoviesContract.MovieEntry._ID+"=?";

    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String authority=MoviesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority,MoviesContract.PATH_MOVIE,MOVIES);
        uriMatcher.addURI(authority,MoviesContract.PATH_MOVIE+"/#",MOVIES_WITH_ID);
        uriMatcher.addURI(authority,MoviesContract.PATH_FAVORITE,FAVORITES);
        uriMatcher.addURI(authority,MoviesContract.PATH_MOST_POPULAR,MOST_POPULAR);
        uriMatcher.addURI(authority,MoviesContract.PATH_HIGHEST_RATED,MOST_RATED);

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

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match=URI_MATCHER.match(uri);
        Cursor cursor=null;
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
            case MOST_POPULAR:
                cursor=fetchFromTable(MoviesContract.MostPopularEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder);
                break;
            case MOST_RATED:
                cursor=fetchFromTable(MoviesContract.MostRatedEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder);
                break;
            case FAVORITES:
                cursor=fetchFromTable(MoviesContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return cursor;
    }



    private Cursor fetchMovieById(Uri uri, String[] projection, String sortOrder) {
        long id = ContentUris.parseId(uri);
        String[] selectionArgs = new String[]{Long.toString(id)};
        return sqlHelper.getReadableDatabase().query(
                MoviesContract.MovieEntry.TABLE_NAME,
                projection,
                MOVIE_SELECTION_BY_ID,
                selectionArgs,
                null,
                null,
                sortOrder
        );
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

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match=URI_MATCHER.match(uri);
        int rowsDeleted=0;
        switch (match){
            case MOVIES:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOVIES_WITH_ID:
                long movieId= ContentUris.parseId(uri);
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MovieEntry.TABLE_NAME,
                                MOVIE_SELECTION_BY_ID,
                                new String[] {Long.toString(movieId)});
                break;
            case MOST_POPULAR:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MostPopularEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOST_RATED:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.MostRatedEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case FAVORITES:
                rowsDeleted=sqlHelper.getWritableDatabase()
                        .delete(MoviesContract.FavoriteEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //
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
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


}
