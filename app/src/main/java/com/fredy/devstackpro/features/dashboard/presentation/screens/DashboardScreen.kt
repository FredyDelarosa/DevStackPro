package com.fredy.devstackpro.features.dashboard.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fredy.devstackpro.core.components.IDEItem
import com.fredy.devstackpro.core.components.LanguageItem
import com.fredy.devstackpro.features.dashboard.presentation.viewmodels.DashboardViewModel
import com.fredy.devstackpro.features.ide.domain.entities.IDE
import com.fredy.devstackpro.features.language.domain.entities.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    isLoggedIn: Boolean,
    onAddIDE: () -> Unit,
    onAddLanguage: () -> Unit,
    onEditIDE: (IDE) -> Unit,
    onEditLanguage: (Language) -> Unit,
    onDeleteIDE: (Int) -> Unit,
    onDeleteLanguage: (Int) -> Unit,
    onAuthClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("IDEs", "Lenguajes")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("DevStack Pro", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = onAuthClick) {
                        Icon(
                            imageVector = if (isLoggedIn) Icons.Default.Logout else Icons.Default.Login,
                            contentDescription = if (isLoggedIn) "Cerrar Sesión" else "Iniciar Sesión",
                            tint = if (isLoggedIn) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (selectedTab == 0) onAddIDE() else onAddLanguage() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar nuevo")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            state.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTab) {
                0 -> IDEList(
                    ides = state.ides,
                    onEdit = onEditIDE,
                    onDelete = onDeleteIDE
                )
                1 -> LanguageList(
                    languages = state.languages,
                    onEdit = onEditLanguage,
                    onDelete = onDeleteLanguage
                )
            }
        }
    }
}

@Composable
fun IDEList(
    ides: List<IDE>,
    onEdit: (IDE) -> Unit,
    onDelete: (Int) -> Unit
) {
    if (ides.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay IDEs registrados")
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(ides) { ide ->
                IDEItem(
                    ide = ide,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun LanguageList(
    languages: List<Language>,
    onEdit: (Language) -> Unit,
    onDelete: (Int) -> Unit
) {
    if (languages.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay lenguajes registrados")
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(languages) { lang ->
                LanguageItem(
                    language = lang,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}