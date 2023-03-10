package com.jaumo.dateapp.core.util

/***
 * Class that represents UI events.
 */
sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
}