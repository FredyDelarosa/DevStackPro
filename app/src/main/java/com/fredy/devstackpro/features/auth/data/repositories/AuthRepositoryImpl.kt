package com.fredy.devstackpro.features.auth.data.repositories

import com.fredy.devstackpro.core.network.DevStackApi
import com.fredy.devstackpro.features.auth.data.datasources.remote.mapper.toDomain
import com.fredy.devstackpro.features.auth.data.datasources.remote.model.LoginRequest
import com.fredy.devstackpro.features.auth.data.datasources.remote.model.RegisterRequest
import com.fredy.devstackpro.features.auth.domain.entities.User
import com.fredy.devstackpro.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: DevStackApi
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Pair<User, String>> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.user != null && response.token != null) {
                Result.success(Pair(response.user.toDomain(), response.token))
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: User, password: String): Result<User> {
        return try {
            val request = RegisterRequest(user.email, password, user.firstName, user.lastName)
            val response = api.register(request)
            if (response.user != null) {
                Result.success(response.user.toDomain())
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
    }
}