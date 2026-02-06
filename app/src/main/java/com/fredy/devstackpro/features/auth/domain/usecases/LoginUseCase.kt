package com.fredy.devstackpro.features.auth.domain.usecases

import com.fredy.devstackpro.features.auth.domain.entities.User
import com.fredy.devstackpro.features.auth.domain.repositories.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Pair<User, String>> {
        return repository.login(email, password)
    }
}