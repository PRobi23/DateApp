package com.jaumo.dateapp.features.filter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.util.ErrorType
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.toCamelCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
internal fun FilterScreen(
    viewModel: FilterViewModel = hiltViewModel<FilterViewModelImpl>(),
    navigateUp: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val preselectedGender = viewModel.preselectedGender.collectAsState().value.toCamelCase()

    val isSelectedItem: (String) -> Boolean = { preselectedGender == it }

    val genders =
        Gender.values()
            .map { gender ->
                gender.toCamelCase()
            }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.filter_screen_title),
                    style = MaterialTheme.typography.h5
                )
            }, navigationIcon =
            {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_content_description)
                    )
                }
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.LightGray)
        ) {
            state.filter?.let {
                Column {
                    Text(
                        text = stringResource(id = R.string.filter_description),
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                    Row(
                        Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .fillMaxWidth()
                    ) {
                        genders.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .selectable(
                                        selected = isSelectedItem(item),
                                        onClick = {
                                            viewModel.saveSelectedFilter(Gender.valueOf(item.uppercase()))
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(16.dp)
                            ) {
                                RadioButton(
                                    selected = isSelectedItem(item),
                                    onClick = null
                                )
                                Text(
                                    text = item
                                )
                            }
                        }
                    }
                }
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
private fun PreviewFilterScreen() {
    FilterScreen(
        navigateUp = {},
        viewModel = object : FilterViewModel {
            override val state: StateFlow<FilterState> = MutableStateFlow(
                FilterState(isLoading = false, filter = Filter(gender = Gender.MALE))
            )
            override val preselectedGender: StateFlow<Gender> = MutableStateFlow(Gender.MALE)

            override fun saveSelectedFilter(gender: Gender) = Unit
        }
    )

}