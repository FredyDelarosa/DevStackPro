package com.fredy.devstackpro.features.ide.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class IDEDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("developer") val developer: String,
    @SerializedName("release_year") val releaseYear: Int
)