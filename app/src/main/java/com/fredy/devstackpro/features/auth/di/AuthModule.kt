package com.fredy.devstackpro.features.auth.di

import com.fredy.devstackpro.core.di.AppContainer
import com.fredy.devstackpro.core.session.SessionManager
import com.fredy.devstackpro.features.auth.data.repositories.AuthRepositoryImpl
import com.fredy.devstackpro.features.auth.domain.repositories.AuthRepository
import com.fredy.devstackpro.features.auth.domain.usecases.LoginUseCase
import com.fredy.devstackpro.features.auth.domain.usecases.RegisterUseCase
import com.fredy.devstackpro.features.auth.presentation.viewmodels.AuthViewModelFactory

class AuthModule(
    private val appContainer: AppContainer
) {
    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(appContainer.devStackApi)
    }

    private val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository)
    }

    private val registerUseCase: RegisterUseCase by lazy {
        RegisterUseCase(authRepository)
    }

    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory(
            loginUseCase,
            registerUseCase,
            appContainer.sessionManager
        )
    }
}