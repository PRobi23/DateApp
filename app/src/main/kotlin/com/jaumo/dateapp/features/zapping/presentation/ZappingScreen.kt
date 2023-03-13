package com.jaumo.dateapp.features.zapping.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.navigation.Route
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.core.util.UiEvent
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.features.zapping.presentation.component.ZappingCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs

@Composable
internal fun ZappingScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ZappingViewModel = hiltViewModel<ZappingViewModelImpl>(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    var direction by remember { mutableStateOf(-1) }


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getUser()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.zapping_screen_title),
                    style = MaterialTheme.typography.subtitle2
                )
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
                                    x > 0 -> {
                                        direction = 0
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
                    getNewUser = viewModel::getUser,
                    onNavigate = onNavigate
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
    ZappingScreen(onNavigate = { UiEvent.Navigate(Route.FILTER) },
        viewModel = object : ZappingViewModel {

            override val state: StateFlow<UserState> = MutableStateFlow(
                UserState(
                    isLoading = false,
                    user =
                    User(
                        lastName = "Name",
                        age = 25,
                        userPicture = "https://url.com",
                        thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg",
                        gender = Gender.FEMALE
                    ),
                    error = null
                )
            )

            override fun getUser() = Unit
        })
}