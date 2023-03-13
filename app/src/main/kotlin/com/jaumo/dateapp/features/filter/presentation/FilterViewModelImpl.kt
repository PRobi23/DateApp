package com.jaumo.dateapp.features.filter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaumo.dateapp.core.util.Response
import com.jaumo.dateapp.features.filter.domain.model.Filter
import com.jaumo.dateapp.features.filter.domain.usecase.GetSavedFilterUseCase
import com.jaumo.dateapp.features.filter.domain.usecase.SaveFilterUseCase
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@HiltViewModel
class FilterViewModelImpl @Inject constructor(
    private val getSavedFilterUseCase: GetSavedFilterUseCase,
    private val savedFilterUseCase: SaveFilterUseCase
) : ViewModel(), FilterViewModel {

    private val _state = MutableStateFlow(FilterState())
    override val state: StateFlow<FilterState> = _state.asStateFlow()

    private val _preselectedGender = MutableStateFlow(Gender.BOTH)
    override val preselectedGender: StateFlow<Gender> = _preselectedGender.asStateFlow()

    init {
        getActuallySelectedFilter()
    }

    @TestOnly
    fun getActuallySelectedFilter() {
        getSavedFilterUseCase()
            .onEach { result ->
                setStateByResponseResult(result)
            }.launchIn(viewModelScope)
    }

    private fun setStateByResponseResult(result: Response<Filter>) {
        when (result) {
            is Response.Success -> {
                _state.value = FilterState(filter = result.data)
                getActuallySelectedGenderForFilter()
            }
            is Response.Error -> {
                _state.value = FilterState(
                    error = result.errorType
                )
            }
            is Response.Loading -> {
                _state.value = FilterState(isLoading = true)
            }
        }
    }

    @TestOnly
    fun getActuallySelectedGenderForFilter() {
        _preselectedGender.value = _state.value.filter?.let { filter ->
            Gender.valueOf(filter.gender.toString())
        } ?: Gender.BOTH
    }

    override fun saveSelectedFilter(gender: Gender) {
        savedFilterUseCase(Filter(gender = gender)).launchIn(viewModelScope)
        _preselectedGender.value = gender
    }
}