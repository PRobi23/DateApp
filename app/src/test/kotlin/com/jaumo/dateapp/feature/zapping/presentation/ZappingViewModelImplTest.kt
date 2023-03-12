package com.jaumo.dateapp.feature.zapping.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.jaumo.dateapp.UserGenerator
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.repository.FilterRepository
import com.jaumo.dateapp.features.filter.domain.usecase.GetSavedFilterUseCase
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.features.zapping.domain.repository.UserRepository
import com.jaumo.dateapp.features.zapping.domain.usecase.GetUserUseCase
import com.jaumo.dateapp.features.zapping.presentation.ZappingViewModelImpl
import com.jaumo.dateapp.util.MainDispatcherCoroutinesTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ZappingViewModelImplTest {
    private val userRepository: UserRepository = mockk()
    private val filterRepository: FilterRepository = mockk()

    @get:Rule
    var coroutinesTestRule = MainDispatcherCoroutinesTestRule()

    private fun createViewModel() = ZappingViewModelImpl(
        GetUserUseCase(userRepository),
        GetSavedFilterUseCase(filterRepository)
    )

    @Test
    fun `when repository call of getUser tells that there is a network error the state is set to state`() =
        runTest {
            // given
            val savedGender = Gender.BOTH
            every {
                filterRepository.getFilter()
            } returns flowOf(Response.Success(Filter(gender = savedGender)))
            every {
                userRepository.getUser(savedGender)
            } returns flowOf(
                Response.Error<User>(errorType = ErrorType.NETWORK_ERROR)
            )

            val viewModel = createViewModel()

            // when
            viewModel.state.test {
                this.awaitItem()
                val state = this.awaitItem()

                // then
                Truth.assertThat(state.error).isEqualTo(ErrorType.NETWORK_ERROR)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when error happened in the use case the error is set to the state`() = runTest {
        // given
        val savedGender = Gender.FEMALE
        every {
            filterRepository.getFilter()
        } returns flowOf(Response.Success(Filter(gender = savedGender)))
        every {
            userRepository.getUser(savedGender)
        } returns flowOf(
            Response.Error<User>(errorType = ErrorType.UNKNOWN_ERROR)
        )

        // when
        val viewModel = createViewModel()

        // then
        viewModel.state.test {
            awaitItem()
            val state = awaitItem()

            Truth.assertThat(state.error).isEqualTo(ErrorType.UNKNOWN_ERROR)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when use case call succeeds in the use case the elements are set to the state`() =
        runTest {
            // given
            val generatedUser = UserGenerator.generateUser()
            val savedGender = Gender.MALE
            every {
                filterRepository.getFilter()
            } returns flowOf(Response.Success(Filter(gender = savedGender)))
            every {
                userRepository.getUser(savedGender)
            } returns flowOf(
                Response.Success(generatedUser)
            )

            // when
            val viewModel = createViewModel()

            // then
            viewModel.state.test {
                awaitItem()
                val state = awaitItem()

                Truth.assertThat(state.error).isNull()
                Truth.assertThat(state.user).isEqualTo(generatedUser)
                cancelAndIgnoreRemainingEvents()
            }
        }
}