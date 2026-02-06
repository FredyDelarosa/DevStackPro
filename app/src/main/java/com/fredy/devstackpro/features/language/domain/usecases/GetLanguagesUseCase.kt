package com.fredy.devstackpro.features.language.domain.usecases

import com.fredy.devstackpro.features.language.domain.repositories.LanguageRepository

class GetLanguagesUseCase(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke() = repository.getAllLanguages()
}