package org.d3if0097assessment1.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0097assessment1.database.BeritaDao

class GudangBerita(dao: BeritaDao) : ViewModel() {
    val data: StateFlow<List<BeritaAcara>> = dao.getBerita().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}