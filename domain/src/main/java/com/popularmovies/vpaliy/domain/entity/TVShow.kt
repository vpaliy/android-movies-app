package com.popularmovies.vpaliy.domain.entity

class TVShow {
  lateinit var id: String
  lateinit var title: String
  var averageVote: Double? = null
  var director: String? = null
  var budget: String? = null
  var revenue: String? = null
  var description: String? = null
  var lastAirTime: String? = null
  var firstAirTime: String? = null
  var backdropImage: String? = null
  var poster: String? = null
  var genres: List<String>? = null
  var images: List<String>? = null
  var isWatched = false
  var isMust = false
  var isFavorite = false
  var releaseYear: String? = null        //TODO extract year from first air time
    private set
    get() = firstAirTime
}