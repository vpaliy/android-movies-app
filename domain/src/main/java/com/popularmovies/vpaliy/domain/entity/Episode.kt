package com.popularmovies.vpaliy.domain.entity

data class Episode(val id: String,
                   val name: String,
                   val overview: String,
                   val poster: String,
                   val averageVote: Double,
                   val votes: Int,
                   val number: Int)