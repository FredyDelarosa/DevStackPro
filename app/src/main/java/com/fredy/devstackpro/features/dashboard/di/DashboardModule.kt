package com.fredy.devstackpro.features.dashboard.di

import com.fredy.devstackpro.core.di.AppContainer
import com.fredy.devstackpro.features.dashboard.presentation.viewmodels.DashboardViewModelFactory
import com.fredy.devstackpro.features.ide.di.IDEModule
import com.fredy.devstackpro.features.language.di.LanguageModule

class DashboardModule(
    appContainer: AppContainer
) {
    private val ideModule = IDEModule(appContainer)
    private val languageModule = LanguageModule(appContainer)

    fun provideDashboardViewModelFactory(): DashboardViewModelFactory {
        return DashboardViewModelFactory(
            ideModule.getIDEsUseCase,
            languageModule.getLanguagesUseCase,
            ideModule.deleteIDEUseCase,
            languageModule.deleteLanguageUseCase
        )
    }
}