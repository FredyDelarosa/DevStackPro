package com.fredy.devstackpro.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fredy.devstackpro.features.ide.domain.usecases.DeleteIDEUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.GetIDEsUseCase
import com.fredy.devstackpro.features.language.domain.usecases.DeleteLanguageUseCase
import com.fredy.devstackpro.features.language.domain.usecases.GetLanguagesUseCase

class DashboardViewModelFactory(
    private val getIDEsUseCase: GetIDEsUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val deleteIDEUseCase: DeleteIDEUseCase,
    private val deleteLanguageUseCase: DeleteLanguageUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                getIDEsUseCase,
                getLanguagesUseCase,
                deleteIDEUseCase,
                deleteLanguageUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}