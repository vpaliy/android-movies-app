package com.popularmovies.vpaliy.data.utils

import com.vpaliy.tmdb.model.ActorModel
import com.vpaliy.tmdb.model.MovieModel

const val BASE_MOVIE_URL = "http://image.tmdb.org/t/p/"
const val IMAGE_SIZE_W185 = "w185/"
const val IMAGE_SIZE_W342 = "w342/"
const val IMAGE_SIZE_W500 = "w500/"
const val IMAGE_SIZE_W780 = "w780/"

fun buildPoster(path: String?)=path add(BASE_MOVIE_URL+ IMAGE_SIZE_W185)

fun buildBackdrop(path:String?)=path add(BASE_MOVIE_URL+ IMAGE_SIZE_W780)

@JvmName("filterActors")
fun Array<ActorModel>.filterOut():List<ActorModel>{
    forEach {
        it.profile_path= buildPoster(it.profile_path)
    }
    return this.toList()
}

@JvmName("filterMovies")
fun Array<MovieModel>.filterOut():List<MovieModel>{
    forEach {
        it.backdrop_path= buildBackdrop(it.backdrop_path)
        it.poster_path= buildPoster(it.poster_path)
    }
    return this.toList()
}

private infix fun String?.add(path:String):String?{
    if(this!=null){
        return path+this
    }
    return null
}