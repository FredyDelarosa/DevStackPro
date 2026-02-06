package com.fredy.devstackpro.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fredy.devstackpro.core.components.DevStackButton
import com.fredy.devstackpro.core.components.DevStackTextField
import com.fredy.devstackpro.features.auth.domain.entities.User
import com.fredy.devstackpro.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Únete a la comunidad de DevStack Pro",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        DevStackTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "Nombre",
            placeholder = "Tu nombre"
        )
        Spacer(modifier = Modifier.height(16.dp))

        DevStackTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Apellido",
            placeholder = "Tu apellido"
        )
        Spacer(modifier = Modifier.height(16.dp))

        DevStackTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo electrónico",
            placeholder = "ejemplo@correo.com"
        )
        Spacer(modifier = Modifier.height(16.dp))

        DevStackTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            placeholder = "Mínimo 6 caracteres"
        )

        Spacer(modifier = Modifier.height(24.dp))

        uiState.error?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        uiState.successMessage?.let { success ->
            Text(
                text = success,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        DevStackButton(
            text = "Registrarme",
            isLoading = uiState.isLoading,
            onClick = {
                val newUser = User(0, email, firstName, lastName)
                viewModel.register(newUser, password, onRegisterSuccess)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onNavigateToLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
