package com.popularmovies.vpaliy.domain.entity

import android.text.format.DateUtils
import android.text.format.Time

class Movie {
  lateinit var id: String
  lateinit var title: String
  var averageVote: Double? = null
  var director: String? = null
  var budget: String? = null
  var homepage: String? = null
  var revenue: String? = null
  var description: String? = null
  var releaseDate: String? = null
  var backdropImage: String? = null
  var poster: String? = null
  var backdrops: List<String>? = null
  var genres: List<String>? = null
  var releaseYear: String? = null
    private set
    get() {
      if (releaseDate.isNullOrEmpty()) return null
      val time = Time()
      time.parse3339(releaseDate)
      return DateUtils.getRelativeTimeSpanString(time.toMillis(false),
          System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
          DateUtils.FORMAT_ABBREV_ALL).toString()
    }
}