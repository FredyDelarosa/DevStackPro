package com.fredy.devstackpro.features.auth.domain.usecases

import com.fredy.devstackpro.features.auth.domain.entities.User
import com.fredy.devstackpro.features.auth.domain.repositories.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User, password: String) =
        repository.register(user, password)
}