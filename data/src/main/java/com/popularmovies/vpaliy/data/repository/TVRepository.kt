package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.buildQuery
import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.toStream
import com.vpaliy.tmdb.service.TvShowService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVRepository
@Inject constructor(val mapper: Mapper<TVShow, TVEntity>,
                    val genreKeeper: GenreKeeper,
                    val service:TvShowService):MediaRepository<TVShow>{
    override fun fetchItem(id: String): Stream<String, TVShow> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchList(request: TypePage): Stream<TypePage, List<TVShow>> {
        val result=when(request.type){
            MediaType.POPULAR->service.getPopular(request.buildQuery())
            MediaType.TOP->service.getTvOnAir(request.buildQuery())
            MediaType.UPCOMING->service.getAiringToday(request.buildQuery())
            else->service.getAiringToday(request.buildQuery())
        }
        return result
                .map{it.results.filterOut()}
                .map{TVEntity.build(it.toTypedArray(),genreKeeper)}
                .map(mapper::map)
                .toStream(request)
    }

    override fun fetchReviews(item: TVShow): Stream<TVShow, List<Review>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchRoles(item: TVShow): Stream<TVShow, List<Role>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchSuggested(request: Suggestion<TVShow>): Stream<Suggestion<TVShow>, List<TVShow>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTrailers(item: TVShow): Stream<TVShow, List<Trailer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}