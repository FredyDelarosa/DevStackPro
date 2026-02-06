package com.fredy.devstackpro.features.forms.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.devstackpro.features.forms.presentation.FormsUiState
import com.fredy.devstackpro.features.ide.domain.entities.IDE
import com.fredy.devstackpro.features.ide.domain.usecases.CreateIDEUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.GetIDEsUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.UpdateIDEUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IDEFormViewModel(
    private val createIDEUseCase: CreateIDEUseCase,
    private val updateIDEUseCase: UpdateIDEUseCase,
    private val getIDEsUseCase: GetIDEsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormsUiState())
    val uiState = _uiState.asStateFlow()

    var name by mutableStateOf("")
    var developer by mutableStateOf("")
    var releaseYear by mutableStateOf("")

    fun loadIDE(id: Int) {
        if (id == -1) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val ide = getIDEsUseCase().find { it.id == id }
                ide?.let {
                    name = it.name
                    developer = it.developer
                    releaseYear = it.releaseYear.toString()
                }
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar IDE"
                    )
                }
            }
        }
    }

    fun saveIDE(id: Int?, onNavigateBack: () -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null, successMessage = null)
            }

            val ide = IDE(id, name, developer, releaseYear.toIntOrNull() ?: 0)
            val result = if (id == null) createIDEUseCase(ide) else updateIDEUseCase(ide)

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