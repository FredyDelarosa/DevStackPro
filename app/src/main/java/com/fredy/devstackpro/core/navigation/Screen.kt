package com.fredy.devstackpro.core.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")

    object LanguageForm : Screen("language_form/{id}") {
        fun createRoute(id: Int = -1) = "language_form/$id"
    }

    object IDEForm : Screen("ide_form/{id}") {
        fun createRoute(id: Int = -1) = "ide_form/$id"
    }
}