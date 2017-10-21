package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.domain.entity.Movie
import com.vpaliy.tmdb.model.TMDBMovieDetails

class MovieMapper:Mapper<Movie, MovieEntity>{

    override fun map(fake: MovieEntity): Movie {
        val movie=Movie()
        val entity=fake.details?:fake.movie
        entity?.let {
            movie.id=it.id.toString()
            movie.backdropImage=it.backdrop_path
            movie.poster=it.poster_path
            movie.title=it.title
            movie.releaseDate=it.release_date
            movie.genres=fake.genres
            movie.backdrops=fake.images
            movie.averageVote=it.vote_average
            if(it is TMDBMovieDetails){
                movie.homepage=it.homepage
                movie.revenue=it.revenue.toString()
                movie.budget=it.budget.toString()
                //movie.director=it.
            }
        }
        return movie
    }

    override fun reverse(real: Movie): MovieEntity{
        val result=MovieEntity()
        val details=TMDBMovieDetails()
        result.images=real.backdrops
        result.genres=real.genres
        details.budget=real.budget?.toInt()
        details.homepage=real.homepage
        details.id=real.id?.toInt()
        details.backdrop_path=real.backdropImage
        details.poster_path=real.poster
        details.title=real.title
        details.release_date=real.releaseDate
        details.revenue=real.revenue?.toInt()
        result.details=details
        result.movie=details
        return result
    }
}