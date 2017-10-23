package com.popularmovies.vpaliy.data

import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.vpaliy.tmdb.model.Genres
import com.vpaliy.tmdb.service.GenreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreKeeper @Inject
constructor(schedulerProvider: BaseSchedulerProvider, service:GenreService){

    private val genresMap=HashMap<Int,String>()

    init{
        service.getMovieGenres()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::handleGenres)
    }

    private fun handleGenres(genres:Genres?){
        genres?.let {
            it.genres.forEach {
                genresMap.put(it.id,it.name)
            }
        }
    }

    fun getGenre(id:Int)=genresMap[id]

    fun put(id:Int, genre:String)=genresMap.put(id,genre)

    fun getGenres(ids:IntArray?):List<String>{
        val result= arrayListOf<String>()
        ids?.forEach {
            genresMap[it]?.let { result.add(it) }
        }
        return result
    }
}