package com.popularmovies.vpaliy.data.source.local;


import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;

import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Genres;
import static com.popularmovies.vpaliy.data.source.local.MoviesContract.Movies;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class MovieDataConverter {

    private ContentValues values;
    private MovieDetailEntity detailEntity;
    private Movie movie;
    private List<ActorEntity> actors;
    private List<TrailerEntity> trailers;
    private List<ReviewEntity> reviews;



    public MovieDataConverter appendTrailersFrom(Cursor cursor){

        return this;
    }

    public MovieDataConverter appendCastFrom(Cursor cursor){

        return this;
    }

    public MovieDataConverter appendReviewsFrom(Cursor cursor){

        return this;
    }

    public MovieDataConverter begin(Cursor cursor){
        Movie movie=new Movie();
        final int movieId=cursor.getInt(cursor.getColumnIndex(_ID));
        final String originalTitle=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_ORIGINAL_TITLE));
        final String overview=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_OVERVIEW));
        final String releaseDate=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_RELEASE_DATE));
        final String posterPath=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_POSTER_URL));
        final Number popularity=cursor.getDouble(cursor.getColumnIndex(Movies.MOVIE_POPULARITY));
        final long budget=cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_BUDGET));
        final int runtime=cursor.getInt(cursor.getColumnIndex(Movies.MOVIE_RUNTIME));
        final long revenue=cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_REVENUE));

        final String homePage=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_HOMEPAGE));
        final String title=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_TITLE));
        final double averageVote=cursor.getDouble(cursor.getColumnIndex(Movies.MOVIE_AVERAGE_VOTE));
        final long voteCount=cursor.getLong(cursor.getColumnIndex(Movies.MOVIE_VOTE_COUNT));
        final String backdropPath=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_BACKDROP_URL));
        final String jsonBackdropsString=cursor.getString(cursor.getColumnIndex(Movies.MOVIE_BACKDROPS));
        Type type = new TypeToken<ArrayList<BackdropImage>>() {}.getType();
        movie.setBackdropImages(convertFromJsonString(jsonBackdropsString,type));

        movie.setMovieId(movieId);
        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setPopularity(popularity);
        movie.setPosterPath(posterPath);
        movie.setReleaseDate(releaseDate);
        movie.setVoteCount(voteCount);
        movie.setBackdropPath(backdropPath);
        movie.setOriginalTitle(originalTitle);
        movie.setBudget(budget);
        movie.setRuntime(runtime);
        movie.setRevenue(revenue);
        movie.setHomepage(homePage);
        movie.setVoteAverage(averageVote);

        return this;
    }

    public MovieDataConverter isFavorite(Cursor cursor){
        if(movie==null) movie=new Movie();
        if(cursor!=null) {
            movie.setFavorite(cursor.getCount()!=0);
        }
        return this;
    }

    public MovieDataConverter appendGenresFrom(Cursor cursor){
        if(movie==null) movie=new Movie();

        String genreName=cursor.getString(cursor.getColumnIndex(Genres.GENRE_NAME));
        List<Genre> genres=movie.getGenres();
        if(genres==null) genres=new ArrayList<>();
        genres.add(new Genre(genreName));
        movie.setGenres(genres);
        return this;
    }

    public MovieDetailEntity merge(){
        if(movie!=null){
            detailEntity.setMovie(movie);
            detailEntity.setFavorite(movie.isFavorite());
            detailEntity.setBackdropImages(movie.getBackdropImages());
        }
        if(actors!=null) detailEntity.setCast(actors);
        if(trailers!=null) detailEntity.setTrailers(trailers);
        if(reviews!=null) detailEntity.setReviews(reviews);

        return detailEntity;
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
