package com.popularmovies.vpaliy.data.entity

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.copy
import com.vpaliy.tmdb.model.MovieDetails
import com.vpaliy.tmdb.model.MovieModel

class MovieEntity{
  var details:MovieDetails?=null
  var movie:MovieModel?=null
  var images:List<String>?=null
  var genres:List<String>?=null

  companion object {
    fun build(array:Array<MovieModel>,genreKeeper: GenreKeeper):List<MovieEntity>{
      return mutableListOf<MovieEntity>().copy(array,{
        val entity=MovieEntity()
        entity.movie=it
        entity.genres=genreKeeper.getGenres(it.genre_ids)
        entity
      })
    }
  }
}