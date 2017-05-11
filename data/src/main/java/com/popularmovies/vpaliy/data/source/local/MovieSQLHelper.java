package com.popularmovies.vpaliy.data.source.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;

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
        String MEDIA_ACTORS="media_actors";
        String MEDIA_REVIEWS="media_reviews";
        String MEDIA_TRAILERS="media_trailers";
        String MEDIA_GENRES="media_genres";

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



    public MovieSQLHelper(@NonNull Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Tables.MOVIES+" (" +
                Movies._ID + " INTEGER PRIMARY KEY, " +
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




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
}
