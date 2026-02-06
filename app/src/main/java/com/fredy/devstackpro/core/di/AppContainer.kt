package com.fredy.devstackpro.core.di

import android.content.Context
import com.fredy.devstackpro.core.network.DevStackApi
import com.fredy.devstackpro.core.session.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class AppContainer(private val context: Context) {

    val sessionManager: SessionManager by lazy {
        SessionManager(context)
    }

    private val baseUrl = "https://a2.upprojects.online/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            sessionManager.fetchAuthToken()?.let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val devStackApi: DevStackApi by lazy {
        retrofit.create(DevStackApi::class.java)
    }
}