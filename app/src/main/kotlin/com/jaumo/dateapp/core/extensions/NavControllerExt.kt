package com.jaumo.dateapp.core.extensions

import androidx.navigation.NavController
import com.jaumo.dateapp.core.util.UiEvent

/***
 * Calls navigate on the ui event route
 */
fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}

/***
 * Helper function for navigating up
 */
fun NavController.navigateUpWithCheck(): Boolean {
    return if (this.previousBackStackEntry != null) {
        this.navigateUp()
        true
    } else {
        false
    }
}