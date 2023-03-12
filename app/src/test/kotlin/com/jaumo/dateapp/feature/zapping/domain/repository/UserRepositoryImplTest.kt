package com.jaumo.dateapp.feature.zapping.domain.repository

import com.google.common.truth.Truth
import com.jaumo.dateapp.UserGenerator
import com.jaumo.dateapp.core.analytics.Analytics
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.zapping.data.remote.UserApi
import com.jaumo.dateapp.features.zapping.data.repository.UserRepositoryImpl
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.User
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {
    private val userApi: UserApi = mockk()
    private val analytics: Analytics = mockk()

    private fun createUserRepository(dispatcher: CoroutineDispatcher) =
        UserRepositoryImpl(userApi, analytics, dispatcher)

    @get:Rule
    var coroutinesTestRule = MainDispatcherCoroutinesTestRule()

    @Test
    fun `the initial state is loading when calling get user`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createUserRepository(testDispatcher)
        coEvery {
            userApi.getUser()
        } returns UserGenerator.generateUserResponseDTO()

        // when
        val userResponse = repository.getUser().toList()
        val firstItem = userResponse.first()

        // then
        Truth.assertThat(firstItem).isInstanceOf(Response.Loading::class.java)
    }

    @Test
    fun `when io error happens then the response has a network error`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createUserRepository(testDispatcher)
        coEvery {
            userApi.getUser()
        } throws IOException()
        every {
            analytics.logError()
        } returns Unit

        // when
        val userResponse = repository.getUser().toList()
        val error = userResponse[1]

        // then
        Truth.assertThat(userResponse.first()).isInstanceOf(Response.Loading::class.java)
        Truth.assertThat(error.errorType).isEqualTo(ErrorType.NETWORK_ERROR)
        verify {
            analytics.logError()
        }
    }

    @Test
    fun `when http error happens then the response has an unknown error`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createUserRepository(testDispatcher)
        coEvery {
            userApi.getUser()
        } throws HttpException(
            retrofit2.Response.error<ResponseBody>(
                500,
                "some content".toResponseBody("plain/text".toMediaType())
            )
        )
        every {
            analytics.logError()
        } returns Unit

        // when
        val userResponse = repository.getUser().toList()
        val error = userResponse[1]

        // then
        Truth.assertThat(userResponse.first()).isInstanceOf(Response.Loading::class.java)
        Truth.assertThat(error.errorType).isEqualTo(ErrorType.UNKNOWN_ERROR)
        verify {
            analytics.logError()
        }
    }

    @Test
    fun `when response succeeds the user is returned`() = runTest {
        // given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val repository = createUserRepository(testDispatcher)
        coEvery {
            userApi.getUser()
        } returns UserGenerator.generateUserResponseDTO()
        val expectedUser = User(
            lastName = "Nichols",
            userPicture = "https://randomuser.me/api/portraits/men/75.jpg",
            age = 30,
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg",
            gender = Gender.FEMALE
        )

        // when
        val userResponse = repository.getUser().toList()
        val user = userResponse[1]

        // then
        Truth.assertThat(userResponse.first()).isInstanceOf(Response.Loading::class.java)
        Truth.assertThat(user.data).isEqualTo(expectedUser)
    }
}