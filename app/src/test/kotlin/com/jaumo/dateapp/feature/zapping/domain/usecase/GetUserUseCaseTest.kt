package com.jaumo.dateapp.feature.zapping.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.jaumo.dateapp.UserGenerator
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.zapping.domain.repository.UserRepository
import com.jaumo.dateapp.features.zapping.domain.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private fun getUserUseCase() = GetUserUseCase(userRepository)

    @Test
    fun `when the repository call fails the error is propagated through the use case`() =
        runTest {
            // given
            val useCase = getUserUseCase()
            coEvery {
                userRepository.getUser()
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
            val useCase = getUserUseCase()
            val user = UserGenerator.generateUser()
            coEvery {
                userRepository.getUser()
            } returns flowOf(Response.Success(user))

            // when
            val response = useCase()

            // then
            Truth.assertThat(response.first()).isInstanceOf(Response.Success::class.java)
            response.test {
                val dogBreeds = awaitItem()
                Truth.assertThat(dogBreeds.data).isEqualTo(user)
                cancelAndIgnoreRemainingEvents()
            }
        }
}