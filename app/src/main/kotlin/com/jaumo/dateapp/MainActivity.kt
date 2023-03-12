package com.jaumo.dateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaumo.dateapp.core.extensions.navigate
import com.jaumo.dateapp.core.extensions.navigateUpWithCheck
import com.jaumo.dateapp.core.navigation.Route
import com.jaumo.dateapp.features.filter.presentation.FilterScreen
import com.jaumo.dateapp.features.zapping.presentation.ZappingScreen
import com.jaumo.dateapp.ui.theme.DateAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DateAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.ZAPPING
                ) {
                    composable(Route.ZAPPING) {
                        ZappingScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.FILTER) {
                        FilterScreen(navigateUp = navController::navigateUpWithCheck)
                    }
                }
            }
        }
    }
}