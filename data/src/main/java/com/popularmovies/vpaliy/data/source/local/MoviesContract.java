package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

@SuppressWarnings("all")
public final class MoviesContract {

    interface MovieColumns extends BaseColumns{
        String MOVIE_ID="movie_id";
        String MOVIE_TITLE = "title";
        String MOVIE_ORIGINAL_TITLE="original_title";
        String MOVIE_TAG_LINE="tag_line";
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
        String MOVIE_ACTORS="actors";
        String MOVIE_GENRES="genres";
        String MOVIE_POSTER_URL = "poster_url";
    }


    interface ActorColumns extends BaseColumns {
        String ACTOR_ID="actor_id";
        String ACTOR_NAME="name";
        String ACTOR_BIRTHDAY="birthday";
        String ACTOR_BIOGRAPHY="biography";
        String ACTOR_HOMEPAGE="homepage";
        String ACTOR_PLACE_OF_BIRTH="place_of_birth";
        String ACTOR_POPULARITY="popularity";
        String ACTOR_IMAGE_URL="image_url";
        String ACTOR_DEATH_DAY="death_day";
    }

    interface TrailerColumns extends BaseColumns {
        String TRAILER_ID="trailer_id";
        String TRAILER_MEDIA_ID="media_id";
        String TRAILER_VIDEO_URL="video_url";
        String TRAILER_TITLE="trailer_title";
        String TRAILER_SITE="trailer_site";
    }

    interface ReviewColumns extends BaseColumns{
        String REVIEW_ID="review_id";
        String REVIEW_MEDIA_ID="media_id";
        String REVIEW_AUTHOR="author";
        String REVIEW_CONTENT="content";
        String REVIEW_URL="review_url";
    }

    interface GenreColumns extends BaseColumns {
        String GENRE_ID="genre_id";
        String GENRE_NAME="genre_name";
    }


    interface MediaCollectionColumns extends BaseColumns{
        String COLLECTION_MEDIA_ID="collection_media_id";
    }


    public static final String CONTENT_AUTHORITY="com.popularmovies.vpaliy";
    public static final String PREFIX="content://";

    public static final Uri BASE_CONTENT_URI=Uri.parse(PREFIX+CONTENT_AUTHORITY);

    public static final String PATH_MOVIE="movie";

    public static final String PATH_ALL_DETAILS="details";

    public static final String PATH_ACTOR="actor";

    public static final String PATH_TRAILER="trailer";

    public static final String PATH_REVIEW="review";

    public static final String PATH_GENRE="genre";

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

        /** Build {@link Uri} for requested {@link #MOVIE_ID}. */
        public static Uri buildMovieUri(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).build();
        }

        /**
         * Build {@link Uri} that references any {@link Trailers} associated
         * with the requested {@link #MOVIE_ID}.
         */
        public static Uri buildMovieWithTrailersUri(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).appendPath(PATH_TRAILER).build();
        }

        /**
         * Build {@link Uri} that references any {@link Reviews} associated
         * with the requested {@link #MOVIE_ID}.
         */
        public static Uri buildMovieWithReviewsUri(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).appendPath(PATH_REVIEW).build();
        }

        /**
         * Build {@link Uri} that references any {@link Genres} associated
         * with the requested {@link #MOVIE_ID}.
         */
        public static Uri buildMovieWithGenresUri(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).appendPath(PATH_GENRE).build();
        }

        /**
         * Build {@link Uri} that references any {@link Genres,Reviews,Trailers} associated
         * with the requested {@link #MOVIE_ID}.
         */
        public static Uri buildMovieWithDetailsUri(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).appendPath(PATH_ALL_DETAILS).build();
        }

        /** Read {@link #MOVIE_ID} from {@link Sessions} {@link Uri}. */
        public static String getMovieId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }

    }

    public static class Genres implements GenreColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_GENRE;

        /** Build {@link Uri} for requested {@link #GENRE_ID}. */
        public static Uri buildGenreUri(String genreId){
            return CONTENT_URI.buildUpon().appendPath(genreId).build();
        }

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #GENRE_ID}.
         */
        public static Uri buildGenreWithMoviesUri(String genresId){
            return CONTENT_URI.buildUpon().appendPath(genresId).appendPath(PATH_MOVIE).build();
        }

        /** Read {@link #GENRE_ID} from {@link Sessions} {@link Uri}. */
        public static String getGenreId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class Trailers implements TrailerColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_TRAILER;

        /** Build {@link Uri} for requested {@link #TRAILER_ID}. */
        public static Uri buildTrailerUri(String trailerId){
            return CONTENT_URI.buildUpon().appendPath(trailerId).build();
        }

        /** Read {@link #TRAILER_ID} from {@link Sessions} {@link Uri}. */
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

        /** Build {@link Uri} for requested {@link #REVIEW_ID}. */
        public static Uri buildReviewUri(String reviewUri){
            return CONTENT_URI.buildUpon().appendPath(reviewUri).build();
        }

        /** Read {@link #REVIEW_ID} from {@link Sessions} {@link Uri}. */
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

        /** Build {@link Uri} for requested {@link #ACTOR_ID}. */
        public static Uri buildActorUri(String actorUri){
            return CONTENT_URI.buildUpon().appendPath(actorUri).build();
        }

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #ACTOR_ID}.
         */
        public static Uri buildActorWithMoviesUri(String actorId){
            return CONTENT_URI.buildUpon().appendPath(actorId).appendPath(PATH_MOVIE).build();
        }

        /** Read {@link #ACTOR_ID} from {@link Sessions} {@link Uri}. */
        public static String getActorId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }

    }


    public static class PopularMedia implements MediaCollectionColumns  {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_POPULAR;

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildPopularMediaUri(String popularId){
            return CONTENT_URI.buildUpon().appendPath(popularId).build();
        }

        public static String getPopularMediaId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }


    public static class TopRatedMedia implements MediaCollectionColumns  {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_RATED).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_RATED;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_TOP_RATED;

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildTopRatedMediaUri(String topRatedId){
            return CONTENT_URI.buildUpon().appendPath(topRatedId).build();
        }

        public static String getTopRatedMediaId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class NowPlayingMedia implements MediaCollectionColumns  {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOW_PLAYING).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOW_PLAYING;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_NOW_PLAYING;

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildNowPlayingMediaUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getNowPlayingMediaUri(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class UpcomingMedia implements MediaCollectionColumns  {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_UPCOMING).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UPCOMING;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_UPCOMING;

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildUpcomingMediaUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getUpcomingMediaId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class LatestMedia implements MediaCollectionColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_LATEST).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LATEST;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_LATEST;

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildLatestMediaUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getLatestMediaId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class FavoriteMedia implements MediaCollectionColumns  {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_FAVORITE;

        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildFavoriteMediaUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getFavoriteMediaId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

    public static class RecommendedMedia implements MediaCollectionColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECOMMENDED).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECOMMENDED;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_RECOMMENDED;


        /**
         * Build {@link Uri} that references any {@link Movies} associated
         * with the requested {@link #COLLECTION_MEDIA_ID}.
         */
        public static Uri buildRecommendedMediaUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getRecommendedMediaId(Uri uri){
            return Long.toString(ContentUris.parseId(uri));
        }
    }

}
