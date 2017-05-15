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
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.WatchedhMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MustWatchMedia;

public class MovieSQLHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="movies.db";
    private static final int DATABASE_VERSION=2;


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
        String MUST_WATCH="must_watch";
        String WATCHED="watched";
        String LATEST="latest";
        String FAVORITE="favorite";
        String NOW_PLAYING="now_playing";
        String SIMILAR_MEDIA="similar_movies";
        String MEDIA_ACTORS="media_actors";
        String MEDIA_GENRES="media_genres";


        String MOVIE_JOIN_TRAILERS="movies"
                +"INNER JOIN trailers ON movies.movie_id=trailers.media_id";

        String MOVIE_JOIN_REVIEWS="movies"
                +"INNER JOIN reviews ON movies.movie_id=reviews.media_id";

        String MOVIE_JOIN_SIMILAR_MOVIES="movies"
                +"INNER JOIN similar_movies ON movies.movie_id=similar_movies.media_id";

        String MOVIES_GENRES_JOIN_GENRES="media_genres"
                +"INNER JOIN genres ON media_genres.genre_id=genres.genre_id";

        String MOVIES_GENRES_JOIN_MOVIES="media_genres"
                +"INNER JOIN movies ON media_genres.media_id=movies.movie_id";

        String MOVIES_ACTORS_JOIN_ACTORS="media_actors"
                +"INNER JOIN actors ON media_actors.actor_id=actors.actor_id";

        String MOVIES_ACTORS_JOIN_MOVIES="media_actors"
                +"INNER JOIN movies ON media_actors.media_id=movies.movie_id";

        String MOVIE_JOIN_FAVORITES="movies"
                +"INNER JOIN favorite ON movies.movie_id=favorite.media_id";

        String MOVIE_JOIN_TOP_RATED="movies"
                +"INNER JOIN top_rated ON movies.movie_id=top_rated.media_id";

        String MOVIE_JOIN_LATEST="movies"
                +"INNER JOIN latest ON movies.movie_id=latest.media_id";

        String MOVIE_JOIN_NOW_PLAYING="movies"
                +"INNER JOIN now_playing ON movies.movie_id=now_playing.media_id";

        String MOVIE_JOIN_UPCOMING="movies"
                +"INNER JOIN upcoming ON movies.movie_id=upcoming.media_id";

        String MOVIE_JOIN_POPULAR="movies"
                +"INNER JOIN popular ON movies.movie_id=popular.media_id";

        String MOVIE_JOIN_RECOMMENDED="movies"
                +"INNER JOIN recommended ON movies.movie_id=recommended.media_id";

        String MOVIE_JOIN_MUST_WATCH="movies"
                +"INNER JOIN must_watch ON movies.movie_id=must_watch.media_id";

        String MOVIE_JOIN_WATCHED="movies"
                +"INNER JOIN watched ON movies.movie_id=watched.media_id";

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
                Movies.MOVIE_ACTORS+"  TEXT,"+
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

        db.execSQL("CREATE TABLE "+Tables.WATCHED+" ("+
                WatchedhMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                WatchedhMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + WatchedhMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.MUST_WATCH+" ("+
                MustWatchMedia._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                MustWatchMedia.COLLECTION_MEDIA_ID+" INTEGER NOT NULL "+References.MEDIA_ID+","+
                "UNIQUE (" + MustWatchMedia.COLLECTION_MEDIA_ID + ") ON CONFLICT REPLACE)");

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
        db.execSQL("DROP TABLE IF EXISTS"+Tables.MUST_WATCH);
        db.execSQL("DROP TABLE IF EXISTS"+Tables.WATCHED);
        onCreate(db);
    }

    static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
