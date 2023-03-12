package com.jaumo.dateapp.features.filter.presentation

import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.features.filter.domain.model.Filter

/***
 * Class that holds the state of the filter
 */
data class FilterState(
    val isLoading: Boolean = false,
    val filter: Filter? = null,
    val error: ErrorType? = null
)