package org.d3if0097assessment1.ui.screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0097assessment1.database.BukuDao
import org.d3if0097assessment1.model.Buku

class DetailViewModel(private val dao: BukuDao) : ViewModel() {
    fun insert(judul: String, penulis: String, sinopsis: String, gambar: String) {
        val buku = Buku(
            judul = judul,
            penulis = penulis,
            sinopsis = sinopsis,
            gambar = gambar
        )
        viewModelScope.launch (Dispatchers.IO) {
            dao.insert(buku)
        }
    }
    suspend fun getBuku(id: Long?): Buku? {
        return dao.getBukuById(id)
    }

    fun update(id: Long, judul: String, penulis: String, sinopsis: String, gambar: String) {
        val buku = Buku(
            id = id,
            judul = judul,
            penulis = penulis,
            sinopsis = sinopsis,
            gambar = gambar
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(buku)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}