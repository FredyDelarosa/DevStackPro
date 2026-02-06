package com.fredy.devstackpro.features.language.di

import com.fredy.devstackpro.core.di.AppContainer
import com.fredy.devstackpro.features.language.data.repositories.LanguageRepositoryImpl
import com.fredy.devstackpro.features.language.domain.repositories.LanguageRepository
import com.fredy.devstackpro.features.language.domain.usecases.AddLanguageUseCase
import com.fredy.devstackpro.features.language.domain.usecases.DeleteLanguageUseCase
import com.fredy.devstackpro.features.language.domain.usecases.GetLanguagesUseCase
import com.fredy.devstackpro.features.language.domain.usecases.UpdateLanguageUseCase

class LanguageModule(
    private val appContainer: AppContainer
) {
    private val languageRepository: LanguageRepository by lazy {
        LanguageRepositoryImpl(appContainer.devStackApi)
    }

    val getLanguagesUseCase: GetLanguagesUseCase by lazy {
        GetLanguagesUseCase(languageRepository)
    }

    val addLanguageUseCase: AddLanguageUseCase by lazy {
        AddLanguageUseCase(languageRepository)
    }

    val updateLanguageUseCase: UpdateLanguageUseCase by lazy {
        UpdateLanguageUseCase(languageRepository)
    }

    val deleteLanguageUseCase: DeleteLanguageUseCase by lazy {
        DeleteLanguageUseCase(languageRepository)
    }
}