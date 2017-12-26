package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.interactor.GetTrailers
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.*

class GetTrailersInteractorTest:BaseInteractorTest(){
  @Mock lateinit var repository:MediaRepository<*>
  @InjectMocks lateinit var interactor:GetTrailers<*>

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
    whenever(repository.fetchTrailers(any())).thenReturn(Single.just(Collections.emptyList()))
    interactor.execute(success, error,ID)

    verifyScheduler()
    verify(repository).fetchTrailers(eq(ID))
    verify(success).invoke(any())
    verify(error, never()).invoke(any())
  }
}