package com.popularmovies.vpaliy.data.entity

import com.vpaliy.tmdb.model.TMDBMovie
import com.vpaliy.tmdb.model.TMDBMovieDetails

class MovieEntity{
    var details:TMDBMovieDetails?=null
    var movie:TMDBMovie?=null
    var images:List<String>?=null
    var genres:List<String>?=null
}