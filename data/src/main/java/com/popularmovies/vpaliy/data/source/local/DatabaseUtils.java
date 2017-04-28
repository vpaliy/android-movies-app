package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
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

public final class DatabaseUtils {

    private DatabaseUtils(){
        throw new UnsupportedOperationException("Can't be created");
    }

    public static ContentValues convertToValues(Movie item){
        if(item==null) return null;

        ContentValues values=new ContentValues();
        values.put(_ID,item.getMovieId());
        values.put(COLUMN_ORIGINAL_TITLE,item.getOriginalTitle());
        values.put(COLUMN_OVERVIEW,item.getOverview());
        values.put(COLUMN_RELEASE_DATE,item.getReleaseDate());
        values.put(COLUMN_POSTER_PATH,item.getPosterPath());
        values.put(COLUMN_POPULARITY,item.getPopularity());
        values.put(COLUMN_BUDGET,item.getBudget());
        values.put(COLUMN_RUNTIME,item.getRuntime());
        values.put(COLUMN_REVENUE,item.getRevenue());
        Type type=new TypeToken<ArrayList<Genre>>(){}.getType();
        String jsonString=convertToJsonString(item.getGenres(),type);
        values.put(COLUMN_GENRES,jsonString);
        values.put(COLUMN_HOME_PAGE,item.getHomepage());
        values.put(COLUMN_TITLE,item.getTitle());
        values.put(COLUMN_IS_FAVORITE,item.isFavorite()?1:0);
        values.put(COLUMN_VOTE_COUNT,item.getVoteCount());
        values.put(COLUMN_AVERAGE_VOTE,item.getVoteAverage());
        values.put(COLUMN_BACKDROP_PATH,item.getBackdrop_path());
        type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        jsonString=convertToJsonString(item.getBackdropImages(),type);
        values.put(COLUMN_MOVIE_BACKDROPS,jsonString);

        return values;
    }


    public static Movie convertToMovie(Cursor cursor){
        if(cursor==null) return null;
        Movie movie=new Movie();

        final int movieId=cursor.getInt(cursor.getColumnIndex(_ID));
        final String originalTitle=cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_TITLE));
        final String overview=cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW));
        final String releaseDate=cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE));
        final String posterPath=cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH));
        final Number popularity=cursor.getDouble(cursor.getColumnIndex(COLUMN_POPULARITY));
        final long budget=cursor.getLong(cursor.getColumnIndex(COLUMN_BUDGET));
        final int runtime=cursor.getInt(cursor.getColumnIndex(COLUMN_RUNTIME));
        final long revenue=cursor.getLong(cursor.getColumnIndex(COLUMN_REVENUE));
        final String jsonGenresString=cursor.getString(cursor.getColumnIndex(COLUMN_GENRES));
        Type type = new TypeToken<ArrayList<Genre>>() {}.getType();
        movie.setGenres(convertFromJsonString(jsonGenresString,type));
        final String homePage=cursor.getString(cursor.getColumnIndex(COLUMN_HOME_PAGE));
        final String title=cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        final boolean isFavorite=cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE))==1;
        final double averageVote=cursor.getDouble(cursor.getColumnIndex(COLUMN_AVERAGE_VOTE));
        final long voteCount=cursor.getLong(cursor.getColumnIndex(COLUMN_VOTE_COUNT));
        final String backdropPath=cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP_PATH));
        final String jsonBackdropsString=cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_BACKDROPS));
        type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        movie.setBackdropImages(convertFromJsonString(jsonBackdropsString,type));

        movie.setMovieId(movieId);
        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setPopularity(popularity);
        movie.setPosterPath(posterPath);
        movie.setReleaseDate(releaseDate);
        movie.setFavorite(isFavorite);
        movie.setVoteCount(voteCount);
        movie.setBackdropPath(backdropPath);
        movie.setOriginalTitle(originalTitle);
        movie.setBudget(budget);
        movie.setRuntime(runtime);
        movie.setRevenue(revenue);
        movie.setHomepage(homePage);
        movie.setVoteAverage(averageVote);

        return movie;
    }


    public static String convertToJsonString(Object object, Type type){
        if(object==null) return null;
        Gson gson=new Gson();
        return gson.toJson(object,type);
    }

    public static <T> T convertFromJsonString(String jsonString, Type type){
        if(jsonString==null) return null;
        Gson gson=new Gson();
        return gson.fromJson(jsonString,type);
    }

    public static Cursor fetchFromMovieTable(String tableName, String[] projection, String selection,
                                  String[] selectionArgs, String sortOrder, SQLiteOpenHelper sqLiteOpenHelper) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        sqLiteQueryBuilder.setTables(
                tableName + " INNER JOIN " + MoviesContract.MovieEntry.TABLE_NAME +
                        " ON " + tableName + "." + MoviesContract.MovieEntry.MOVIE_ID +
                        " = " + MoviesContract.MovieEntry.TABLE_NAME + "." + _ID
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
