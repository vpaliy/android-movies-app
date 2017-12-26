package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.interactor.SearchInteractor
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.*

class SearchInteractorTest:BaseInteractorTest(){
  @Mock lateinit var repository:SearchRepository<*>
  @InjectMocks lateinit var interactor:SearchInteractor<*>

  private val success by lazy(LazyThreadSafetyMode.NONE){
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
    whenever(repository.search(any())).thenReturn(Single.just(Collections.emptyList()))
    interactor.execute(success, error, SEARCH_DEFAULT)

    verifyScheduler()
    verify(repository).search(eq(SEARCH_DEFAULT))
    verify(success).invoke(any())
    verify(error, never()).invoke(any())
  }
}