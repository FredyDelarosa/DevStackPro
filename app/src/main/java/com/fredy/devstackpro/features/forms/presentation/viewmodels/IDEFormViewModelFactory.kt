package com.fredy.devstackpro.features.forms.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fredy.devstackpro.features.ide.domain.usecases.CreateIDEUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.GetIDEsUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.UpdateIDEUseCase

class IDEFormViewModelFactory(
    private val createIDEUseCase: CreateIDEUseCase,
    private val updateIDEUseCase: UpdateIDEUseCase,
    private val getIDEsUseCase: GetIDEsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IDEFormViewModel::class.java)) {
            return IDEFormViewModel(
                createIDEUseCase,
                updateIDEUseCase,
                getIDEsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}