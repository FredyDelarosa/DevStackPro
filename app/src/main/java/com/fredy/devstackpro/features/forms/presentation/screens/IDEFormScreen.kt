package com.fredy.devstackpro.features.forms.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fredy.devstackpro.core.components.DevStackButton
import com.fredy.devstackpro.core.components.DevStackTextField
import com.fredy.devstackpro.features.forms.presentation.viewmodels.IDEFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IDEFormScreen(
    viewModel: IDEFormViewModel,
    ideId: Int?,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(ideId) {
        ideId?.let { viewModel.loadIDE(it) }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = if (ideId == null) "Nuevo IDE" else "Editar IDE",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
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

            DevStackTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = "Nombre del IDE",
                placeholder = "Ej. IntelliJ IDEA"
            )
            Spacer(Modifier.height(16.dp))
            DevStackTextField(
                value = viewModel.developer,
                onValueChange = { viewModel.developer = it },
                label = "Desarrollador",
                placeholder = "Ej. JetBrains"
            )
            Spacer(Modifier.height(16.dp))
            DevStackTextField(
                value = viewModel.releaseYear,
                onValueChange = { viewModel.releaseYear = it },
                label = "Año de Lanzamiento",
                placeholder = "Ej. 2001"
            )
            
            Spacer(modifier = Modifier.weight(1f))

            DevStackButton(
                text = if (ideId == null) "Crear IDE" else "Guardar Cambios",
                isLoading = uiState.isLoading,
                onClick = { viewModel.saveIDE(ideId, onNavigateBack) },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
