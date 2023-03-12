package com.jaumo.dateapp.features.filter.data.repository

import com.jaumo.dateapp.core.analytics.Analytics
import com.jaumo.dateapp.core.di.DefaultDispatcher
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.data.local.sharedpreferences.SharedPreferencesUtils
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.repository.FilterRepository
import com.jaumo.dateapp.features.zapping.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

/**
 * {@inheritDoc}
 */
class FilterRepositoryImpl(
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    private val analytics: Analytics,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : FilterRepository {

    /**
     * {@inheritDoc}
     */
    override fun getFilter(): Flow<Response<Filter>> = flow {
        emit(Response.Loading())

        try {
            val genderFromSharedPreferences = sharedPreferencesUtils.getSavedGender()
            emit(Response.Success<Filter>(Filter(gender = genderFromSharedPreferences)))
        } catch (e: Exception) {
            emit(Response.Error<Filter>(ErrorType.UNKNOWN_ERROR))
            analytics.logError()
        }
    }.flowOn(defaultDispatcher)

    /**
     * {@inheritDoc}
     */
    override fun setFilter(filter: Filter): Flow<Response<Unit>> = flow {
        try {
            sharedPreferencesUtils.saveGender(filter.gender)
            emit(Response.Success<Unit>(Unit))
        } catch (e: Exception) {
            emit(Response.Error<Unit>(ErrorType.UNKNOWN_ERROR))
            analytics.logError()
        }
    }.flowOn(defaultDispatcher)
}