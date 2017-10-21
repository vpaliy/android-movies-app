package com.popularmovies.vpaliy.domain

fun <T,Type> Type?.ifNotNull(source:(Type)->T, default:T)
    =if(this!=null) source(this) else default