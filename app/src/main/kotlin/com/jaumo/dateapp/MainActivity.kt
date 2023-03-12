package com.jaumo.dateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaumo.dateapp.core.extensions.navigate
import com.jaumo.dateapp.core.navigation.Route
import com.jaumo.dateapp.core.util.UiEvent
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
                    }
                }
            }
        }
    }
}