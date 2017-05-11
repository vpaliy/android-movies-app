package com.popularmovies.vpaliy.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;


public class MovieProvider extends ContentProvider {

    private static final String TAG=MovieProvider.class.getSimpleName();

    private MovieSQLHelper sqlHelper;
    private MovieUriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        if(getContext()!=null) {
            this.sqlHelper = new MovieSQLHelper(getContext());
            this.uriMatcher=new MovieUriMatcher();
        }
        return sqlHelper!=null;
    }

    private void deleteDatabase(){
        sqlHelper.close();
        Context context=getContext();
        if(context!=null){
            MovieSQLHelper.deleteDatabase(context);
            sqlHelper=new MovieSQLHelper(context);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return uriMatcher.getType(uri);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db=sqlHelper.getReadableDatabase();
        final MovieUriEnum movieUriEnum=uriMatcher.match(uri);

        Log.v(TAG, "Uri=" + uri + " code=" + movieUriEnum.code + " projection=" +
                Arrays.toString(projection) + " selection=" + selection + " args="
                + Arrays.toString(selectionArgs) + ")");

        QueryBuilder builder=buildExpandedQuery(uri,movieUriEnum);
        Cursor cursor=builder
                    .where(selection,selectionArgs)
                    .query(db,projection,sortOrder);

        Context context = getContext();
        if (null != context) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db=sqlHelper.getWritableDatabase();
        final MovieUriEnum movieUriEnum=uriMatcher.match(uri);
        final QueryBuilder builder=buildSimpleQuery(uri,movieUriEnum);

        int retVal = builder.where(selection, selectionArgs).update(db,values);
        notifyChange(uri);
        return retVal;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        if (uri == MoviesContract.BASE_CONTENT_URI) {
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }

        final SQLiteDatabase db=sqlHelper.getWritableDatabase();
        final MovieUriEnum movieUriEnum=uriMatcher.match(uri);
        final QueryBuilder builder=buildSimpleQuery(uri,movieUriEnum);

        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.v(TAG, "insert(Uri=" + uri + ", values=" + values.toString());

        final SQLiteDatabase db=sqlHelper.getWritableDatabase();
        final MovieUriEnum movieUriEnum=uriMatcher.match(uri);

        if(movieUriEnum.table!=null) {
            db.insertOrThrow(movieUriEnum.table, null, values);
            notifyChange(uri);

        }

        switch (movieUriEnum) {
            case MOVIES:
                return Movies.buildMovieUri(values.getAsString(Movies._ID));
            case ACTORS:
                return Actors.buildActorUri(values.getAsString(Actors._ID));
            case GENRES:
                return Genres.buildGenreUri(values.getAsString(Genres._ID));
            case REVIEWS:
                return Reviews.buildReviewUri(values.getAsString(Reviews._ID));
            case TRAILERS:
                return Trailers.buildTrailerUri(values.getAsString(Trailers._ID));
            case POPULAR_MOVIES:
                return PopularMedia.buildPopularMediaUri(values.getAsString(PopularMedia._ID));
            case TOP_RATED_MOVIES:
                return TopRatedMedia.buildTopRatedMediaUri(values.getAsString(TopRatedMedia._ID));
            case FAVORITE_MOVIES:
                return FavoriteMedia.buildFavoriteMediaUri(values.getAsString(FavoriteMedia._ID));
            case UPCOMING_MOVIES:
                return UpcomingMedia.buildUpcomingMediaUri(values.getAsString(UpcomingMedia._ID));
            case LATEST_MOVIES:
                return LatestMedia.buildLatestMediaUri(values.getAsString(LatestMedia._ID));
            case RECOMMENDED_MOVIES:
                return RecommendedMedia.buildRecommendedMediaUri(values.getAsString(RecommendedMedia._ID));
            case NOW_PLAYING_MOVIES:
                return NowPlayingMedia.buildNowPlayingMediaUri(values.getAsString(NowPlayingMedia._ID));
            default:
                throw new UnsupportedOperationException("Unknown uri");
        }
    }

    private void notifyChange(Uri uri) {
        if (getContext()!=null) {
            Context context = getContext();
            context.getContentResolver().notifyChange(uri, null);
        }
    }

    private QueryBuilder buildSimpleQuery(Uri uri, MovieUriEnum movieUriEnum){
        final QueryBuilder queryBuilder=new QueryBuilder();
        switch (movieUriEnum){
            case MOVIES:
            case ACTORS:
            case REVIEWS:
            case GENRES:
            case TRAILERS:
            case POPULAR_MOVIES:
            case TOP_RATED_MOVIES:
            case LATEST_MOVIES:
            case UPCOMING_MOVIES:
            case RECOMMENDED_MOVIES:
            case NOW_PLAYING_MOVIES:
            case FAVORITE_MOVIES:
                return queryBuilder.table(movieUriEnum.table);
            case MOVIE_ID:
                String movieId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIES)
                        .where(Movies._ID+"=?",movieId);
            case ACTOR_ID:
                String actorId=Actors.getActorId(uri);
                return queryBuilder.table(Tables.ACTORS)
                        .where(Actors._ID+"=?",actorId);
            case GENRE_ID:
                String genreId=Genres.getGenreId(uri);
                return queryBuilder.table(Tables.GENRES)
                        .where(Genres._ID+"=?",genreId);
            case TRAILER_ID:
                String trailerId=Trailers.getTrailerId(uri);
                return queryBuilder.table(Tables.TRAILERS)
                        .where(Trailers._ID+"=?",trailerId);
            default:
                throw new UnsupportedOperationException("Unknown uri");
        }
    }

    private QueryBuilder buildExpandedQuery(Uri uri, MovieUriEnum movieUriEnum){
        QueryBuilder builder=new QueryBuilder();
        switch (movieUriEnum){
            case MOVIES:
                return builder.table(Tables.MOVIE_JOIN_DETAILS);
            case MOVIE_ID:
                String movieId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIE_JOIN_DETAILS)
                        .where(Movies._ID+"=?",movieId);
            case ACTORS:
                return builder.table(Tables.ACTORS);
            case ACTOR_ID:
                String actorId=Actors.getActorId(uri);
                return builder.table(Tables.ACTORS)
                        .where(Actors._ID+"=?",actorId);
            case GENRES:
                return builder.table(Tables.GENRES);
            case TRAILERS:
                return builder.table(Tables.TRAILERS);
            case REVIEWS:
                return builder.table(Tables.REVIEWS);
            case POPULAR_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_POPULAR);
            case TOP_RATED_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_TOP_RATED);
            case RECOMMENDED_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_RECOMMENDED);
            case LATEST_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_LATEST);
            case NOW_PLAYING_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_NOW_PLAYING);
            case FAVORITE_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_FAVORITES);
            case UPCOMING_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_UPCOMING);
            default:
                throw new UnsupportedOperationException("Unknown uri");
        }
    }
}
