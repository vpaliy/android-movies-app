package com.popularmovies.vpaliy.domain.entity

data class Movie(val id:String,
                 var title:String,
                 var averageVote:Double,
                 var director:String,
                 var budget:String,
                 var revenue:String,
                 var description:String,
                 var releaseDate:String,
                 var backdropImage:String,
                 var poster:String,
                 var releaseYear:String,
                 var backdrops:List<String>,
                 var genres:List<String>)