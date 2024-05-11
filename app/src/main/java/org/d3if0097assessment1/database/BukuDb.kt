package org.d3if0097assessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.d3if0097assessment1.model.BeritaAcara
import org.d3if0097assessment1.model.Buku

@Database(entities = [Buku::class, BeritaAcara::class], version = 2, exportSchema = false)
abstract class BukuDb : RoomDatabase() {
    abstract val bukuDao: BukuDao
    abstract val beritaDao: BeritaDao

    companion object {
        @Volatile
        private var INSTANCE: BukuDb? = null
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS berita_acara (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "todo TEXT NOT NULL," +
                        "kalender TEXT NOT NULL," +
                        "jam TEXT NOT NULL)")
            }
        }

        fun getInstance(context: Context): BukuDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BukuDb::class.java,
                        "gerejaku.db"
                    )
                        .addMigrations(MIGRATION_1_2)  // Tambahkan migrasi ke sini
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
