package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.toStream
import com.vpaliy.tmdb.model.TMDBMediaImages
import com.vpaliy.tmdb.model.TMDBMovieDetails
import com.vpaliy.tmdb.model.TMDBReview
import com.vpaliy.tmdb.query.QueryBuilder
import com.vpaliy.tmdb.service.MovieService
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MovieRepository(val mapper:Mapper<Movie,MovieEntity>,
                      val reviewMapper:Mapper<Review,TMDBReview>,
                      val service:MovieService):MediaRepository<Movie>{

    override fun fetchItem(id: String): Stream<String, Movie> {
        return Single.zip<TMDBMovieDetails, TMDBMediaImages, MovieEntity>(
                service.getDetails(id),
                service.getImages(id),
                BiFunction{ details, images ->
                    val entity=MovieEntity()
                    entity.details=details
                    entity
                }).map(mapper::map).toStream(id)
    }


    override fun fetchList(request: TypePage): Stream<TypePage, List<Movie>> {
        val query=QueryBuilder()
                .query("limit",request.limit.toString())
                .query("page", request.current.toString())
        val result=when(request.type){
            MovieType.POPULAR->service.getPopular(query.build())
            MovieType.TOP->service.getUpcoming(query.build())
            else->null
        }
        return result!!.map{MovieEntity.build(it.results)}
                .map(mapper::map).toStream(request)
    }

    override fun fetchReviews(item: Movie): Stream<Movie, List<Review>> {
        return service.getReviews(item.id.toString())
                .map{reviewMapper.map(it.results.toList())}
                .toStream(item)
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