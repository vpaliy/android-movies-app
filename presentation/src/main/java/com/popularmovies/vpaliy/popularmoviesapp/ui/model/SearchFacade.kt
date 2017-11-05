package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.SearchInteractor
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage

class SearchFacade(private val movieSearch:SearchInteractor<Movie>,
                   private val peopleSearch:SearchInteractor<Actor>,
                   private val tvSearch:SearchInteractor<TVShow>,
                   private val tvMapper:Mapper<MediaModel,TVShow>,
                   private val movieMapper:Mapper<MediaModel,Movie>){

    var onMovie:((page:SearchPage, List<MediaModel>)->Unit)?=null
    var onTV:((page:SearchPage, List<MediaModel>)->Unit)?=null
    var onPeople:((page:SearchPage, List<Actor>)->Unit)?=null
    var onError:((Throwable)->Unit)?=null

    fun search(page:SearchPage,type: SearchType = SearchType.ALL){
        when(type){
            SearchType.ALL->{
                movieSearch.execute(Consumer(this::onMovieSuccess,this::onError),page)
                tvSearch.execute(Consumer(this::onTVSuccess,this::onError),page)
                peopleSearch.execute(Consumer(this::onPeopleSuccess,this::onError),page)
            }
            SearchType.MOVIE-> movieSearch.execute(Consumer(this::onMovieSuccess,this::onError),page)
            SearchType.PEOPLE-> peopleSearch.execute(Consumer(this::onPeopleSuccess,this::onError),page)
            SearchType.TV-> tvSearch.execute(Consumer(this::onTVSuccess,this::onError),page)
        }
    }

    private fun onMovieSuccess(response: Response<SearchPage,List<Movie>>){
        onMovie?.invoke(response.type,movieMapper.map(response.data))
    }

    private fun onTVSuccess(response: Response<SearchPage,List<TVShow>>){
        onTV?.invoke(response.type,tvMapper.map(response.data))
    }

    private fun onPeopleSuccess(response:Response<SearchPage, List<Actor>>){
        onPeople?.invoke(response.type,response.data)
    }

    private fun onError(ex:Throwable){
        ex.printStackTrace()
        onError?.invoke(ex)
    }
}