package com.fredy.devstackpro.core.network

import com.fredy.devstackpro.features.auth.data.datasources.remote.model.AuthResponse
import com.fredy.devstackpro.features.auth.data.datasources.remote.model.LoginRequest
import com.fredy.devstackpro.features.auth.data.datasources.remote.model.RegisterRequest
import com.fredy.devstackpro.features.ide.data.datasources.remote.model.IDEDto
import com.fredy.devstackpro.features.language.data.datasources.remote.model.LanguageDto
import com.fredy.devstackpro.features.shared.model.GenericResponse
import retrofit2.http.*

interface DevStackApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @GET("ides")
    suspend fun getAllIDEs(): List<IDEDto>

    @GET("ides/{id}")
    suspend fun getIDEById(@Path("id") id: Int): IDEDto

    @POST("ides")
    suspend fun createIDE(@Body ide: IDEDto): GenericResponse

    @PUT("ides/{id}")
    suspend fun updateIDE(@Path("id") id: Int, @Body ide: IDEDto): GenericResponse

    @DELETE("ides/{id}")
    suspend fun deleteIDE(@Path("id") id: Int): GenericResponse

    @GET("languages")
    suspend fun getAllLanguages(): List<LanguageDto>

    @POST("languages")
    suspend fun addLanguage(@Body language: LanguageDto): GenericResponse

    @PUT("languages/{id}")
    suspend fun updateLanguage(@Path("id") id: Int, @Body language: LanguageDto): GenericResponse

    @DELETE("languages/{id}")
    suspend fun removeLanguage(@Path("id") id: Int): GenericResponse
}