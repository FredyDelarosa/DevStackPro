package com.fredy.devstackpro.features.auth.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: UserDto? = null
)