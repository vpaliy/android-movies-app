package com.popularmovies.vpaliy.domain.interactor.utils

data class Consumer<in T>(val success:(T)->Unit, val error:(Throwable)->Unit)