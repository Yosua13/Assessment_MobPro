package org.d3if0097assessment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "berita_acara")
data class BeritaAcara(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val todo: String,
    val tanggal: String,
    val jam: String,
    val description: String? = null // Kolom baru ditambahkan
)