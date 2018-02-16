package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.popularmoviesapp.R

val TYPES by lazy {
  mutableMapOf<MediaType, Int>().apply {
    put(Popular, R.string.popular_media)
    put(Top, R.string.top_rated_media)
    put(Upcoming, R.string.upcoming_media)
    put(NowPlaying, R.string.now_playing_media)
    put(Airing, R.string.today_airing_tv)
  }
}