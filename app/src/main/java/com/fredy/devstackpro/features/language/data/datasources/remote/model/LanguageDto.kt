package com.fredy.devstackpro.features.language.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class LanguageDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("paradigm") val paradigm: String,
    @SerializedName("created_year") val createdYear: Int
)