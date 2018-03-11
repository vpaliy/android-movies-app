package com.popularmovies.vpaliy.domain.entity

sealed class MediaType

object Popular : MediaType()
object Top : MediaType()
object Upcoming : MediaType()
object Airing : MediaType()
object NowPlaying : MediaType()