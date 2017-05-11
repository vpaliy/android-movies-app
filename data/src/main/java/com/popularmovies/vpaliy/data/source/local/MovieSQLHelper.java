package com.popularmovies.vpaliy.data.source.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.model.Trailer;

import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Actors;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Genres;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Trailers;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Reviews;

public class MovieSQLHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="movies.db";
    private static final int DATABASE_VERSION=1;

    interface Tables {

        String MOVIES="movies";
        String REVIEWS="reviews";
        String TRAILERS="trailers";
        String ACTORS="actors";
        String GENRES="genres";
        String POPULAR="popular";
        String UPCOMING="upcoming";
        String TOP_RATED="top_rated";
        String RECOMMENDED="recommended";
        String LATEST="latest";
        String FAVORITE="favorite";
        String NOW_PLAYING="now_playing";
        String SIMILAR_MEDIA="similar_movies";
        String MEDIA_ACTORS="media_actors";
        String MEDIA_REVIEWS="media_reviews";
        String MEDIA_TRAILERS="media_trailers";
        String MEDIA_GENRES="media_genres";

    }

    interface References {
        String MEDIA_ID="REFERENCES "+Tables.MOVIES+"("+Movies._ID+")";
        String ACTOR_ID="REFERENCES "+Tables.ACTORS+"("+ Actors._ID+")";
        String GENRE_ID="REFERENCES "+Tables.GENRES+"("+ Genres._ID+")";
    }

    interface MediaActors {
        String MEDIA_ID="media_id";
        String ACTOR_ID="actor_id";
    }

    interface MediaGenres {
        String MEDIA_ID="media_id";
        String GENRE_ID="genre_id";
    }

    interface MediaTrailers {
        String MEDIA_ID="media_id";
        String TRAILER_ID="trailer_id";
    }

    interface MediaReviews {
        String MEDIA_ID="media_id";
        String REVIEW_ID="review_id";
    }

    interface SimilarMovies {
        String MEDIA_ID="media_id";
        String SIMILAR_MEDIA_ID="similar_media_id";
    }


    public MovieSQLHelper(@NonNull Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Tables.MOVIES+" (" +
                Movies._ID + " INTEGER PRIMARY KEY," +
                Movies.MOVIE_ORIGINAL_TITLE + " TEXT, " +
                Movies.MOVIE_OVERVIEW + " TEXT, " +
                Movies.MOVIE_RELEASE_DATE + " TEXT, " +
                Movies.MOVIE_STATUS + " TEXT, " +
                Movies.MOVIE_TAG_LINE + " TEXT, " +
                Movies.MOVIE_POSTER_URL + " TEXT, " +
                Movies.MOVIE_POPULARITY + " REAL, " +
                Movies.MOVIE_BUDGET+" INTEGER, "+
                Movies.MOVIE_RUNTIME+" INTEGER, "+
                Movies.MOVIE_REVENUE+" INTEGER, "+
                Movies.MOVIE_HOMEPAGE+" TEXT, "+
                Movies.MOVIE_TITLE + " TEXT, " +
                Movies.MOVIE_AVERAGE_VOTE + " REAL, " +
                Movies.MOVIE_VOTE_COUNT + " INTEGER," +
                Movies.MOVIE_BACKDROP_URL + " TEXT, " +
                Movies.MOVIE_BACKDROPS + " TEXT " + " );");

        db.execSQL("CREATE TABLE "+Tables.GENRES+" ("+
                Genres._ID+" INTEGER PRIMARY KEY,"+
                Genres.GENRE_NAME+" TEXT NOT NULL, "+
                "UNIQUE (" + Genres._ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.ACTORS+" ("+
                Actors._ID+" INTEGER PRIMARY KEY,"+
                Actors.ACTOR_NAME+" TEXT NOT NULL, "+
                Actors.ACTOR_BIOGRAPHY+" TEXT,"+
                Actors.ACTOR_IMAGE_URL+" TEXT,"+
                Actors.ACTOR_BIRTHDAY+" TEXT, "+
                Actors.ACTOR_PLACE_OF_BIRTH+" TEXT,"+
                Actors.ACTOR_POPULARITY+" REAL, "+
                Actors.ACTOR_HOMEPAGE+" TEXT,"+
                Actors.ACTOR_DEATH_DAY+" TEXT,"+
                "UNIQUE (" + Actors._ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.TRAILERS+" ("+
                Trailers._ID+" TEXT PRIMARY KEY,"+
                Trailers.TRAILER_TITLE+" TEXT NOT NULL,"+
                Trailers.TRAILER_SITE+" TEXT,"+
                Trailers.TRAILER_VIDEO_URL+" TEXT NOT NULL,"+
                Trailers.TRAILER_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + Trailers._ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.REVIEWS+" ("+
                Reviews._ID+" TEXT PRIMARY KEY,"+
                Reviews.REVIEW_AUTHOR+" TEXT,"+
                Reviews.REVIEW_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                Reviews.REVIEW_URL+" TEXT,"+
                Reviews.REVIEW_CONTENT+" TEXT NOT NULL,"+
                "UNIQUE (" + Reviews._ID + ") ON CONFLICT REPLACE)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
}
