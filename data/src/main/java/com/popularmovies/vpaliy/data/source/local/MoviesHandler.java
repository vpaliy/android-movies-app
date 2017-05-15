package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.TrafficStats;
import android.net.Uri;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Trailers;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Reviews;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Actors;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.MediaCollectionColumns;

public class MoviesHandler {

    private Movie movie;
    private MovieDetailEntity detailEntity;
    private ContentResolver contentResolver;

    private MoviesHandler(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }


    public static MoviesHandler start(ContentResolver contentResolver){
        return new MoviesHandler(contentResolver);
    }

    public MoviesHandler insert(Movie movie){
        ContentValues values=convertMovieToValues(movie);
        if(values!=null){
            contentResolver.insert(Movies.CONTENT_URI,values);
        }
        return this;
    }

    public MoviesHandler insertInCollection(Uri uri, Movie movie){
        ContentValues values=new ContentValues();
        values.put(MediaCollectionColumns.COLLECTION_MEDIA_ID,movie.getMovieId());
        contentResolver.insert(uri,values);
        return this;
    }

    public MoviesHandler insertDetails(MovieDetailEntity detailEntity){
        if(detailEntity!=null){
            insert(detailEntity.getMovie())
                    .insertTrailers(detailEntity.getTrailers(),detailEntity.getMovieId())
                    .insertSimilarMovies(detailEntity.getSimilarMovies(),detailEntity.getMovieId())
                    .insertActors(detailEntity.getCast(),detailEntity.getMovieId())
                    .insertGenres(detailEntity.getGenres(),detailEntity.getMovieId());

        }
        return this;
    }

    public MoviesHandler insertTrailers(List<TrailerEntity> trailers, int movieId){
        if(trailers!=null){
            for(TrailerEntity trailer:trailers){
                //in case there is not id
                trailer.setMovieId(movieId);
                ContentValues values=convertTrailerToValues(trailer);
                contentResolver.insert(Trailers.CONTENT_URI,values);
            }
        }
        return this;
    }

    public MoviesHandler insertGenres(List<Genre> genres, int movieId){
        if(genres==null||!genres.isEmpty()) return this;
        Uri uri=Movies.buildMovieWithGenresUri(Integer.toString(movieId));
        for(Genre genre:genres){
            ContentValues values=new ContentValues();
            values.put(MovieSQLHelper.MediaGenres.MEDIA_ID,movieId);
            values.put(MovieSQLHelper.MediaGenres.GENRE_ID,genre.getId());
            contentResolver.insert(uri,values);
        }
        return this;
    }

    public MoviesHandler insertActors(List<ActorEntity> actors, int movieId){
        if(actors==null||actors.isEmpty()) return this;
        Uri uri=Movies.buildMovieWithActorsUri(Integer.toString(movieId));
        for(ActorEntity actorEntity:actors){
            ContentValues values=new ContentValues();
            values.put(MovieSQLHelper.MediaActors.MEDIA_ID,movieId);
            values.put(MovieSQLHelper.MediaActors.ACTOR_ID,actorEntity.getActorId());
            contentResolver.insert(uri,values);
        }
        return this;
    }

    public MoviesHandler insertSimilarMovies(List<Movie> similarMovies, int movieId){
        if(similarMovies==null||similarMovies.isEmpty()) return null;
        Uri uri=Movies.buildMovieWithSimilarUri(Integer.toString(movieId));
        for(Movie movie:similarMovies){
            ContentValues values=new ContentValues();
            values.put(MovieSQLHelper.SimilarMovies.MEDIA_ID,movieId);
            values.put(MovieSQLHelper.SimilarMovies.SIMILAR_MEDIA_ID,movie.getMovieId());
            contentResolver.insert(uri,values);
        }
        return this;
    }

    public List<Movie> queryAll(Uri uri){
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if(cursor!=null){
            List<Movie> movies=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                Movie movie=convertToMovie(cursor);
                if(movie!=null) movies.add(movie);
            }
            if(cursor.isClosed()) cursor.close();
            return movies;
        }
        return null;
    }

    public MoviesHandler queryById(int movieId){
        Uri uri=Movies.buildMovieUri(Integer.toString(movieId));
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        movie=convertToMovie(cursor);
        if(cursor!=null) cursor.close();
        return this;
    }



    public MoviesHandler appendCast(int movieId){
        Uri uri=Movies.buildMovieWithActorsUri(Integer.toString(movieId));
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if(cursor!=null){
            List<ActorEntity> actorList=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                ActorEntity actor=new ActorEntity();
                actor.setActorId(cursor.getInt(cursor.getColumnIndex(Actors.ACTOR_ID)));
                actor.setName(cursor.getString(cursor.getColumnIndex(Actors.ACTOR_NAME)));
                actor.setActorAvatar(cursor.getString(cursor.getColumnIndex(Actors.ACTOR_IMAGE_URL)));
                actor.setMovieId(movieId);
            }
            if(!cursor.isClosed()) cursor.close();

            if(!actorList.isEmpty()){
                if(detailEntity==null) detailEntity=new MovieDetailEntity();
                detailEntity.setCast(actorList);
            }
        }
        return this;
    }

    public MoviesHandler appendReviews(int movieId){
        Uri uri=Movies.buildMovieWithReviewsUri(Integer.toString(movieId));
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if(cursor!=null){
            List<ReviewEntity>  reviewList=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                ReviewEntity review=new ReviewEntity();
                review.setReviewId(cursor.getString(cursor.getColumnIndex(Reviews.REVIEW_ID)));
                review.setAuthor(cursor.getString(cursor.getColumnIndex(Reviews.REVIEW_AUTHOR)));
                review.setContent(cursor.getString(cursor.getColumnIndex(Reviews.REVIEW_CONTENT)));
                review.setUrl(cursor.getString(cursor.getColumnIndex(Reviews.REVIEW_URL)));
                reviewList.add(review);
            }
            if(!cursor.isClosed()) cursor.close();

            if(!reviewList.isEmpty()){
                if(detailEntity==null) detailEntity=new MovieDetailEntity();
                detailEntity.setReviews(reviewList);
            }
        }
        return this;
    }

    public MoviesHandler appendTrailers(int movieId){
        Uri uri=Movies.buildMovieWithTrailersUri(Integer.toString(movieId));
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if(cursor!=null){
            List<TrailerEntity> trailerList=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                TrailerEntity trailer=new TrailerEntity();
                trailer.setMovieId(movieId);
                trailer.setTrailerUrl(cursor.getString(cursor.getColumnIndex(Trailers.TRAILER_VIDEO_URL)));
                trailer.setTrailerTitle(cursor.getString(cursor.getColumnIndex(Trailers.TRAILER_TITLE)));
                trailer.setTrailerId(cursor.getInt(cursor.getColumnIndex(Trailers.TRAILER_ID)));
                trailer.setSite(cursor.getString(cursor.getColumnIndex(Trailers.TRAILER_SITE)));
                trailerList.add(trailer);
            }

            if(!cursor.isClosed()) cursor.close();

            if(!trailerList.isEmpty()){
                if(detailEntity==null) detailEntity=new MovieDetailEntity();
                detailEntity.setTrailers(trailerList);
            }
        }
        return this;
    }

    public MovieDetailEntity buildDetails(){
        return detailEntity;
    }

    public Movie build(int movieId){
        if(movie==null) queryById(movieId);
        return movie;
    }

    @VisibleForTesting
    public Movie convertToMovie(Cursor cursor){
        if(cursor==null) return null;
        Movie movie=new Movie();
        movie.setMovieId(cursor.getInt(cursor.getColumnIndex(Movies.MOVIE_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_TITLE)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_OVERVIEW)));
        movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_ORIGINAL_TITLE)));
        movie.setBudget(cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_BUDGET)));
        movie.setRevenue(cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_REVENUE)));
        movie.setHomepage(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_HOMEPAGE)));
        movie.setVoteCount(cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_VOTE_COUNT)));
        movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndex(Movies.MOVIE_AVERAGE_VOTE)));
        movie.setStatus(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_STATUS)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_RELEASE_DATE)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(Movies.MOVIE_POSTER_URL)));
        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(Movies.MOVIE_POPULARITY)));
        movie.setRuntime(cursor.getInt(cursor.getColumnIndex(Movies.MOVIE_RUNTIME)));
        String jsonString=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_BACKDROPS));
        Type type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        movie.setBackdropImages(convertFromJsonString(jsonString,type));
        jsonString=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_GENRES));
        type=new TypeToken<ArrayList<Genre>>(){}.getType();
        movie.setGenres(convertFromJsonString(jsonString,type));
        return movie;
    }

    @VisibleForTesting
    public ContentValues convertMovieToValues(Movie movie){
        if(movie==null) return null;
        ContentValues values=new ContentValues();
        values.put(Movies.MOVIE_ID,movie.getMovieId());
        values.put(Movies.MOVIE_TITLE,movie.getTitle());
        values.put(Movies.MOVIE_ORIGINAL_TITLE,movie.getOriginalTitle());
        values.put(Movies.MOVIE_AVERAGE_VOTE,movie.getVoteAverage());
        values.put(Movies.MOVIE_BUDGET,movie.getBudget());
        values.put(Movies.MOVIE_REVENUE,movie.getRevenue());
        values.put(Movies.MOVIE_OVERVIEW,movie.getOverview());
        values.put(Movies.MOVIE_HOMEPAGE,movie.getHomepage());
        values.put(Movies.MOVIE_RUNTIME,movie.getRuntime());
        values.put(Movies.MOVIE_VOTE_COUNT,movie.getVoteCount());
        values.put(Movies.MOVIE_POPULARITY,movie.getPopularity());
        values.put(Movies.MOVIE_POSTER_URL,movie.getPosterPath());
        values.put(Movies.MOVIE_RELEASE_DATE,movie.getReleaseDate());
        values.put(Movies.MOVIE_STATUS,movie.getStatus());
        Type type=new TypeToken<ArrayList<BackdropImage>>(){}.getType();
        String jsonString=convertToJsonString(movie.getBackdropImages(),type);
        values.put(Movies.MOVIE_BACKDROPS,jsonString);
        type=new TypeToken<ArrayList<Genre>>(){}.getType();
        jsonString=convertToJsonString(movie.getGenres(),type);
        values.put(Movies.MOVIE_GENRES,jsonString);
        return values;
    }

    @VisibleForTesting
    public ContentValues convertTrailerToValues(TrailerEntity trailer){
        if(trailer==null) return null;
        ContentValues values=new ContentValues();
        values.put(Trailers.TRAILER_ID,trailer.getTrailerId());
        values.put(Trailers.TRAILER_MEDIA_ID,trailer.getMovieId());
        values.put(Trailers.TRAILER_TITLE,trailer.getTrailerTitle());
        values.put(Trailers.TRAILER_VIDEO_URL,trailer.getTrailerUrl());
        values.put(Trailers.TRAILER_SITE,trailer.getSite());
        return values;
    }

    public static String convertToJsonString(Object object, Type type){
        if(object==null) return null;
        Gson gson=new Gson();
        return gson.toJson(object,type);
    }

    public static <T> T convertFromJsonString(String jsonString, Type type){
        if(jsonString==null) return null;
        Gson gson=new Gson();
        return gson.fromJson(jsonString,type);
    }
}
