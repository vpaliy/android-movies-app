package com.popularmovies.vpaliy.popularmoviesapp.ui.model

data class MediaModel(val id:String, val poster:String,
                      val title:String, val backdrop:String,
                      val release:String, val ratings:String,
                      val tags:List<String>,val isMovie:Boolean=false)