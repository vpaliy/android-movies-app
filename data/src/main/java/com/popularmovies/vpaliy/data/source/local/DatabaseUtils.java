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
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.popularmovies.vpaliy.data.source.local.MovieSQLHelper.Tables;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Actors;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Genres;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Trailers;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Reviews;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.PopularMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.FavoriteMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.TopRatedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.UpcomingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.LatestMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.RecommendedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.NowPlayingMedia;

public final class DatabaseUtils {

    private DatabaseUtils(){
        throw new UnsupportedOperationException("Can't be created");
    }

    public static ContentValues convertToValues(Movie item){
        if(item==null) return null;

        ContentValues values=new ContentValues();
        values.put(_ID,item.getMovieId());
        values.put(Movies.MOVIE_ORIGINAL_TITLE,item.getOriginalTitle());
        values.put(Movies.MOVIE_OVERVIEW,item.getOverview());
        values.put(Movies.MOVIE_RELEASE_DATE,item.getReleaseDate());
        values.put(Movies.MOVIE_POSTER_URL,item.getPosterPath());
        values.put(Movies.MOVIE_POPULARITY,item.getPopularity());
        values.put(Movies.MOVIE_BUDGET,item.getBudget());
        values.put(Movies.MOVIE_RUNTIME,item.getRuntime());
        values.put(Movies.MOVIE_REVENUE,item.getRevenue());
        values.put(Movies.MOVIE_HOMEPAGE,item.getHomepage());
        values.put(Movies.MOVIE_TITLE,item.getTitle());
        values.put(Movies.MOVIE_VOTE_COUNT,item.getVoteCount());
        values.put(Movies.MOVIE_AVERAGE_VOTE,item.getVoteAverage());

        //convert the backdrops
        Type type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        String jsonString=convertToJsonString(item.getBackdropImages(),type);
        values.put(Movies.MOVIE_BACKDROPS,jsonString);

        return values;
    }


    public static Movie convertToMovie(Cursor cursor){
        if(cursor==null) return null;
        Movie movie=new Movie();

        final String movieId=cursor.getString(cursor.getColumnIndex(_ID));
        final String originalTitle=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_ORIGINAL_TITLE));
        final String overview=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_OVERVIEW));
        final String releaseDate=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_RELEASE_DATE));
        final String posterPath=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_POSTER_URL));
        final Number popularity=cursor.getDouble(cursor.getColumnIndex(Movies.MOVIE_POPULARITY));
        final long budget=cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_BUDGET));
        final int runtime=cursor.getInt(cursor.getColumnIndex(Movies.MOVIE_RUNTIME));
        final long revenue=cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_REVENUE));
        final String homePage=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_HOMEPAGE));
        final String title=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_TITLE));
        final double averageVote=cursor.getDouble(cursor.getColumnIndex(Movies.MOVIE_AVERAGE_VOTE));
        final long voteCount=cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_VOTE_COUNT));
        final String jsonBackdropsString=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_BACKDROPS));
        Type type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        movie.setBackdropImages(convertFromJsonString(jsonBackdropsString,type));

        movie.setMovieId(movieId);
        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setPopularity(popularity);
        movie.setPosterPath(posterPath);
        movie.setReleaseDate(releaseDate);
        movie.setVoteCount(voteCount);
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

}
