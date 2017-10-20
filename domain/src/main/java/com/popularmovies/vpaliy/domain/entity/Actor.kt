package com.popularmovies.vpaliy.domain.entity

data class Actor(val id:String,
                 val birthday:String,
                 val deathday:String,
                 val bio:String,
                 val avatarPath:String,
                 val name:String,
                 val images:List<String>)