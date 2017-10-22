package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.vpaliy.tmdb.service.MovieService

class MovieRepository(val mapper:Mapper<Movie,MovieEntity>,
                      val service:MovieService):MediaRepository<Movie>{

    override fun fetchItem(id: String): Stream<String, Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchList(request: TypePage): Stream<TypePage, List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun fetchReviews(item: Movie): Stream<Movie, List<Review>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchRoles(item: Movie): Stream<Movie, List<Role>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchSuggested(request: Suggestion<Movie>): Stream<Suggestion<Movie>, List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTrailers(item: Movie): Stream<Movie, List<Trailer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}