package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Ignore

@RunWith(MockitoJUnitRunner::class)
@Ignore
open class BaseInteractorTest {
  @Mock lateinit var scheduler: BaseScheduler
  internal var error: (Throwable) -> Unit = mock {
    on(it.invoke(any())).thenReturn(Unit)
  }

  @Before
  fun setUp() {
    whenever(scheduler.io()).thenReturn(Schedulers.trampoline())
    whenever(scheduler.ui()).thenReturn(Schedulers.trampoline())
  }

  fun verifyScheduler() {
    verify(scheduler).io()
    verify(scheduler).ui()
  }

  inline fun <reified R : Any> onSuccess() = mock<((R) -> Unit)> {
    on(it.invoke(any())).thenReturn(Unit)
  }
}