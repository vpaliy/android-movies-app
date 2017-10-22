package com.popularmovies.vpaliy.domain.interactor.params

data class SimpleConsumer(val success:()->Unit,val error:(Throwable)->Unit)