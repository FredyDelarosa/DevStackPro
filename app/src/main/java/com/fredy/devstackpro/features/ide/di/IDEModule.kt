package com.fredy.devstackpro.features.ide.di

import com.fredy.devstackpro.core.di.AppContainer
import com.fredy.devstackpro.features.ide.data.repositories.IDERepositoryImpl
import com.fredy.devstackpro.features.ide.domain.repositories.IDERepository
import com.fredy.devstackpro.features.ide.domain.usecases.CreateIDEUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.DeleteIDEUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.GetIDEsUseCase
import com.fredy.devstackpro.features.ide.domain.usecases.UpdateIDEUseCase

class IDEModule(
    private val appContainer: AppContainer
) {
    private val ideRepository: IDERepository by lazy {
        IDERepositoryImpl(appContainer.devStackApi)
    }

    val getIDEsUseCase: GetIDEsUseCase by lazy {
        GetIDEsUseCase(ideRepository)
    }

    val createIDEUseCase: CreateIDEUseCase by lazy {
        CreateIDEUseCase(ideRepository)
    }

    val updateIDEUseCase: UpdateIDEUseCase by lazy {
        UpdateIDEUseCase(ideRepository)
    }

    val deleteIDEUseCase: DeleteIDEUseCase by lazy {
        DeleteIDEUseCase(ideRepository)
    }
}