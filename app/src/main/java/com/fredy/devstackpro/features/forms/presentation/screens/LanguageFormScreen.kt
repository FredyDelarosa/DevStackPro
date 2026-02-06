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
import com.fredy.devstackpro.features.forms.presentation.viewmodels.LanguageFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageFormScreen(
    viewModel: LanguageFormViewModel,
    languageId: Int?,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(languageId) {
        languageId?.let { viewModel.loadLanguage(it) }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = if (languageId == null) "Nuevo Lenguaje" else "Editar Lenguaje",
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
                label = "Nombre del Lenguaje",
                placeholder = "Ej. Kotlin"
            )
            Spacer(Modifier.height(16.dp))
            DevStackTextField(
                value = viewModel.paradigm,
                onValueChange = { viewModel.paradigm = it },
                label = "Paradigma",
                placeholder = "Ej. Multiplataforma, Funcional"
            )
            Spacer(Modifier.height(16.dp))
            DevStackTextField(
                value = viewModel.createdYear,
                onValueChange = { viewModel.createdYear = it },
                label = "Año de Creación",
                placeholder = "Ej. 2011"
            )
            
            Spacer(modifier = Modifier.weight(1f))

            DevStackButton(
                text = if (languageId == null) "Crear Lenguaje" else "Guardar Cambios",
                isLoading = uiState.isLoading,
                onClick = { viewModel.saveLanguage(languageId, onNavigateBack) },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
