package com.fredy.devstackpro.features.language.domain.entities

data class Language(
    val id: Int? = null,
    val name: String,
    val paradigm: String,
    val createdYear: Int
)