package com.popularmovies.vpaliy.data.entity

import com.popularmovies.vpaliy.data.copy
import com.vpaliy.tmdb.model.TMDBMovie
import com.vpaliy.tmdb.model.TMDBMovieDetails

class MovieEntity{
    var details:TMDBMovieDetails?=null
    var movie:TMDBMovie?=null
    var images:List<String>?=null
    var genres:List<String>?=null

    companion object {
        fun build(array:Array<TMDBMovie>):List<MovieEntity>{
            return mutableListOf<MovieEntity>().copy(array,{
                val entity=MovieEntity()
                entity.movie=it
                entity
            })
        }
    }
}