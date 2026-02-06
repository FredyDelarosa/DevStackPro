package com.fredy.devstackpro.features.forms.presentation

data class FormsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)