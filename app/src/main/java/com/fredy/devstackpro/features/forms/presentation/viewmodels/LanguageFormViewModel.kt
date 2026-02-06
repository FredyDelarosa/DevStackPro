package com.fredy.devstackpro.features.forms.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.devstackpro.features.forms.presentation.FormsUiState
import com.fredy.devstackpro.features.language.domain.entities.Language
import com.fredy.devstackpro.features.language.domain.usecases.AddLanguageUseCase
import com.fredy.devstackpro.features.language.domain.usecases.GetLanguagesUseCase
import com.fredy.devstackpro.features.language.domain.usecases.UpdateLanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LanguageFormViewModel(
    private val addLanguageUseCase: AddLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormsUiState())

    val uiState: StateFlow<FormsUiState> = _uiState.asStateFlow()

    var name by mutableStateOf("")
    var paradigm by mutableStateOf("")
    var createdYear by mutableStateOf("")

    fun loadLanguage(id: Int) {
        if (id == -1) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val language = getLanguagesUseCase().find { it.id == id }
                language?.let {
                    name = it.name
                    paradigm = it.paradigm
                    createdYear = it.createdYear.toString()
                }
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar lenguaje"
                    )
                }
            }
        }
    }

    fun saveLanguage(id: Int?, onNavigateBack: () -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null, successMessage = null)
            }

            val language = Language(
                id = id,
                name = name,
                paradigm = paradigm,
                createdYear = createdYear.toIntOrNull() ?: 0
            )

            val result = if (id == null) {
                addLanguageUseCase(language)
            } else {
                updateLanguageUseCase(language)
            }

            result.fold(
                onSuccess = { message ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = message
                        )
                    }
                    onNavigateBack()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al guardar"
                        )
                    }
                }
            )
        }
    }
}