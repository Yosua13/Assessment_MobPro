package org.d3if0097assessment1.navigation

import org.d3if0097assessment1.ui.screen.KEY_ID_BUKU

sealed class Screen(val route: String) {
    data object Buku: Screen("bukuScreen")
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
    data object About: Screen("aboutScreen")
    data object FormBaru: Screen("bukuDetailScreen")
    data object FormUbah: Screen("bukuDetailScreen/{$KEY_ID_BUKU}") {
        fun withId(id: Long) = "bukuDetailScreen/$id"
    }
}