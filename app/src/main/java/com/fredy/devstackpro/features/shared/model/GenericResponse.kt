package com.fredy.devstackpro.features.shared.model

import com.google.gson.annotations.SerializedName

data class GenericResponse(
    @SerializedName("message") val message: String
)