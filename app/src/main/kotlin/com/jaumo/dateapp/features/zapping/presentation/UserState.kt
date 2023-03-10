package com.jaumo.dateapp.features.zapping.presentation

import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.features.zapping.domain.model.User

/***
 * Class that holds the state of the user
 */
data class UserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: ErrorType? = null
)