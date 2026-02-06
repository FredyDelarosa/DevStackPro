package com.fredy.devstackpro.features.ide.domain.usecases

import com.fredy.devstackpro.features.ide.domain.repositories.IDERepository

class GetIDEsUseCase(
    private val repository: IDERepository
) {
    suspend operator fun invoke() = repository.getAllIDEs()
}