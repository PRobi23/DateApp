package com.jaumo.dateapp.features.filter.domain.usecase

import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to save filter to local storage
 */
class SaveFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {

    operator fun invoke(filter: Filter): Flow<Response<Unit>> {
        return filterRepository.setFilter(filter)
    }
}