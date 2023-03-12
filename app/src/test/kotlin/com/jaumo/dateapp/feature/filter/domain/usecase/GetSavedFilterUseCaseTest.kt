package com.jaumo.dateapp.feature.filter.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.jaumo.dateapp.UserGenerator
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.repository.FilterRepository
import com.jaumo.dateapp.features.filter.domain.usecase.GetSavedFilterUseCase
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetSavedFilterUseCaseTest {

    private val filterRepository: FilterRepository = mockk()
    private fun getSavedFilterUseCase() = GetSavedFilterUseCase(filterRepository)

    @Test
    fun `when the repository call fails the error is propagated through the use case`() =
        runTest {
            // given
            val useCase = getSavedFilterUseCase()
            coEvery {
                filterRepository.getFilter()
            } returns flowOf(Response.Error(ErrorType.UNKNOWN_ERROR))

            // when
            val response = useCase()

            // then
            Truth.assertThat(response.first()).isInstanceOf(Response.Error::class.java)
            response.test {
                val errorType = awaitItem()
                Truth.assertThat(errorType.errorType).isEqualTo(ErrorType.UNKNOWN_ERROR)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when the repository call succeeds the elements are propagated through the use case`() =
        runTest {
            // given
            val useCase = getSavedFilterUseCase()
            val filter = Filter(gender = Gender.BOTH)
            coEvery {
                filterRepository.getFilter()
            } returns flowOf(Response.Success(filter))

            // when
            val response = useCase()

            // then
            Truth.assertThat(response.first()).isInstanceOf(Response.Success::class.java)
            response.test {
                val filterItem = awaitItem()
                Truth.assertThat(filterItem.data).isEqualTo(filter)
                cancelAndIgnoreRemainingEvents()
            }
        }
}