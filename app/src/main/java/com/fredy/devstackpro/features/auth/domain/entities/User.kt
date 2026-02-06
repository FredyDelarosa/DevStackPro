package com.fredy.devstackpro.features.auth.domain.entities

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String
)