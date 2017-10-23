package com.popularmovies.vpaliy.data.entity

import com.vpaliy.tmdb.model.TMDBTVDetails
import com.vpaliy.tmdb.model.TVShowModel

class TVEntity{
    var details:TMDBTVDetails?=null
    var tv:TVShowModel?=null
    var images:List<String>?=null
    var genres:List<String>?=null
}
