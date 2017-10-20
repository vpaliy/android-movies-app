package com.popularmovies.vpaliy.domain.entity

data class MediaSet<out T>(val type:MovieType, val data:List<T>)