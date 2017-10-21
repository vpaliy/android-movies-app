package com.popularmovies.vpaliy.domain.entity

class Movie{
    var id:String?=null
    var title:String?=null
    var averageVote:Double?=null
    var director:String?=null
    var budget:String?=null
    var homepage:String?=null
    var revenue:String?=null
    var description:String?=null
    var releaseDate:String?=null
    var backdropImage:String?=null
    var poster:String?=null
    var backdrops:List<String>?=null
    var genres:List<String>?=null
    var releaseYear:String?=null
        private set
        get() = releaseDate
}