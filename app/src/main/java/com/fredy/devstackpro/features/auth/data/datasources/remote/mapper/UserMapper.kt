package com.fredy.devstackpro.features.auth.data.datasources.remote.mapper

import com.fredy.devstackpro.features.auth.data.datasources.remote.model.UserDto
import com.fredy.devstackpro.features.auth.domain.entities.User

fun UserDto.toDomain() = User(id, email, firstName, lastName)