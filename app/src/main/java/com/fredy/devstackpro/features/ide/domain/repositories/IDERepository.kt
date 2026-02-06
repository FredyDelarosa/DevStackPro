package com.fredy.devstackpro.features.ide.domain.repositories

import com.fredy.devstackpro.features.ide.domain.entities.IDE

interface IDERepository {
    suspend fun getAllIDEs(): List<IDE>
    suspend fun getIDEById(id: Int): IDE
    suspend fun createIDE(ide: IDE): Result<String>
    suspend fun updateIDE(ide: IDE): Result<String>
    suspend fun deleteIDE(id: Int): Result<String>
}