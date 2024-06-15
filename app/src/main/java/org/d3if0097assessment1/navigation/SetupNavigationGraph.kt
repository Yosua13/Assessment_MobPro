package org.d3if0097assessment1.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if0097assessment1.ui.screen.BeritaDetailScreen
import org.d3if0097assessment1.ui.screen.BeritaScreen
import org.d3if0097assessment1.ui.screen.KEY_ID_BERITA
import org.d3if0097assessment1.ui.screen.LoginScreen
import org.d3if0097assessment1.ui.screen.MainScreen
import org.d3if0097assessment1.ui.screen.RegisterScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SetupNavigationGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navHostController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController)
        }
        composable(route = Screen.Buku.route) {
            MainScreen(navHostController)
        }
        composable(route = Screen.Berita.route) {
            BeritaScreen(navHostController)
        }
        composable(route = Screen.FormBaruBerita.route) {
            BeritaDetailScreen(navHostController)
        }
        composable(
            route = Screen.FormUbahBerita.route,
            arguments = listOf(
                navArgument(KEY_ID_BERITA) {
                    type = NavType.LongType
                }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_BERITA)
            BeritaDetailScreen(navHostController, id)
        }
    }
}