package org.d3if0097assessment1.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0097assessment1.database.BeritaDao
import org.d3if0097assessment1.model.GudangBerita
import org.d3if0097assessment1.ui.screen.BeritaViewModel
import java.lang.IllegalArgumentException

class ViewModelFactoryBerita(
    private val dao: BeritaDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GudangBerita::class.java)) {
            return GudangBerita(dao) as T
        } else if (modelClass.isAssignableFrom(BeritaViewModel::class.java)) {
            return BeritaViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
