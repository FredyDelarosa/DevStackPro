package com.fredy.devstackpro.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fredy.devstackpro.core.components.DevStackButton
import com.fredy.devstackpro.core.components.DevStackTextField
import com.fredy.devstackpro.features.auth.domain.entities.User
import com.fredy.devstackpro.features.auth.presentation.viewmodels.AuthViewModel
import com.fredy.devstackpro.features.auth.presentation.viewmodels.AuthViewModelFactory

@Composable
fun RegisterScreen(
    factory: AuthViewModelFactory,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel(factory = factory)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text(text = "Crear Cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        uiState.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        uiState.successMessage?.let { success ->
            Text(
                text = success,
                color = Color.Green,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        DevStackTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "Nombre"
        )
        DevStackTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Apellido"
        )
        DevStackTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        DevStackTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña (mín. 6 caracteres)"
        )

        Spacer(modifier = Modifier.height(24.dp))

        DevStackButton(
            text = "Registrarme",
            isLoading = uiState.isLoading,
            onClick = {
                val newUser = User(0, email, firstName, lastName)
                viewModel.register(newUser, password, onRegisterSuccess)
            }
        )
    }
}