package com.jaumo.dateapp.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Helper class to pass the string resource down to the composables.
 */
sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    data class StringResource(@StringRes val id: Int) : UiText()

    /**
     * Helper function that returns the string value
     */
    fun asString(context: Context): String =
        when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id)

        }

    /***
     * Helper function for composables
     */
    @Composable
    fun asString(): String =
        when (this) {
            is UiText.DynamicString -> value
            is UiText.StringResource -> stringResource(id = id)

        }
}
