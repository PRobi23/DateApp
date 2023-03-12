package com.jaumo.dateapp.features.filter.presentation

import com.jaumo.dateapp.features.zapping.domain.model.Gender
import kotlinx.coroutines.flow.StateFlow

/***
 * Interface for filter ViewModel. This is mandatory for the proper preview in compose.
 */
interface FilterViewModel {

    /***
     * Flow which will contain the current state.
     */
    val state: StateFlow<FilterState>

    /***
     * Returns the actually selected gender for filtering.
     * @return Gender
     */
    fun getActuallySelectedGenderForFilter(): Gender

    /***
     * Save filter selected by user
     */
    fun saveSelectedFilter(gender: Gender)
}