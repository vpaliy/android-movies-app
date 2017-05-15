package com.popularmovies.vpaliy.data.source.local;

import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.PopularMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.LatestMedia;
import static com.popularmovies.vpaliy.data.source.local.MovieSQLHelper.Tables;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Actors;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Genres;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Trailers;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Reviews;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.FavoriteMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.TopRatedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.UpcomingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.RecommendedMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.NowPlayingMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.WatchedhMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MustWatchMedia;

enum MovieUriEnum {

    MOVIES(100, MoviesContract.PATH_MOVIE, Tables.MOVIES,Movies.CONTENT_DIR_TYPE),
    MOVIE_ID(101,MoviesContract.PATH_MOVIE+"/#",null,Movies.CONTENT_ITEM_TYPE),
    MOVIE_ID_TRAILERS(102,MoviesContract.PATH_MOVIE+"/*"+MoviesContract.PATH_TRAILER,null,Movies.CONTENT_ITEM_TYPE),
    MOVIE_ID_REVIEWS(103,MoviesContract.PATH_MOVIE+"/*"+MoviesContract.PATH_REVIEW,null,Movies.CONTENT_ITEM_TYPE),
    MOVIE_ID_GENRES(105,MoviesContract.PATH_MOVIE+"/*"+MoviesContract.PATH_GENRE,Tables.MEDIA_GENRES,Movies.CONTENT_ITEM_TYPE),
    MOVIE_ID_ACTORS(115,MoviesContract.PATH_MOVIE+"/*"+MoviesContract.PATH_ACTOR,Tables.MEDIA_ACTORS,Movies.CONTENT_ITEM_TYPE),

    ACTORS(200,MoviesContract.PATH_ACTOR,Tables.ACTORS,Actors.CONTENT_DIR_TYPE),
    ACTOR_ID(201,MoviesContract.PATH_ACTOR+"/#",null,Actors.CONTENT_ITEM_TYPE),
    ACTOR_ID_MOVIES(202,MoviesContract.PATH_ACTOR+"/*"+MoviesContract.PATH_MOVIE,Tables.MEDIA_ACTORS,Actors.CONTENT_ITEM_TYPE),

    GENRES(300,MoviesContract.PATH_GENRE,Tables.GENRES,Genres.CONTENT_DIR_TYPE),
    GENRE_ID(301,MoviesContract.PATH_GENRE+"/#",null,Genres.CONTENT_ITEM_TYPE),
    GENRE_ID_MOVIES(302,MoviesContract.PATH_GENRE+"/#"+MoviesContract.PATH_MOVIE,Tables.MEDIA_GENRES, Genres.CONTENT_ITEM_TYPE),

    TRAILERS(400,MoviesContract.PATH_TRAILER,Tables.TRAILERS,Trailers.CONTENT_DIR_TYPE),
    TRAILER_ID(401,MoviesContract.PATH_TRAILER+"/#",null,Trailers.CONTENT_ITEM_TYPE),
    REVIEWS(500,MoviesContract.PATH_REVIEW,Tables.REVIEWS,Reviews.CONTENT_DIR_TYPE),
    REVIEW_ID(501,MoviesContract.PATH_REVIEW+"/#",null,Reviews.CONTENT_ITEM_TYPE),

    SIMILAR_MOVIES(605,MoviesContract.PATH_MOVIE+"/*"+MoviesContract.PATH_SIMILAR,Tables.SIMILAR_MEDIA,Movies.CONTENT_ITEM_TYPE),
    POPULAR_MOVIES(600,MoviesContract.PATH_POPULAR,Tables.POPULAR,PopularMedia.CONTENT_DIR_TYPE),
    TOP_RATED_MOVIES(700,MoviesContract.PATH_TOP_RATED,Tables.TOP_RATED,TopRatedMedia.CONTENT_DIR_TYPE),
    FAVORITE_MOVIES(800,MoviesContract.PATH_FAVORITE,Tables.FAVORITE,FavoriteMedia.CONTENT_DIR_TYPE),
    UPCOMING_MOVIES(900,MoviesContract.PATH_UPCOMING,Tables.UPCOMING,UpcomingMedia.CONTENT_DIR_TYPE),
    LATEST_MOVIES(910,MoviesContract.PATH_LATEST,Tables.LATEST,LatestMedia.CONTENT_DIR_TYPE),
    RECOMMENDED_MOVIES(921,MoviesContract.PATH_RECOMMENDED,Tables.RECOMMENDED,RecommendedMedia.CONTENT_DIR_TYPE),
    WATCHED_MOVIES(931,MoviesContract.PATH_WATCHED,Tables.WATCHED,WatchedhMedia.CONTENT_DIR_TYPE),
    MUST_WATCH_MOVIES(935,MoviesContract.PATH_MUST_WATCH,Tables.MUST_WATCH,MustWatchMedia.CONTENT_DIR_TYPE),
    NOW_PLAYING_MOVIES(942,MoviesContract.PATH_NOW_PLAYING,Tables.NOW_PLAYING,NowPlayingMedia.CONTENT_DIR_TYPE);


    public int code;
    public String contentType;
    public String table;
    public String path;

    MovieUriEnum(int code, String path, String table, String contentType){
        this.code=code;
        this.path=path;
        this.table=table;
        this.contentType=contentType;
    }
}
