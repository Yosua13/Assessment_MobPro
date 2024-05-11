package org.d3if0097assessment1.navigation

import org.d3if0097assessment1.ui.screen.KEY_ID_BERITA
import org.d3if0097assessment1.ui.screen.KEY_ID_BUKU

sealed class Screen(val route: String) {
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
    data object Buku: Screen("bukuScreen")
    data object Berita: Screen("beritaScreen")
    data object About: Screen("aboutScreen")
    data object FormBaruBuku: Screen("bukuDetailScreen")
    data object FormUbahBuku: Screen("bukuDetailScreen/{$KEY_ID_BUKU}") {
        fun withId(id: Long) = "bukuDetailScreen/$id"
    }
    data object FormBaruBerita: Screen("beritaDetailScreen")
    data object FormUbahBerita: Screen("beritaDetailScreen/{$KEY_ID_BERITA}") {
        fun withId(id: Long) = "beritaDetailScreen/$id"
    }
}