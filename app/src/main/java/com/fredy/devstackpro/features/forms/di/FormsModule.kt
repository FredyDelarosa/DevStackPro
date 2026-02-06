package com.fredy.devstackpro.features.forms.di

import com.fredy.devstackpro.core.di.AppContainer
import com.fredy.devstackpro.features.forms.presentation.viewmodels.IDEFormViewModelFactory
import com.fredy.devstackpro.features.forms.presentation.viewmodels.LanguageFormViewModelFactory
import com.fredy.devstackpro.features.ide.di.IDEModule
import com.fredy.devstackpro.features.language.di.LanguageModule

class FormsModule(
    appContainer: AppContainer
) {
    private val ideModule = IDEModule(appContainer)
    private val languageModule = LanguageModule(appContainer)

    fun provideIDEFormViewModelFactory(): IDEFormViewModelFactory {
        return IDEFormViewModelFactory(
            ideModule.createIDEUseCase,
            ideModule.updateIDEUseCase,
            ideModule.getIDEsUseCase
        )
    }

    fun provideLanguageFormViewModelFactory(): LanguageFormViewModelFactory {
        return LanguageFormViewModelFactory(
            languageModule.addLanguageUseCase,
            languageModule.updateLanguageUseCase,
            languageModule.getLanguagesUseCase
        )
    }
}