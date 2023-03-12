package com.jaumo.dateapp.feature.filter.data.repository

import com.google.common.truth.Truth
import com.jaumo.dateapp.core.analytics.Analytics
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.data.local.sharedpreferences.SharedPreferencesUtils
import com.jaumo.dateapp.features.filter.data.repository.FilterRepositoryImpl
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.util.MainDispatcherCoroutinesTestRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilterRepositoryImplTest {

    private val sharedPreferencesUtils: SharedPreferencesUtils = mockk()
    private val analytics: Analytics = mockk()

    private fun createFilterRepository(dispatcher: CoroutineDispatcher) =
        FilterRepositoryImpl(sharedPreferencesUtils, analytics, dispatcher)

    @get:Rule
    var coroutinesTestRule = MainDispatcherCoroutinesTestRule()

    @Test
    fun `the initial state is loading when calling getSavedGender`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createFilterRepository(testDispatcher)
        coEvery {
            sharedPreferencesUtils.getSavedGender()
        } returns Gender.MALE

        // when
        val filterResponse = repository.getFilter().toList()
        val firstItem = filterResponse.first()

        // then
        Truth.assertThat(firstItem).isInstanceOf(Response.Loading::class.java)
    }

    @Test
    fun `when null pointer error happens during getSavedGender call then unkown error is propagated`() =
        runTest {
            // given
            val testDispatcher = UnconfinedTestDispatcher(testScheduler)
            val repository = createFilterRepository(testDispatcher)
            coEvery {
                sharedPreferencesUtils.getSavedGender()
            } throws java.lang.NullPointerException()
            every {
                analytics.logError()
            } returns Unit

            // when
            val filterResponse = repository.getFilter().toList()
            val error = filterResponse[1]

            // then
            Truth.assertThat(filterResponse.first()).isInstanceOf(Response.Loading::class.java)
            Truth.assertThat(error.errorType).isEqualTo(ErrorType.UNKNOWN_ERROR)
            verify {
                analytics.logError()
            }
        }

    @Test
    fun `when response succeeds during getSavedGender call the filter is returned`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createFilterRepository(testDispatcher)
        val expectedGender = Gender.MALE
        coEvery {
            sharedPreferencesUtils.getSavedGender()
        } returns expectedGender

        // when
        val filterResponse = repository.getFilter().toList()
        val filter = filterResponse[1]

        // then
        Truth.assertThat(filterResponse.first()).isInstanceOf(Response.Loading::class.java)
        Truth.assertThat(filter.data).isEqualTo(Filter(gender = expectedGender))
    }

    @Test
    fun `when null pointer error happens during the get saved gender then unknown error is propagated`() =
        runTest {
            // given
            val testDispatcher = UnconfinedTestDispatcher(testScheduler)
            val genderToSave = Gender.BOTH
            val repository = createFilterRepository(testDispatcher)
            coEvery {
                sharedPreferencesUtils.saveGender(genderToSave)
            } throws java.lang.NullPointerException()
            every {
                analytics.logError()
            } returns Unit

            // when
            val filterResponse = repository.getFilter().toList()
            val error = filterResponse[1]

            // then
            Truth.assertThat(filterResponse.first()).isInstanceOf(Response.Loading::class.java)
            Truth.assertThat(error.errorType).isEqualTo(ErrorType.UNKNOWN_ERROR)
            verify {
                analytics.logError()
            }
        }

    @Test
    fun `when response succeeds the filter is returned`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createFilterRepository(testDispatcher)
        val expectedGender = Gender.MALE
        val filterToSet = Filter(gender = expectedGender)

        coEvery {
            sharedPreferencesUtils.saveGender(filterToSet.gender)
        } returns Unit

        // when
        val filterResponse = repository.setFilter(filterToSet).toList()
        val filter = filterResponse.first()

        // then
        Truth.assertThat(filter.data).isEqualTo(Unit)
    }
}