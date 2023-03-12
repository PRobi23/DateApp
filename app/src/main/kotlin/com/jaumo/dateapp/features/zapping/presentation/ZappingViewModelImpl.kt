package com.jaumo.dateapp.features.zapping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.usecase.GetSavedFilterUseCase
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.features.zapping.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@HiltViewModel
class ZappingViewModelImpl @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getSavedFilterUseCase: GetSavedFilterUseCase
) : ViewModel(), ZappingViewModel {

    private val _state = MutableStateFlow(UserState())
    override val state: StateFlow<UserState> = _state.asStateFlow()

    init {
        getUser()
    }

    override fun getUser() {
        getSavedFilterUseCase().onEach {
            it.data?.let { filter ->
                getUserUseCase(filter.gender)
                    .onEach { result ->
                        setStateByResponseResult(result)
                    }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }

    private fun setStateByResponseResult(result: Response<User>) {
        when (result) {
            is Response.Success -> {
                _state.value = UserState(user = result.data)
            }
            is Response.Error -> {
                _state.value = UserState(
                    error = result.errorType
                )
            }
            is Response.Loading -> {
                _state.value = UserState(isLoading = true)
            }
        }
    }
}