package com.jaumo.dateapp.features.zapping.data.repository

import com.jaumo.dateapp.core.analytics.Analytics
import com.jaumo.dateapp.core.di.DefaultDispatcher
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.zapping.data.remote.UserApi
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.features.zapping.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

/**
 * {@inheritDoc}
 */
class UserRepositoryImpl(
    private val userApi: UserApi,
    private val analytics: Analytics,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : UserRepository {

    /**
     * {@inheritDoc}
     */
    override fun getUser(): Flow<Response<User>> = flow {
        emit(Response.Loading())

        try {
            val userDTO = userApi.getUser()
            val user = userDTO.results.map {
                it.toUser()
            }.first()
            emit(Response.Success<User>(user))

        } catch (e: HttpException) {
            emit(Response.Error<User>(ErrorType.UNKNOWN_ERROR))
            analytics.logError()
        } catch (e: IOException) {
            emit(Response.Error<User>(ErrorType.NETWORK_ERROR))
            analytics.logError()
        } catch (e: Exception) {
            emit(Response.Error<User>(ErrorType.UNKNOWN_ERROR))
            analytics.logError()
        }
    }.flowOn(defaultDispatcher)
}