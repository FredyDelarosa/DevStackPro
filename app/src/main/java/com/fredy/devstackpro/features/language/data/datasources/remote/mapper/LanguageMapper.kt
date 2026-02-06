package com.fredy.devstackpro.features.language.data.datasources.remote.mapper

import com.fredy.devstackpro.features.language.data.datasources.remote.model.LanguageDto
import com.fredy.devstackpro.features.language.domain.entities.Language

fun LanguageDto.toDomain() = Language(id, name, paradigm, createdYear)

fun Language.toDto() = LanguageDto(id, name, paradigm, createdYear)