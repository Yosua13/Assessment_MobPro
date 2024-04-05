package org.d3if0097assessment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if0097assessment1.ui.screen.AboutScreen
import org.d3if0097assessment1.ui.screen.LoginScreen
import org.d3if0097assessment1.ui.screen.MainScreen
import org.d3if0097assessment1.ui.screen.RegisterScreen

@Composable
fun SetupNavigationGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navHostController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navHostController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navHostController)
        }
    }
}