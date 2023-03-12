package com.jaumo.dateapp.features.filter.domain.usecase

import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get filter from local storage
 */
class GetSavedFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {

    operator fun invoke(): Flow<Response<Filter>> {
        return filterRepository.getFilter()
    }
}