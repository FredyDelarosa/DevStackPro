package com.fredy.devstackpro.features.ide.data.datasources.remote.mapper

import com.fredy.devstackpro.features.ide.data.datasources.remote.model.IDEDto
import com.fredy.devstackpro.features.ide.domain.entities.IDE

fun IDEDto.toDomain() = IDE(id, name, developer, releaseYear)

fun IDE.toDto() = IDEDto(id, name, developer, releaseYear)