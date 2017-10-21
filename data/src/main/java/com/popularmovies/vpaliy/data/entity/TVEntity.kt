package com.popularmovies.vpaliy.data.entity

import com.vpaliy.tmdb.model.TMDBTVDetails
import com.vpaliy.tmdb.model.TMDBTvShow

class TVEntity{
    var details:TMDBTVDetails?=null
    var tv:TMDBTvShow?=null
    var images:List<String>?=null
    var genres:List<String>?=null
}
