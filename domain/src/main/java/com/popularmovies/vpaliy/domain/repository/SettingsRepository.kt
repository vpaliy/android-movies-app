package com.popularmovies.vpaliy.domain.repository

import io.reactivex.Completable

interface SettingsRepository{
  fun save():Completable
  fun remove():Completable
}
