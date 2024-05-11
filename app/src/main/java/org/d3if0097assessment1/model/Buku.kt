package org.d3if0097assessment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buku")
data class Buku(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val penulis: String,
    val sinopsis: String,
    val gambar: String,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$judul$penulis",
            "$judul $penulis",
            "$judul",
            "$penulis"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}