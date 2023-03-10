package com.jaumo.dateapp.core.extensions

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

/**
 * Adds a debug place holder for koin resource
 */
@Composable
internal fun debugPlaceholderForImage(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }