package com.fredy.devstackpro.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.devstackpro.features.dashboard.presentation.DashboardUiState
import com.fredy.devstackpro.features.ide.domain.usecases.DeleteIDEUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.GetIDEsUseCase
import com.fredy.devstackpro.features.language.domain.usecases.DeleteLanguageUseCase
import com.fredy.devstackpro.features.language.domain.usecases.GetLanguagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getIDEsUseCase: GetIDEsUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val deleteIDEUseCase: DeleteIDEUseCase,
    private val deleteLanguageUseCase: DeleteLanguageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val ides = getIDEsUseCase()
                val languages = getLanguagesUseCase()
                _uiState.update {
                    it.copy(
                        ides = ides,
                        languages = languages,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun deleteIDE(id: Int) {
        viewModelScope.launch {
            deleteIDEUseCase(id).fold(
                onSuccess = { loadDashboardData() },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            )
        }
    }

    fun deleteLanguage(id: Int) {
        viewModelScope.launch {
            deleteLanguageUseCase(id).fold(
                onSuccess = { loadDashboardData() },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            )
        }
    }
}