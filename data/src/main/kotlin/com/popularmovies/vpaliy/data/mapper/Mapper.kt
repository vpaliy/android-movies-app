package com.popularmovies.vpaliy.data.mapper

interface Mapper<Real, Fake> {
  fun map(list: List<Fake>): List<Real> {
    val result = arrayListOf<Real>()
    list.forEach {
      result.add(map(it))
    }
    return result
  }

  fun reverse(list: List<Real>): List<Fake> {
    val result = arrayListOf<Fake>()
    list.forEach {
      result.add(reverse(it))
    }
    return result
  }

  fun reverse(real: Real): Fake
  fun map(fake: Fake): Real
}