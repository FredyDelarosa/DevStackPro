package com.fredy.devstackpro.features.auth.domain.repositories

import com.fredy.devstackpro.features.auth.domain.entities.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Pair<User, String>>
    suspend fun register(user: User, password: String): Result<User>
    suspend fun logout()
}