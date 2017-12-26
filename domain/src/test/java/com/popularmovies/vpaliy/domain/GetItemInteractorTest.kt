package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.interactor.GetMediaItem
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

class GetItemInteractorTest : BaseInteractorTest() {
  @Mock lateinit var repository: MediaRepository<Any>
  @InjectMocks lateinit var interactor: GetMediaItem<Any>
  private val success by lazy(LazyThreadSafetyMode.NONE) {
    onSuccess<Any>()
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
    whenever(repository.fetchItem(any())).thenReturn(Single.just(Any()))
    interactor.execute(success, error, ID)

    verifyScheduler()
    verify(repository).fetchItem(eq(ID))
    verify(success).invoke(any())
    verify(error, never()).invoke(any())
  }
}