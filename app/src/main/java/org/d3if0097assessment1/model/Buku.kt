package org.d3if0097assessment1.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Buku(
    @StringRes val judul: Int,
    @StringRes val penulis: Int,
    @StringRes val sinopsis: Int,
    @DrawableRes val gambar: Int
)