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

public enum MovieUriEnum {

    MOVIES(100, MoviesContract.PATH_MOVIE, Tables.MOVIES,Movies.CONTENT_DIR_TYPE),
    MOVIE_ID(101,MoviesContract.PATH_MOVIE+"/*",Tables.MOVIES,Movies.CONTENT_ITEM_TYPE),
    ACTORS(200,MoviesContract.PATH_ACTOR,Tables.ACTORS,Actors.CONTENT_DIR_TYPE),
    ACTOR_ID(201,MoviesContract.PATH_ACTOR+"/*",Tables.ACTORS,Actors.CONTENT_ITEM_TYPE),
    GENRES(300,MoviesContract.PATH_GENRE,Tables.GENRES,Genres.CONTENT_DIR_TYPE),
    GENRE_ID(301,MoviesContract.PATH_GENRE+"/*",Tables.GENRES,Genres.CONTENT_ITEM_TYPE),
    TRAILERS(400,MoviesContract.PATH_TRAILER,Tables.TRAILERS,Trailers.CONTENT_DIR_TYPE),
    TRAILER_ID(401,MoviesContract.PATH_TRAILER+"/*",Tables.TRAILERS,Trailers.CONTENT_ITEM_TYPE),
    REVIEWS(500,MoviesContract.PATH_REVIEW,Tables.REVIEWS,Reviews.CONTENT_DIR_TYPE),
    REVIEW_ID(501,MoviesContract.PATH_REVIEW+"/*",Tables.REVIEWS,Reviews.CONTENT_ITEM_TYPE),
    POPULAR_MOVIES(600,MoviesContract.PATH_POPULAR,Tables.POPULAR,PopularMedia.CONTENT_DIR_TYPE),
    TOP_RATED_MOVIES(700,MoviesContract.PATH_TOP_RATED,Tables.TOP_RATED,TopRatedMedia.CONTENT_DIR_TYPE),
    FAVORITE_MOVIES(800,MoviesContract.PATH_FAVORITE,Tables.FAVORITE,FavoriteMedia.CONTENT_DIR_TYPE),
    UPCOMING_MOVIES(900,MoviesContract.PATH_UPCOMING,Tables.UPCOMING,UpcomingMedia.CONTENT_DIR_TYPE),
    LATEST_MOVIES(910,MoviesContract.PATH_LATEST,Tables.LATEST,LatestMedia.CONTENT_DIR_TYPE),
    RECOMMENDED_MOVIES(921,MoviesContract.PATH_RECOMMENDED,Tables.RECOMMENDED,RecommendedMedia.CONTENT_DIR_TYPE),
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
