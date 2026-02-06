package com.fredy.devstackpro.features.language.data.repositories

import com.fredy.devstackpro.core.network.DevStackApi
import com.fredy.devstackpro.features.language.data.datasources.remote.mapper.toDomain
import com.fredy.devstackpro.features.language.data.datasources.remote.mapper.toDto
import com.fredy.devstackpro.features.language.domain.entities.Language
import com.fredy.devstackpro.features.language.domain.repositories.LanguageRepository

class LanguageRepositoryImpl(
    private val api: DevStackApi
) : LanguageRepository {
    override suspend fun getAllLanguages(): List<Language> {
        return try {
            api.getAllLanguages().map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addLanguage(language: Language): Result<String> {
        return try {
            val response = api.addLanguage(language.toDto())
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateLanguage(language: Language): Result<String> {
        return try {
            val response = api.updateLanguage(language.id ?: 0, language.toDto())
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeLanguage(id: Int): Result<String> {
        return try {
            val response = api.removeLanguage(id)
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}