package com.fredy.devstackpro.features.forms.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fredy.devstackpro.core.components.DevStackButton
import com.fredy.devstackpro.core.components.DevStackTextField
import com.fredy.devstackpro.features.forms.presentation.viewmodels.IDEFormViewModel
import com.fredy.devstackpro.features.forms.presentation.viewmodels.IDEFormViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IDEFormScreen(
    factory: IDEFormViewModelFactory,
    ideId: Int?,
    onNavigateBack: () -> Unit
) {
    val viewModel: IDEFormViewModel = viewModel(factory = factory)

    LaunchedEffect(ideId) {
        ideId?.let { viewModel.loadIDE(it) }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (ideId == null) "Agregar IDE" else "Editar IDE") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
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
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = "Nombre del IDE"
            )
            Spacer(Modifier.height(8.dp))
            DevStackTextField(
                value = viewModel.developer,
                onValueChange = { viewModel.developer = it },
                label = "Desarrollador"
            )
            Spacer(Modifier.height(8.dp))
            DevStackTextField(
                value = viewModel.releaseYear,
                onValueChange = { viewModel.releaseYear = it },
                label = "Año de Lanzamiento"
            )
            Spacer(Modifier.height(24.dp))

            DevStackButton(
                text = "Guardar",
                isLoading = uiState.isLoading,
                onClick = { viewModel.saveIDE(ideId, onNavigateBack) }
            )
        }
    }
}