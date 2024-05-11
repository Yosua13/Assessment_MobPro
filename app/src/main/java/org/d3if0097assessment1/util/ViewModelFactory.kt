package org.d3if0097assessment1.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0097assessment1.database.BukuDao
import org.d3if0097assessment1.model.GudangBuku
import org.d3if0097assessment1.ui.screen.DetailViewModel
import java.lang.IllegalArgumentException


class ViewModelFactory(
    private val dao: BukuDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GudangBuku::class.java)) {
            return GudangBuku(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}