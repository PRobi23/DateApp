package com.jaumo.dateapp.features.zapping.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.features.zapping.presentation.component.ZappingCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ZappingScreen(
    navController: NavController,
    viewModel: ZappingViewModel = hiltViewModel<ZappingViewModelImpl>()
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    var direction by remember { mutableStateOf(-1) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.zapping_screen_title),
                    style = MaterialTheme.typography.subtitle2
                )
            }, actions = {
                IconButton(onClick = {
                }) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Filter",
                    )
                }
            })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val (x, y) = dragAmount
                            if (abs(x) > abs(y)) {
                                when {
                                    x < 0 -> {
                                        // left
                                        direction = 1
                                    }
                                }
                            }
                        },
                        onDragEnd = {
                            when (direction) {
                                1 -> viewModel.getUser()
                                else -> Unit
                            }
                        }
                    )
                }) {
            state.user?.let { userToShow ->
                ZappingCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    user = userToShow,
                )
            }
            if (state.error != null) {
                Text(
                    text = stringResource(
                        id = if (state.error == ErrorType.NETWORK_ERROR) R.string.network_error else R.string.unknown_error
                    ),
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
private fun ZappingScreenPreview() {
    ZappingScreen(navController = rememberNavController(),
        viewModel = object : ZappingViewModel {

            override val state: StateFlow<UserState> = MutableStateFlow(
                UserState(
                    isLoading = false,
                    user =
                    User(
                        lastName = "Name",
                        age = 25,
                        userPicture = "https://url.com"
                    ),
                    error = null
                )
            )

            override fun getUser() = Unit
        })
}