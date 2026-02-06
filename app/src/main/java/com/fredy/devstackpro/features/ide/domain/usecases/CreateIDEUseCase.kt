package com.fredy.devstackpro.features.ide.domain.usecases

import com.fredy.devstackpro.features.ide.domain.entities.IDE
import com.fredy.devstackpro.features.ide.domain.repositories.IDERepository

class CreateIDEUseCase(
    private val repository: IDERepository
) {
    suspend operator fun invoke(ide: IDE) = repository.createIDE(ide)
}