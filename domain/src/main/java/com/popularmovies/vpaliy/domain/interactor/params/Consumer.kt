package com.popularmovies.vpaliy.domain.interactor.params

data class Consumer<in T>(val success:(T)->Unit, val error:(Throwable)->Unit)