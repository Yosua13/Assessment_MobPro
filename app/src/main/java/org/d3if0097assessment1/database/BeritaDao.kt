package org.d3if0097assessment1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0097assessment1.model.BeritaAcara

@Dao
interface BeritaDao {

    @Insert
    suspend fun insert(beritaAcara: BeritaAcara)

    @Update
    suspend fun update(beritaAcara: BeritaAcara)

    @Query("SELECT * FROM berita_acara ORDER BY id ASC")
    fun getBerita(): Flow<List<BeritaAcara>>

    @Query("SELECT * FROM berita_acara WHERE id = :id")
    suspend fun getBeritaById(id: Long?): BeritaAcara?

    @Query("DELETE FROM berita_acara WHERE id = :id")
    suspend fun deleteById(id: Long)
}
