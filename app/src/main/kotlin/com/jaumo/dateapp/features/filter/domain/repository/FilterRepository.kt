package com.jaumo.dateapp.features.filter.domain.repository

import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import kotlinx.coroutines.flow.Flow

/***
 * Filter repository interface. This interface is responsible for getting filter related values.
 */
interface FilterRepository {

    /**
     * Get filter from local storage. First loading state is emitted and after that if the local call succeeds the
     * saved filter is emitted if it fails then error is propagated.
     * @return Single filter model from local
     */
    fun getFilter(): Flow<Response<Filter>>

    /**
     * Set filter to local storage.
     */
    fun setFilter(filter: Filter): Flow<Response<Unit>>
}