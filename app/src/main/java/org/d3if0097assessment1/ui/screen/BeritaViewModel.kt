package org.d3if0097assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0097assessment1.database.BeritaDao
import org.d3if0097assessment1.model.BeritaAcara

class BeritaViewModel(private val dao: BeritaDao) : ViewModel() {

    fun insert(todo: String, tanggal: String, jam: String) {
        val beritaAcara = BeritaAcara(
            todo = todo,
            tanggal = tanggal,
            jam = jam
        )
        viewModelScope.launch (Dispatchers.IO) {
            dao.insert(beritaAcara)
        }
    }
    suspend fun getBerita(id: Long?): BeritaAcara? {
        return dao.getBeritaById(id)
    }

    fun update(id: Long, todo: String, tanggal: String, jam: String) {
        val beritaAcara = BeritaAcara(
            id = id,
            todo = todo,
            tanggal = tanggal,
            jam = jam
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(beritaAcara)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

}