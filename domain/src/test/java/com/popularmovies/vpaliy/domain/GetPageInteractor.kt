package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.*

class GetPageInteractor:BaseInteractorTest(){
  @Mock lateinit var repository: MediaRepository<*>
  @InjectMocks lateinit var interactor: GetPage<*>
  private val success by lazy(LazyThreadSafetyMode.NONE) {
    onSuccess<List<*>>()
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
    whenever(repository.fetchList(any())).thenReturn(Single.just(Collections.emptyList()))
    interactor.execute(success, error, DEFAULT)

    verifyScheduler()
    verify(repository).fetchList(eq(DEFAULT))
    verify(success).invoke(any())
    verify(error, never()).invoke(any())
  }
}