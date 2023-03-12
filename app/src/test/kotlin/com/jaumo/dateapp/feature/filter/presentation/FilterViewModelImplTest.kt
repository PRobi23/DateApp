package com.jaumo.dateapp.feature.filter.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.jaumo.dateapp.FilterGenerator
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.repository.FilterRepository
import com.jaumo.dateapp.features.filter.domain.usecase.GetSavedFilterUseCase
import com.jaumo.dateapp.features.filter.domain.usecase.SaveFilterUseCase
import com.jaumo.dateapp.features.filter.presentation.FilterViewModelImpl
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.util.MainDispatcherCoroutinesTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilterViewModelImplTest {

    private val filterRepository: FilterRepository = mockk()

    @get:Rule
    var coroutinesTestRule = MainDispatcherCoroutinesTestRule()

    private fun createViewModel() = FilterViewModelImpl(
        GetSavedFilterUseCase(filterRepository),
        SaveFilterUseCase(filterRepository)
    )

    @Test
    fun `when error happened in the use case the error is set to the state`() = runTest {
        // given
        every {
            filterRepository.getFilter()
        } returns flowOf(
            Response.Error<Filter>(errorType = ErrorType.UNKNOWN_ERROR)
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
            val savedFilter = Filter(gender = Gender.BOTH)
            every {
                filterRepository.getFilter()
            } returns flowOf(
                Response.Success(savedFilter)
            )

            // when
            val viewModel = createViewModel()

            // then
            viewModel.state.test {
                awaitItem()
                val state = awaitItem()

                Truth.assertThat(state.error).isNull()
                Truth.assertThat(state.filter).isEqualTo(savedFilter)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when getActuallySelectedGenderForFilter is called and the state saved item is male then male gender is returned`() {
        runTest {
            // given
            val savedFilter = Filter(gender = Gender.MALE)
            every {
                filterRepository.getFilter()
            } returns flowOf(
                Response.Success(savedFilter)
            )

            // when
            val viewModel = createViewModel()

            // then
            viewModel.state.test {
                awaitItem()
                awaitItem()

                Truth.assertThat(viewModel.getActuallySelectedGenderForFilter())
                    .isEqualTo(Gender.MALE)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when getActuallySelectedGenderForFilter is called and the state saved item is female then female gender is returned`() {
        runTest {
            // given
            val savedFilter = Filter(gender = Gender.MALE)
            every {
                filterRepository.getFilter()
            } returns flowOf(
                Response.Success(savedFilter)
            )

            // when
            val viewModel = createViewModel()

            // then
            viewModel.state.test {
                awaitItem()
                awaitItem()

                Truth.assertThat(viewModel.getActuallySelectedGenderForFilter())
                    .isEqualTo(Gender.MALE)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when getActuallySelectedGenderForFilter is called and the state saved item is not set both gender is returned`() {
        runTest {
            // given
            every {
                filterRepository.getFilter()
            } returns flowOf(
                Response.Loading()
            )

            // when
            val viewModel = createViewModel()

            // then
            viewModel.state.test {
                awaitItem()
                awaitItem()

                Truth.assertThat(viewModel.getActuallySelectedGenderForFilter())
                    .isEqualTo(Gender.BOTH)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when saveSelectedFilter is called then it called the set filter function on the repository`() {
        // given
        val filterToSave = FilterGenerator.generateFilter()
        every {
            filterRepository.getFilter()
        } returns flowOf(
            Response.Loading()
        )
        every {
            filterRepository.setFilter(filterToSave)
        } returns flowOf(Response.Success(Unit))
        val viewModel = createViewModel()

        // when
        viewModel.saveSelectedFilter(filterToSave.gender)

        // then
        verify {
            filterRepository.setFilter(filterToSave)
        }
    }
}