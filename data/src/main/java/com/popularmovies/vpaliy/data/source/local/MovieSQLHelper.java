package com.popularmovies.vpaliy.data.source.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;


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

public class MovieSQLHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="movies.db";
    private static final int DATABASE_VERSION=1;

    private static final String INNER_JOIN=" INNER JOIN ";
    private static final String LEFT_OUTER_JOIN=" LEFT OUTER JOIN ";
    private static final String ON=" ON ";
    private static final String DOT=".";

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
        String MEDIA_GENRES="media_genres";

        String MOVIE_JOIN_TRAILERS=LEFT_OUTER_JOIN+TRAILERS+ON+MOVIES+DOT+Movies._ID+"="+TRAILERS+DOT+Trailers.TRAILER_MEDIA_ID;

        String MOVIE_JOIN_REVIEWS=LEFT_OUTER_JOIN+REVIEWS+ON+MOVIES+DOT+Movies._ID+"="+REVIEWS+DOT+Reviews.REVIEW_MEDIA_ID;

        String MOVIE_JOIN_SIMILAR_MOVIES=LEFT_OUTER_JOIN+SIMILAR_MEDIA+ON+MOVIES+DOT+Movies._ID+"="+SIMILAR_MEDIA+DOT+SimilarMovies.MEDIA_ID;

        String MOVIE_JOIN_GENRES=LEFT_OUTER_JOIN+MEDIA_GENRES+ON+MOVIES+DOT+Movies._ID+"="+MEDIA_GENRES+DOT+MediaGenres.MEDIA_ID;

        String MOVIE_JOIN_ACTORS=LEFT_OUTER_JOIN+MEDIA_ACTORS+ON+MOVIES+DOT+Movies._ID+"="+MEDIA_ACTORS+DOT+MediaActors.MEDIA_ID;

        String MOVIE_JOIN_DETAILS=MOVIES+" "+
                                MOVIE_JOIN_TRAILERS+" "+
                                MOVIE_JOIN_ACTORS+" "+
                                MOVIE_JOIN_GENRES+" "+
                                MOVIE_JOIN_REVIEWS+" "+
                                MOVIE_JOIN_SIMILAR_MOVIES;

        String MOVIE_JOIN_FAVORITES=MOVIES+" "+
                    INNER_JOIN+FAVORITE+ON+MOVIES+DOT+Movies._ID+"="+FAVORITE+DOT+FavoriteMedia.COLLECTION_MEDIA_ID;

        String MOVIE_JOIN_TOP_RATED=MOVIES+" "+
                INNER_JOIN+TOP_RATED+ON+MOVIES+DOT+Movies._ID+"="+TOP_RATED+DOT+TopRatedMedia.COLLECTION_MEDIA_ID;

        String MOVIE_JOIN_LATEST=MOVIES+" "+
                INNER_JOIN+LATEST+ON+MOVIES+DOT+Movies._ID+"="+LATEST+DOT+LatestMedia.COLLECTION_MEDIA_ID;

        String MOVIE_JOIN_NOW_PLAYING=MOVIES+" "+
                INNER_JOIN+NOW_PLAYING+ON+MOVIES+DOT+Movies._ID+"="+NOW_PLAYING+DOT+NowPlayingMedia.COLLECTION_MEDIA_ID;

        String MOVIE_JOIN_UPCOMING=MOVIES+" "+
                INNER_JOIN+UPCOMING+ON+MOVIES+DOT+Movies._ID+"="+UPCOMING+DOT+UpcomingMedia.COLLECTION_MEDIA_ID;

        String MOVIE_JOIN_POPULAR=MOVIES+" "+
                INNER_JOIN+POPULAR+ON+MOVIES+DOT+Movies._ID+"="+POPULAR+DOT+PopularMedia.COLLECTION_MEDIA_ID;

        String MOVIE_JOIN_RECOMMENDED=MOVIES+" "+
                INNER_JOIN+RECOMMENDED+ON+MOVIES+DOT+Movies._ID+"="+RECOMMENDED+DOT+RecommendedMedia.COLLECTION_MEDIA_ID;

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
                Movies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Movies.MOVIE_ID+" INTEGER NOT NULL,"+
                Movies.MOVIE_TITLE + " TEXT," +
                Movies.MOVIE_ORIGINAL_TITLE + " TEXT," +
                Movies.MOVIE_OVERVIEW + " TEXT," +
                Movies.MOVIE_RELEASE_DATE + " TEXT," +
                Movies.MOVIE_STATUS + " TEXT," +
                Movies.MOVIE_TAG_LINE + " TEXT," +
                Movies.MOVIE_POSTER_URL + " TEXT," +
                Movies.MOVIE_POPULARITY + " REAL," +
                Movies.MOVIE_BUDGET+" INTEGER,"+
                Movies.MOVIE_RUNTIME+" INTEGER,"+
                Movies.MOVIE_REVENUE+" INTEGER,"+
                Movies.MOVIE_HOMEPAGE+" TEXT,"+
                Movies.MOVIE_GENRES+ " TEXT,"+
                Movies.MOVIE_AVERAGE_VOTE + " REAL," +
                Movies.MOVIE_VOTE_COUNT + " INTEGER," +
                Movies.MOVIE_ACTORS+"  TEXT NOT NULL,"+
                Movies.MOVIE_BACKDROPS + " TEXT," +
                " UNIQUE (" + Movies.MOVIE_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.ACTORS+" ("+
                Actors._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Actors.ACTOR_ID+" INTEGER NOT NULL,"+
                Actors.ACTOR_NAME+" TEXT NOT NULL, "+
                Actors.ACTOR_BIOGRAPHY+" TEXT,"+
                Actors.ACTOR_IMAGE_URL+" TEXT,"+
                Actors.ACTOR_BIRTHDAY+" TEXT,"+
                Actors.ACTOR_PLACE_OF_BIRTH+" TEXT,"+
                Actors.ACTOR_POPULARITY+" REAL, "+
                Actors.ACTOR_HOMEPAGE+" TEXT,"+
                Actors.ACTOR_DEATH_DAY+" TEXT,"+
                " UNIQUE (" + Actors.ACTOR_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.TRAILERS+" ("+
                Trailers._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Trailers.TRAILER_ID+" INTEGER NOT NULL,"+
                Trailers.TRAILER_TITLE+" TEXT NOT NULL,"+
                Trailers.TRAILER_SITE+" TEXT,"+
                Trailers.TRAILER_VIDEO_URL+" TEXT NOT NULL,"+
                Trailers.TRAILER_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + Trailers.TRAILER_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.GENRES+" ("+
                Genres.GENRE_ID+" TEXT PRIMARY KEY NOT NULL,"+
                Genres.GENRE_NAME+" TEXT NOT NULL,"+
                " UNIQUE (" + Genres.GENRE_ID + ") ON CONFLICT REPLACE)");


        db.execSQL("CREATE TABLE "+Tables.REVIEWS+" ("+
                Reviews._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Reviews.REVIEW_ID+" INTEGER NOT NULL,"+
                Reviews.REVIEW_AUTHOR+" TEXT,"+
                Reviews.REVIEW_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                Reviews.REVIEW_URL+" TEXT,"+
                Reviews.REVIEW_CONTENT+" TEXT NOT NULL,"+
                "UNIQUE (" + Reviews.REVIEW_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.POPULAR+" ("+
                PopularMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                PopularMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + PopularMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.FAVORITE+" ("+
                FavoriteMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                FavoriteMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + FavoriteMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.TOP_RATED+" ("+
                TopRatedMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TopRatedMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + TopRatedMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.UPCOMING+" ("+
                UpcomingMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                UpcomingMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + UpcomingMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.RECOMMENDED+" ("+
                RecommendedMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                RecommendedMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + RecommendedMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.LATEST+" ("+
                LatestMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                LatestMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + LatestMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.NOW_PLAYING+" ("+
                NowPlayingMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NowPlayingMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + NowPlayingMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.SIMILAR_MEDIA+" ("+
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                SimilarMovies.MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                SimilarMovies.SIMILAR_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","
                + "UNIQUE (" + SimilarMovies.MEDIA_ID + ","
                + SimilarMovies.SIMILAR_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.MEDIA_ACTORS+" ("+
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                MediaActors.MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                MediaActors.ACTOR_ID+" INTEGER NOT NULL "+References.ACTOR_ID+","
                + " UNIQUE (" + MediaActors.MEDIA_ID + ","
                + MediaActors.ACTOR_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.MEDIA_GENRES+" ("+
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                MediaGenres.MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                MediaGenres.GENRE_ID+" TEXT NOT NULL "+References.GENRE_ID+","
                + " UNIQUE (" + MediaGenres.MEDIA_ID + ","
                + MediaGenres.GENRE_ID + ") ON CONFLICT REPLACE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+Tables.MOVIES);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.ACTORS);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.GENRES);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.MEDIA_GENRES);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.MEDIA_ACTORS);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.SIMILAR_MEDIA);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.LATEST);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.UPCOMING);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.TRAILERS);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.POPULAR);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.TOP_RATED);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.NOW_PLAYING);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.RECOMMENDED);
        onCreate(db);
    }

    static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
