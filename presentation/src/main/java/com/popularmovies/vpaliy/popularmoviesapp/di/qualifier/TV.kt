package com.popularmovies.vpaliy.popularmoviesapp.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.FIELD,AnnotationTarget.VALUE_PARAMETER)
annotation class TV