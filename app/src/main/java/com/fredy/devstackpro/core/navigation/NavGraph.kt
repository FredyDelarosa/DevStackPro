package com.fredy.devstackpro.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fredy.devstackpro.core.di.AppContainer
import com.fredy.devstackpro.features.auth.di.AuthModule
import com.fredy.devstackpro.features.auth.presentation.screens.LoginScreen
import com.fredy.devstackpro.features.auth.presentation.screens.RegisterScreen
import com.fredy.devstackpro.features.dashboard.di.DashboardModule
import com.fredy.devstackpro.features.dashboard.presentation.screens.DashboardScreen
import com.fredy.devstackpro.features.dashboard.presentation.viewmodels.DashboardViewModel
import com.fredy.devstackpro.features.forms.di.FormsModule
import com.fredy.devstackpro.features.forms.presentation.screens.IDEFormScreen
import com.fredy.devstackpro.features.forms.presentation.screens.LanguageFormScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    appContainer: AppContainer
) {
    val authModule = AuthModule(appContainer)
    val dashboardModule = DashboardModule(appContainer)
    val formsModule = FormsModule(appContainer)

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                factory = authModule.provideAuthViewModelFactory(),
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                factory = authModule.provideAuthViewModelFactory(),
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                factory = dashboardModule.provideDashboardViewModelFactory(),
                isLoggedIn = appContainer.sessionManager.isLoggedIn(),
                onAddIDE = {
                    if (appContainer.sessionManager.isLoggedIn()) {
                        navController.navigate(Screen.IDEForm.createRoute(-1))
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onAddLanguage = {
                    if (appContainer.sessionManager.isLoggedIn()) {
                        navController.navigate(Screen.LanguageForm.createRoute(-1))
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onEditIDE = { ide ->
                    if (appContainer.sessionManager.isLoggedIn()) {
                        navController.navigate(Screen.IDEForm.createRoute(ide.id ?: -1))
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onEditLanguage = { lang ->
                    if (appContainer.sessionManager.isLoggedIn()) {
                        navController.navigate(Screen.LanguageForm.createRoute(lang.id ?: -1))
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onDeleteIDE = { id ->
                    if (appContainer.sessionManager.isLoggedIn()) {
                        val viewModel = dashboardModule.provideDashboardViewModelFactory().create(
                            DashboardViewModel::class.java)
                        viewModel.deleteIDE(id)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onDeleteLanguage = { id ->
                    if (appContainer.sessionManager.isLoggedIn()) {
                        val viewModel = dashboardModule.provideDashboardViewModelFactory().create(DashboardViewModel::class.java)
                        viewModel.deleteLanguage(id)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onAuthClick = {
                    if (appContainer.sessionManager.isLoggedIn()) {
                        appContainer.sessionManager.clearSession()
                        navController.navigate(Screen.Dashboard.route) { popUpTo(0) }
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                }
            )
        }

        composable(
            route = Screen.IDEForm.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id").takeIf { it != -1 }
            IDEFormScreen(
                factory = formsModule.provideIDEFormViewModelFactory(),
                ideId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.LanguageForm.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id").takeIf { it != -1 }
            LanguageFormScreen(
                factory = formsModule.provideLanguageFormViewModelFactory(),
                languageId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}