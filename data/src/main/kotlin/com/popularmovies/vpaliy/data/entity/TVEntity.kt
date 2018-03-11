package com.popularmovies.vpaliy.data.entity

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.copy
import com.vpaliy.tmdb.model.TVDetails
import com.vpaliy.tmdb.model.TVShowModel

class TVEntity {
  var details: TVDetails? = null
  var tv: TVShowModel? = null
  var images: List<String>? = null
  var genres: List<String>? = null

  companion object {
    fun build(array: Array<TVShowModel>, genreKeeper: GenreKeeper): List<TVEntity> {
      return mutableListOf<TVEntity>().copy(array) {
        val entity = TVEntity()
        entity.tv = it
        entity.genres = genreKeeper.getGenres(it.genres_ids.toIntArray())
        entity
      }
    }
  }
}
