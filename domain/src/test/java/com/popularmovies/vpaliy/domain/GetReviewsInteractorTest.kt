package com.popularmovies.vpaliy.domain

import com.nhaarman.mockito_kotlin.*
import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.interactor.GetReviews
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.*

class GetReviewsInteractorTest : BaseInteractorTest() {
  @Mock lateinit var repository: MediaRepository<Any>
  @InjectMocks lateinit var interactor: GetReviews<Any>

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
    val list = Collections.singletonList(Review(CONTENT, URL, AUTHOR))
    whenever(repository.fetchReviews(any())).thenReturn(Single.just(list))
    interactor.execute(success, error, ID)

    verifyScheduler()
    verify(repository).fetchReviews(eq(ID))
    verify(success).invoke(any())
    verify(error, never()).invoke(any())
  }
}