package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.repository.Repository
import com.vpaliy.tmdb.service.MovieService
import rx.Single

class MovieRepository(private val service:MovieService):Repository<Movie>{

    override fun fetchItem(type: MovieType): Single<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchList(type: MovieType): Single<MediaSet<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchBasedOn(item: Movie, type: SimilarityType): Single<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchReviews(item: Movie): Single<List<Review>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchRoles(item: Movie): Single<List<Role>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTrailers(item: Movie): Single<List<Trailer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}