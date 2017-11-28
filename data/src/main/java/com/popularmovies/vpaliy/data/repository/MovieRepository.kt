package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.buildQuery
import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.log
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.buildBackdrop
import com.popularmovies.vpaliy.data.utils.buildPoster
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.vpaliy.tmdb.model.*
import com.vpaliy.tmdb.service.MovieService
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject
constructor(val mapper:Mapper<Movie,MovieEntity>,
            val reviewMapper:Mapper<Review,ReviewModel>,
            val trailerMapper:Mapper<Trailer,VideoModel>,
            val roleMapper:Mapper<Role,CastModel>,
            val genreKeeper: GenreKeeper,
            val service:MovieService):MediaRepository<Movie>{


  override fun fetchItem(id: String): Single<Movie> {
    return Single.zip<MovieDetails, MediaImages, MovieEntity>(
            service.getDetails(id),
            service.getImages(id),
            BiFunction{ details, images ->
              val entity=MovieEntity()
              entity.details=details
              details.poster_path= buildPoster(details.poster_path)
              entity.genres=details.genres.filterOut()
              images.backdrops?.let {
                val list= arrayListOf<String>()
                it.forEach {
                  val backdrop= buildBackdrop(it.file_path)
                  log(backdrop)
                  if(backdrop!=null){
                    list.add(backdrop)
                  }
                }
                entity.images=list
              }
              entity
            }).map(mapper::map)
  }


  override fun fetchList(request: TypePage): Single<List<Movie>> {
    val result=when(request.type){
      MediaType.POPULAR->service.getPopular(request.buildQuery())
      MediaType.TOP->service.getTopRated(request.buildQuery())
      MediaType.UPCOMING->service.getUpcoming(request.buildQuery())
      else->service.getNowPlaying(request.buildQuery())
    }
    return result
            .map{it.results.filterOut()}
            .map{MovieEntity.build(it.toTypedArray(),genreKeeper)}
            .map(mapper::map)
  }

  override fun fetchReviews(id: String): Single<List<Review>> {
    return service.getReviews(id)
            .map{reviewMapper.map(it.results.toList())}
  }

  override fun fetchRoles(id: String): Single<List<Role>> {
    return service.getCredits(id)
            .map{roleMapper.map(it.cast.filterOut())}
  }

  override fun fetchTrailers(id: String): Single<List<Trailer>> {
    return service.getVideos(id)
            .map({trailerMapper.map(it.results.toList())})
  }

  override fun fetchSuggested(request: Suggestion): Single<List<Movie>> {
    return when(request.type){
      SimilarityType.RECOMMENDATION ->
        service.getRecommendations(request.id,request.buildQuery())
      else->
        service.getSimilar(request.id,request.buildQuery())
    }.map{it.results.filterOut()}.map{MovieEntity.build(it.toTypedArray(),genreKeeper)}
            .map(mapper::map)
  }
}