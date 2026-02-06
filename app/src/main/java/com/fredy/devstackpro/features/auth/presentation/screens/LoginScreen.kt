package com.fredy.devstackpro.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fredy.devstackpro.core.components.DevStackButton
import com.fredy.devstackpro.core.components.DevStackTextField
import com.fredy.devstackpro.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DevStack Pro",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Gestiona tus stacks de manera profesional",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))

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
            placeholder = "••••••••"
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
            text = "Iniciar Sesión",
            isLoading = uiState.isLoading,
            onClick = { viewModel.login(email, password, onLoginSuccess) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
