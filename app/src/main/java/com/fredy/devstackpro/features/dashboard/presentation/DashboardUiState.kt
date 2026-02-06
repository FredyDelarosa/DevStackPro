package com.fredy.devstackpro.features.dashboard.presentation

import com.fredy.devstackpro.features.ide.domain.entities.IDE
import com.fredy.devstackpro.features.language.domain.entities.Language

data class DashboardUiState(
    val ides: List<IDE> = emptyList(),
    val languages: List<Language> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)