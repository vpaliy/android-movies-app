package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage
import com.popularmovies.vpaliy.domain.interactor.utils.SimilarPage
import com.popularmovies.vpaliy.domain.repository.Repository
import com.vpaliy.tmdb.service.MovieService
import io.reactivex.Single

class MovieRepository(val service:MovieService):Repository<Movie>{

    override fun search(page: MediaPage): Single<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchBasedOn(item: Movie, page: SimilarPage): Single<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchItem(id: String): Single<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchList(page: MediaPage): Single<MediaSet<Movie>> {
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