package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MoviesContract {


    interface MovieColumns extends BaseColumns{
        String MOVIE_TITLE = "title";
        String MOVIE_ORIGINAL_TITLE="original_title";
        String MOVIE_TAGLINE="tagline";
        String MOVIE_STATUS="status";
        String MOVIE_OVERVIEW="overview";
        String MOVIE_BUDGET="budget";
        String MOVIE_REVENUE="revenue";
        String MOVIE_RUNTIME="runtime";
        String MOVIE_HOMEPAGE="homepage";
        String MOVIE_POPULARITY="popularity";
        String MOVIE_RELEASE_DATE = "release_date";
        String MOVIE_AVERAGE_VOTE = "vote_average";
        String MOVIE_VOTE_COUNT = "vote_count";
        String MOVIE_BACKDROPS="backdrops";
        String MOVIE_POSTER_URL = "poster_url";
        String MOVIE_BACKDROP_URL = "backdrop_url";
    }


    interface ActorColumns extends BaseColumns {
        String ACTOR_NAME="name";
        String ACTOR_BIRTHDAY="birthday";
        String ACTOR_BIOGRAPHY="biography";
        String ACTOR_HOMEPAGE="homepage";
        String ACTOR_PLACE_OF_BIRTH="place_of_birth";
        String ACTOR_POPULARITY="popularity";
        String ACTOR_IMAGE_URL="image_url";
    }

    interface TrailerColumns extends BaseColumns {
        String TRAILER_MEDIA_ID="media_id";
        String TRAILER_VIDEO_URL="video_url";
        String TRAILER_TITLE="trailer_title";
        String TRAILER_SITE="site";
    }

    interface ReviewColumns extends BaseColumns{
        String REVIEW_MEDIA_ID="media_id";
        String REVIEW_AUTHOR="author";
        String REVIEW_CONTENT="content";
        String REVIEW_URL="review_url";
    }


    public static final String CONTENT_AUTHORITY="com.popularmovies.vpaliy";
    public static final String PREFIX="content://";
    public static final Uri BASE_CONTENT_URI=Uri.parse(PREFIX+CONTENT_AUTHORITY);

    public static final String PATH_MOVIE="movie";
    public static final String PATH_ACTOR="actor";
    public static final String PATH_TRAILER="trailer";
    public static final String PATH_REVIEW="review";

    public static final String PATH_FAVORITE="favorite";
    public static final String PATH_TOP_RATED="top_rated";
    public static final String PATH_NOW_PLAYING="now_playing";
    public static final String PATH_UPCOMING="upcoming";
    public static final String PATH_LATEST="latest";
    public static final String PATH_POPULAR="most_popular";
    public static final String PATH_RECOMMENDED="recommended";


    private MoviesContract(){
        throw new UnsupportedOperationException("Can't create a class instance");
    }


    public static class Movies implements MovieColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_MOVIE;

        public static Uri buildMovieUri(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).build();
        }

        public static String getMovieId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }

    }


    public static class Trailers implements TrailerColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_TRAILER;

        public static Uri buildTrailerUri(String trailerId){
            return CONTENT_URI.buildUpon().appendPath(trailerId).build();
        }

        public static String getTrailerId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }

    }

    public static class Reviews implements ReviewColumns{

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_REVIEW;

        public static Uri buildReviewUri(String reviewUri){
            return CONTENT_URI.buildUpon().appendPath(reviewUri).build();
        }

        public static String getReviewId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class Actors implements ActorColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTOR).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ACTOR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_ACTOR;

        public static Uri buildActorUri(String actorUri){
            return CONTENT_URI.buildUpon().appendPath(actorUri).build();
        }

        public static String getActorId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }

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
        public static final String COLUMN_BUDGET="budget";
        public static final String COLUMN_REVENUE="revenue";
        public static final String COLUMN_RUNTIME="runtime";
        public static final String COLUMN_HOME_PAGE="homePage";
        public static final String COLUMN_POPULARITY="popularity";
        public static final String COLUMN_MOVIE_BACKDROPS="backdrops";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IS_FAVORITE="isFavorite";
        public static final String COLUMN_AVERAGE_VOTE = "voteAverage";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_GENRES="genres";
        public static final String COLUMN_BACKDROP_PATH = "backdropPath";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_ORIGINAL_TITLE + " TEXT, " +
                        COLUMN_OVERVIEW + " TEXT, " +
                        COLUMN_RELEASE_DATE + " TEXT, " +
                        COLUMN_POSTER_PATH + " TEXT, " +
                        COLUMN_POPULARITY + " REAL, " +
                        COLUMN_BUDGET+" INTEGER, "+
                        COLUMN_RUNTIME+" INTEGER, "+
                        COLUMN_REVENUE+" INTEGER, "+
                        COLUMN_GENRES+" TEXT, "+
                        COLUMN_HOME_PAGE+" TEXT, "+
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_IS_FAVORITE+" INTEGER, "+
                        COLUMN_AVERAGE_VOTE + " REAL, " +
                        COLUMN_VOTE_COUNT + " INTEGER," +
                        COLUMN_BACKDROP_PATH + " TEXT, " +
                        COLUMN_MOVIE_BACKDROPS + " TEXT " +
                        " );";

        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;


        public static final String[] COLUMNS= new String[]{
                _ID,
                COLUMN_ORIGINAL_TITLE,COLUMN_OVERVIEW,
                COLUMN_RELEASE_DATE,COLUMN_POSTER_PATH,
                COLUMN_POPULARITY,COLUMN_BUDGET,
                COLUMN_RUNTIME,COLUMN_REVENUE,
                COLUMN_GENRES,COLUMN_HOME_PAGE,
                COLUMN_TITLE,COLUMN_IS_FAVORITE,
                COLUMN_AVERAGE_VOTE,COLUMN_VOTE_COUNT,
                COLUMN_BACKDROP_PATH,COLUMN_MOVIE_BACKDROPS
        };


    }




    public static class MostPopularEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOST_POPULAR).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + "/" + PATH_MOVIE+"/"+PATH_MOST_POPULAR;

        public static final String TABLE_NAME="mostPopular";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        MovieEntry.MOVIE_ID + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + MovieEntry.MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") " + " );";
        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;

    }


    public static class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE
                        +"/"+PATH_FAVORITE;

        public static final String TABLE_NAME="favorite";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        MovieEntry.MOVIE_ID + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + MovieEntry.MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") " + " );";

        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;
    }


    public static class MostRatedEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_HIGHEST_RATED).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE
                        +"/"+PATH_HIGHEST_RATED;

        public static final String TABLE_NAME="favorites";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        MovieEntry.MOVIE_ID + " INTEGER NOT NULL, " +
                        " FOREIGN KEY (" + MovieEntry.MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") " + " );";
        public static final String SQL_DROP_IF_EXISTS="DROP TABLE IF EXISTS "+TABLE_NAME;

    }


}
