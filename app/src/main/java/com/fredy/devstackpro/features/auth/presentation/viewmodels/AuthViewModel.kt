package com.fredy.devstackpro.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.devstackpro.core.session.SessionManager
import com.fredy.devstackpro.features.auth.domain.entities.User
import com.fredy.devstackpro.features.auth.domain.usecases.LoginUseCase
import com.fredy.devstackpro.features.auth.domain.usecases.RegisterUseCase
import com.fredy.devstackpro.features.auth.presentation.screens.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        _uiState.update { it.copy(isLoading = true, error = null, successMessage = null) }

        viewModelScope.launch {
            val result = loginUseCase(email, password)

            result.fold(
                onSuccess = { pair ->
                    sessionManager.saveAuthToken(pair.second)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "Bienvenido"
                        )
                    }
                    onSuccess()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al iniciar sesión"
                        )
                    }
                }
            )
        }
    }

    fun register(user: User, password: String, onSuccess: () -> Unit) {
        _uiState.update { it.copy(isLoading = true, error = null, successMessage = null) }

        viewModelScope.launch {
            val result = registerUseCase(user, password)

            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "Usuario registrado exitosamente"
                        )
                    }
                    onSuccess()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error en el registro"
                        )
                    }
                }
            )
        }
    }
}