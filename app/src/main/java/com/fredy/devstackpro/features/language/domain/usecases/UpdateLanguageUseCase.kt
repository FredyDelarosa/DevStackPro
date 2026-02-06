package com.fredy.devstackpro.features.language.domain.usecases

import com.fredy.devstackpro.features.language.domain.entities.Language
import com.fredy.devstackpro.features.language.domain.repositories.LanguageRepository

class UpdateLanguageUseCase(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke(language: Language) = repository.updateLanguage(language)
}