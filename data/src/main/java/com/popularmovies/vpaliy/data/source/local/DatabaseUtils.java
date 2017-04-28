package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseUtils {

    private DatabaseUtils(){
        throw new UnsupportedOperationException("Can't be created");
    }

    public static ContentValues convertToValues(Movie item){
        if(item==null) return null;
        Gson gson=new Gson();
        String jsonBackdrops=item.getBackdropImages()!=null?
                gson.toJson(item.getBackdropImages()):null;
        ContentValues values=new ContentValues();
        values.put(MoviesContract.MovieEntry._ID,item.getMovieId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE,item.getTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE,item.getOriginalTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW,item.getOverview());
        values.put(MoviesContract.MovieEntry.COLUMN_POPULARITY,item.getPopularity());
        values.put(MoviesContract.MovieEntry.COLUMN_MOVIE_BACKDROPS,jsonBackdrops);
        values.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH,item.getPosterPath());
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE,item.getReleaseDate());
        values.put(MoviesContract.MovieEntry.COLUMN_IS_FAVORITE,item.isFavorite()?1:0);
        values.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT,item.getVoteCount());
        values.put(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE,item.getVoteAverage());
        values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH,item.getBackdrop_path());
        return values;
    }


    public static Movie convertToMovie(Cursor cursor){
        if(cursor==null) return null;
        Movie movie=new Movie();
        final String title=cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE));
        final String overview=cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW));
        final Number popularity=cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY));
        final String backdrops=cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_BACKDROPS));
        if(backdrops!=null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            final List<String> backdropList = gson.fromJson(backdrops, type);
            movie.setBackdropImages(BackdropImage.convertToBackdrops(backdropList));
        }
        final String posterPath=cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_PATH));
        final String releaseDate=cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE));
        final boolean isFavorite=cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_IS_FAVORITE))==1;
        final long voteCount=cursor.getLong(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT));
        final String backdropPath=cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH));

        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setPopularity(popularity);
        movie.setPosterPath(posterPath);
        movie.setReleaseDate(releaseDate);
        movie.setFavorite(isFavorite);
        movie.setVoteCount(voteCount);
        movie.setBackdropPath(backdropPath);

        return movie;
    }


    private Cursor fetchFromTable(String tableName, String[] projection, String selection,
                                  String[] selectionArgs, String sortOrder, SQLiteOpenHelper sqLiteOpenHelper) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        sqLiteQueryBuilder.setTables(
                tableName + " INNER JOIN " + MoviesContract.MovieEntry.TABLE_NAME +
                        " ON " + tableName + "." + MoviesContract.MovieEntry.MOVIE_ID +
                        " = " + MoviesContract.MovieEntry.TABLE_NAME + "." + MoviesContract.MovieEntry._ID
        );

        return sqLiteQueryBuilder.query(sqLiteOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
}
