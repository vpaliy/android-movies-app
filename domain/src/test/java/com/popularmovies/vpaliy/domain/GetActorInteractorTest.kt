package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.interactor.GetActor
import com.popularmovies.vpaliy.domain.repository.Repository
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

class GetActorInteractorTest : BaseInteractorTest() {
  @Mock lateinit var repository: Repository
  @InjectMocks lateinit var interactor: GetActor
  private val success by lazy(LazyThreadSafetyMode.NONE) {
    onSuccess<Actor>()
  }

  @Test
  fun testExecute() {
    interactor.execute(success, error)

    verifyScheduler()
    verify(success, never()).invoke(any())
    verify(error).invoke(any())
  }

  @Test
  fun testExecuteWithWrongArgument() {
    whenever(repository.fetchActor(any())).thenReturn(Single.just(Actor()))
    interactor.execute(success, error, ID)

    verifyScheduler()
    verify(repository).fetchActor(eq(ID))
    verify(success).invoke(any())
    verify(error, never()).invoke(any())
  }
}