package com.fredy.devstackpro.features.language.domain.usecases

import com.fredy.devstackpro.features.language.domain.repositories.LanguageRepository

class DeleteLanguageUseCase(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke(id: Int) = repository.removeLanguage(id)
}