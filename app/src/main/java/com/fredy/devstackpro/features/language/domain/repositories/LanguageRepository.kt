package com.fredy.devstackpro.features.language.domain.repositories

import com.fredy.devstackpro.features.language.domain.entities.Language

interface LanguageRepository {
    suspend fun getAllLanguages(): List<Language>
    suspend fun addLanguage(language: Language): Result<String>
    suspend fun updateLanguage(language: Language): Result<String>
    suspend fun removeLanguage(id: Int): Result<String>
}