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
import com.fredy.devstackpro.features.forms.presentation.viewmodels.LanguageFormViewModel
import com.fredy.devstackpro.features.forms.presentation.viewmodels.LanguageFormViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageFormScreen(
    factory: LanguageFormViewModelFactory,
    languageId: Int?,
    onNavigateBack: () -> Unit
) {
    val viewModel: LanguageFormViewModel = viewModel(factory = factory)

    LaunchedEffect(languageId) {
        languageId?.let { viewModel.loadLanguage(it) }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (languageId == null) "Nuevo Lenguaje" else "Editar Lenguaje") },
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
                .fillMaxSize()
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
                label = "Nombre del Lenguaje"
            )
            Spacer(Modifier.height(12.dp))
            DevStackTextField(
                value = viewModel.paradigm,
                onValueChange = { viewModel.paradigm = it },
                label = "Paradigma (Ej: Funcional, POO)"
            )
            Spacer(Modifier.height(12.dp))
            DevStackTextField(
                value = viewModel.createdYear,
                onValueChange = { viewModel.createdYear = it },
                label = "Año de Creación"
            )
            Spacer(Modifier.height(32.dp))

            DevStackButton(
                text = if (languageId == null) "Registrar Lenguaje" else "Actualizar Datos",
                isLoading = uiState.isLoading,
                onClick = { viewModel.saveLanguage(languageId, onNavigateBack) }
            )
        }
    }
}