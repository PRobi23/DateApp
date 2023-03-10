package com.jaumo.dateapp.core.extensions

import androidx.navigation.NavController
import com.jaumo.dateapp.core.util.UiEvent

/***
 * Calls navigate on the ui event route
 */
fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}