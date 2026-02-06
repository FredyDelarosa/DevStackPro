package com.fredy.devstackpro.features.forms.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fredy.devstackpro.features.language.domain.usecases.AddLanguageUseCase
import com.fredy.devstackpro.features.language.domain.usecases.GetLanguagesUseCase
import com.fredy.devstackpro.features.language.domain.usecases.UpdateLanguageUseCase

class LanguageFormViewModelFactory(
    private val addLanguageUseCase: AddLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageFormViewModel::class.java)) {
            return LanguageFormViewModel(
                addLanguageUseCase,
                updateLanguageUseCase,
                getLanguagesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}