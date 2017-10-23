package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.MovieMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule{
    @Singleton
    @Provides
    internal fun movieMapper(mapper:MovieMapper)=mapper


}
