package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.toStream
import com.vpaliy.tmdb.model.*
import com.vpaliy.tmdb.query.QueryBuilder
import com.vpaliy.tmdb.service.MovieService
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject
constructor(val mapper:Mapper<Movie,MovieEntity>,
            val reviewMapper:Mapper<Review,ReviewModel>,
            val roleMapper:Mapper<Role,CastModel>,
            val genreKeeper: GenreKeeper,
            val service:MovieService):MediaRepository<Movie>{

    override fun fetchItem(id: String): Stream<String, Movie> {
        return Single.zip<MovieDetails, MediaImages, MovieEntity>(
                service.getDetails(id),
                service.getImages(id),
                BiFunction{ details, images ->
                    val entity=MovieEntity()
                    entity.details=details
                    entity.genres=genreKeeper.getGenres(details.genre_ids)
                    images.backdrops?.let {
                        val list= arrayListOf<String>()
                        it.forEach { list.add(it.file_path) }
                        entity.images=list
                    }
                    entity
                }).map(mapper::map).toStream(id)
    }


    override fun fetchList(request: TypePage): Stream<TypePage, List<Movie>> {
        val query=QueryBuilder()
                .query("limit",request.limit.toString())
                .query("page", request.current.toString())
        val result=when(request.type){
            MovieType.POPULAR->service.getPopular(query.build())
            else->service.getUpcoming(query.build())
        }
        return result.map{MovieEntity.build(it.results,genreKeeper)}
                .map(mapper::map).toStream(request)
    }

    override fun fetchReviews(item: Movie): Stream<Movie, List<Review>> {
        return service.getReviews(item.id.toString())
                .map{reviewMapper.map(it.results.toList())}
                .toStream(item)
    }

    override fun fetchRoles(item: Movie): Stream<Movie, List<Role>> {
        return service.getCredits(item.id!!)
                .map{roleMapper.map(it.cast.toList())}
                .toStream(item)
    }

    override fun fetchSuggested(request: Suggestion<Movie>): Stream<Suggestion<Movie>, List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTrailers(item: Movie): Stream<Movie, List<Trailer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}