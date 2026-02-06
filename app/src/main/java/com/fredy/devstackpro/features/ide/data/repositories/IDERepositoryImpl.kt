package com.fredy.devstackpro.features.ide.data.repositories

import com.fredy.devstackpro.core.network.DevStackApi
import com.fredy.devstackpro.features.ide.data.datasources.remote.mapper.toDomain
import com.fredy.devstackpro.features.ide.data.datasources.remote.mapper.toDto
import com.fredy.devstackpro.features.ide.domain.entities.IDE
import com.fredy.devstackpro.features.ide.domain.repositories.IDERepository

class IDERepositoryImpl(
    private val api: DevStackApi
) : IDERepository {
    override suspend fun getAllIDEs(): List<IDE> {
        return try {
            api.getAllIDEs().map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getIDEById(id: Int): IDE {
        return api.getIDEById(id).toDomain()
    }

    override suspend fun createIDE(ide: IDE): Result<String> {
        return try {
            val response = api.createIDE(ide.toDto())
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateIDE(ide: IDE): Result<String> {
        return try {
            val response = api.updateIDE(ide.id ?: 0, ide.toDto())
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteIDE(id: Int): Result<String> {
        return try {
            val response = api.deleteIDE(id)
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}