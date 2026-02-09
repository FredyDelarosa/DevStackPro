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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fredy.devstackpro.core.components.IDEItem
import com.fredy.devstackpro.core.components.LanguageItem
import com.fredy.devstackpro.features.dashboard.presentation.viewmodels.DashboardViewModel
import com.fredy.devstackpro.features.dashboard.presentation.viewmodels.DashboardViewModelFactory
import com.fredy.devstackpro.features.ide.domain.entities.IDE
import com.fredy.devstackpro.features.language.domain.entities.Language
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    factory: DashboardViewModelFactory,
    isLoggedIn: Boolean,
    onAddIDE: () -> Unit,
    onAddLanguage: () -> Unit,
    onEditIDE: (IDE) -> Unit,
    onEditLanguage: (Language) -> Unit,
    onDeleteIDE: (Int) -> Unit,
    onDeleteLanguage: (Int) -> Unit,
    onAuthClick: () -> Unit
) {
    val viewModel: DashboardViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("IDEs", "Lenguajes")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "DevStack Pro", 
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    ) 
                },
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
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (selectedTab == 0) onAddIDE() else onAddLanguage() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = FloatingActionButtonDefaults.largeShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar nuevo")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { 
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            ) 
                        }
                    )
                }
            }

            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            state.errorMessage?.let { error ->
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
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
        EmptyState(message = "No hay IDEs registrados")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
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
        EmptyState(message = "No hay lenguajes registrados")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
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

@Composable
fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
