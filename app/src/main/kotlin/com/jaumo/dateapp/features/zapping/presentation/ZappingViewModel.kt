package com.jaumo.dateapp.features.zapping.presentation

import kotlinx.coroutines.flow.StateFlow

/***
 * Interface for zapping ViewModel. This is mandatory for the proper preview in compose.
 */
interface ZappingViewModel {
    /***
     * Flow which will contain the current state.
     */
    val state: StateFlow<UserState>

    /**
     * Get a fresh user
     */
    fun getUser()
}