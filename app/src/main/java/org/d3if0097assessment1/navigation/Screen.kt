package org.d3if0097assessment1.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
    data object About: Screen("aboutScreen")
}