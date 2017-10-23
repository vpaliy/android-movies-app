package com.popularmovies.vpaliy.data.entity

import com.vpaliy.tmdb.model.TVDetails
import com.vpaliy.tmdb.model.TVShowModel

class TVEntity{
    var details:TVDetails?=null
    var tv:TVShowModel?=null
    var images:List<String>?=null
    var genres:List<String>?=null
}
