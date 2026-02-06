package com.fredy.devstackpro.features.ide.domain.usecases

import com.fredy.devstackpro.features.ide.domain.repositories.IDERepository

class DeleteIDEUseCase(
    private val repository: IDERepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteIDE(id)
}