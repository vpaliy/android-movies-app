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
import static com.popularmovies.vpaliy.data.source.local.MovieSQLHelper.SimilarMovies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.WatchedhMedia;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MustWatchMedia;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


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
                return Movies.buildMovieUri(values.getAsString(Movies.MOVIE_ID));
            case SIMILAR_MOVIES:
                return Movies.buildMovieWithSimilarUri(values.getAsString(SimilarMovies.MEDIA_ID));
            case ACTORS:
                return Actors.buildActorUri(values.getAsString(Actors.ACTOR_ID));
            case GENRES:
                return Genres.buildGenreUri(values.getAsString(Genres.GENRE_ID));
            case REVIEWS:
                return Reviews.buildReviewUri(values.getAsString(Reviews.REVIEW_ID));
            case TRAILERS:
                return Trailers.buildTrailerUri(values.getAsString(Trailers.TRAILER_ID));
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
            case WATCHED_MOVIES:
                return WatchedhMedia.buildWatchedMediaUri(values.getAsString(WatchedhMedia._ID));
            case MUST_WATCH_MOVIES:
                return MustWatchMedia.buildMustWatchMediaUri(values.getAsString(MustWatchMedia._ID));
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
            case WATCHED_MOVIES:
            case MUST_WATCH_MOVIES:
                return queryBuilder.table(movieUriEnum.table);
            case MOVIE_ID:
                String movieId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIES)
                        .where(Movies.MOVIE_ID+"=?",movieId);
            case MOVIE_ID_TRAILERS:
                String movieTrailersId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIE_JOIN_TRAILERS)
                        .where(Movies.MOVIE_ID+"=?",movieTrailersId);
            case MOVIE_ID_GENRES:
                String movieGenresId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIES_GENRES_JOIN_MOVIES)
                        .where(Movies.MOVIE_ID+"=?",movieGenresId);
            case MOVIE_ID_REVIEWS:
                String movieReviewsId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIE_JOIN_REVIEWS)
                        .where(Movies.MOVIE_ID+"=?",movieReviewsId);
            case MOVIE_ID_ACTORS:
                String movieCastId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIES_ACTORS_JOIN_MOVIES)
                        .where(Movies.MOVIE_ID+"=?",movieCastId);
            case ACTOR_ID:
                String actorId=Actors.getActorId(uri);
                return queryBuilder.table(Tables.ACTORS)
                        .where(Actors.ACTOR_ID+"=?",actorId);
            case ACTOR_ID_MOVIES:
                String actorMoviesId=Actors.getActorId(uri);
                return queryBuilder.table(Tables.MOVIES_ACTORS_JOIN_ACTORS)
                        .where(Actors.ACTOR_ID+"=?",actorMoviesId);
            case GENRE_ID:
                String genreId=Genres.getGenreId(uri);
                return queryBuilder.table(Tables.GENRES)
                        .where(Genres.GENRE_ID+"=?",genreId);
            case REVIEW_ID:
                String reviewId=Reviews.getReviewId(uri);
                return queryBuilder.table(Tables.REVIEWS)
                        .where(Reviews.REVIEW_ID+"=?",reviewId);
            case GENRE_ID_MOVIES:
                String genreMovieId=Genres.getGenreId(uri);
                return queryBuilder.table(Tables.MOVIES_GENRES_JOIN_GENRES)
                        .where(Genres.GENRE_ID+"=?",genreMovieId);
            case TRAILER_ID:
                String trailerId=Trailers.getTrailerId(uri);
                return queryBuilder.table(Tables.TRAILERS)
                        .where(Trailers.TRAILER_ID+"=?",trailerId);
            case SIMILAR_MOVIES:
                String similarMoviesId=Movies.getMovieId(uri);
                return queryBuilder.table(Tables.MOVIE_JOIN_SIMILAR_MOVIES)
                        .where(Movies.MOVIE_ID+"=?",similarMoviesId);
            default:
                throw new UnsupportedOperationException("Unknown uri");
        }
    }

    private QueryBuilder buildExpandedQuery(Uri uri, MovieUriEnum movieUriEnum){
        QueryBuilder builder=new QueryBuilder();
        switch (movieUriEnum){
            case MOVIE_ID_TRAILERS:
                String movieWithTrailersId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIE_JOIN_TRAILERS)
                        .mapToTable(Movies.MOVIE_ID,Tables.MOVIES)
                        .mapToTable(Trailers.TRAILER_ID,Tables.TRAILERS)
                        .where(Movies.MOVIE_ID+"=?",movieWithTrailersId);
            case MOVIE_ID_GENRES:
                String movieWithGenresId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIES_GENRES_JOIN_MOVIES)
                        .mapToTable(Movies.MOVIE_ID,Tables.MOVIES)
                        .mapToTable(Genres.GENRE_ID,Tables.GENRES)
                        .where(Movies.MOVIE_ID+"=?",movieWithGenresId);
            case MOVIE_ID_REVIEWS:
                String movieWithReviewsId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIE_JOIN_REVIEWS)
                        .mapToTable(Reviews.REVIEW_ID,Tables.REVIEWS)
                        .mapToTable(Movies.MOVIE_ID,Tables.MOVIES)
                        .where(Movies.MOVIE_ID+"=?",movieWithReviewsId);
            case MOVIE_ID_ACTORS:
                String movieWithCastId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIES_ACTORS_JOIN_MOVIES)
                        .mapToTable(Movies.MOVIE_ID,Tables.MOVIES)
                        .mapToTable(Actors.ACTOR_ID,Tables.ACTORS)
                        .where(Movies.MOVIE_ID+"=?",movieWithCastId);

            case ACTOR_ID_MOVIES:
                String actorMoviesId=Actors.getActorId(uri);
                return builder.table(Tables.MOVIES_ACTORS_JOIN_ACTORS)
                        .mapToTable(Movies.MOVIE_ID,Tables.MOVIES)
                        .mapToTable(Actors.ACTOR_ID,Tables.ACTORS)
                        .where(Actors.ACTOR_ID+"=?",actorMoviesId);
            case GENRES:
                return builder.table(Tables.GENRES);
            case GENRE_ID_MOVIES:
                String genreMovieId= Genres.getGenreId(uri);
                return builder.table(Tables.MOVIES_GENRES_JOIN_GENRES)
                        .mapToTable(Movies.MOVIE_ID,Tables.MOVIES)
                        .mapToTable(Genres.GENRE_ID,Tables.GENRES)
                        .where(Genres.GENRE_ID+"=?",genreMovieId);
            case MOVIE_ID:
                String movieId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIES)
                        .where(Movies.MOVIE_ID+"=?",movieId);
            case ACTOR_ID:
                String actorId=Actors.getActorId(uri);
                return builder.table(Tables.ACTORS)
                        .where(Actors.ACTOR_ID+"=?",actorId);
            case TRAILER_ID:
                String trailerId=Trailers.getTrailerId(uri);
                return builder.table(Tables.TRAILERS)
                        .where(Trailers.TRAILER_ID+"=?",trailerId);
            case REVIEW_ID:
                String reviewId=Reviews.getReviewId(uri);
                return builder.table(Tables.REVIEWS)
                        .where(Reviews.REVIEW_ID+"=?",reviewId);
            case GENRE_ID:
                String genreId=Genres.getGenreId(uri);
                return builder.table(Tables.GENRES)
                        .where(Genres.GENRE_ID+"=?",genreId);

            case SIMILAR_MOVIES:
                String similarMoviesId=Movies.getMovieId(uri);
                return builder.table(Tables.MOVIE_JOIN_SIMILAR_MOVIES)
                        .where(Movies.MOVIE_ID+"=?",similarMoviesId);
            case MOVIES:
                return builder.table(Tables.MOVIES);
            case ACTORS:
                return builder.table(Tables.ACTORS);
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
            case MUST_WATCH_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_MUST_WATCH);
            case WATCHED_MOVIES:
                return builder.table(Tables.WATCHED);
            case UPCOMING_MOVIES:
                return builder.table(Tables.MOVIE_JOIN_UPCOMING);
            default:
                throw new UnsupportedOperationException("Unknown uri");
        }
    }
}
