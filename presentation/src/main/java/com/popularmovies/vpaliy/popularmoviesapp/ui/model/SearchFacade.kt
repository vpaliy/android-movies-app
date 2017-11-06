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

    var onError:((type:SearchType, Throwable)->Unit)?=null
    var onPeople:((result: QueryResult<Actor>)->Unit)?=null
    var onSuccess:((result: QueryResult<MediaModel>)->Unit)?=null

    fun search(page:SearchPage,type: SearchType = SearchType.ALL){
        when(type){
            SearchType.ALL->{
                movieSearch.execute(Consumer(this::onMovie,this::onMovieError),page)
                tvSearch.execute(Consumer(this::onTV,this::onTVError),page)
                peopleSearch.execute(Consumer(this::onPeople,this::onPeopleError),page)
            }
            SearchType.MOVIE-> movieSearch.execute(Consumer(this::onMovie,this::onMovieError),page)
            SearchType.PEOPLE-> peopleSearch.execute(Consumer(this::onPeople,this::onPeopleError),page)
            SearchType.TV-> tvSearch.execute(Consumer(this::onTV,this::onTVError),page)
        }
    }

    private fun onMovie(response: Response<SearchPage,List<Movie>>){
        val data=movieMapper.map(response.data)
        onSuccess?.invoke(QueryResult(response.request,SearchType.MOVIE,data))
    }

    private fun onTV(response: Response<SearchPage,List<TVShow>>){
        val data=tvMapper.map(response.data)
        onSuccess?.invoke(QueryResult(response.request,SearchType.MOVIE,data))
    }

    private fun onPeople(response:Response<SearchPage, List<Actor>>){
        val result= QueryResult(response.request,SearchType.PEOPLE,response.data)
        onPeople?.invoke(result)
    }

    private fun onPeopleError(ex:Throwable){
        ex.printStackTrace()
        onError?.invoke(SearchType.PEOPLE,ex)
    }

    private fun onMovieError(ex:Throwable){
        ex.printStackTrace()
        onError?.invoke(SearchType.MOVIE,ex)
    }

    private fun onTVError(ex:Throwable){
        ex.printStackTrace()
        onError?.invoke(SearchType.TV,ex)
    }
}