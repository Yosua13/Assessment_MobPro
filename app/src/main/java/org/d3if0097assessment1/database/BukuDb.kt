package org.d3if0097assessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.d3if0097assessment1.model.BeritaAcara

@Database(entities = [BeritaAcara::class], version = 3, exportSchema = false)
abstract class BukuDb : RoomDatabase() {
    abstract val beritaDao: BeritaDao

    companion object {
        @Volatile
        private var INSTANCE: BukuDb? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS berita_acara (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "todo TEXT NOT NULL," +
                        "tanggal TEXT NOT NULL," +
                        "jam TEXT NOT NULL)")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE berita_acara ADD COLUMN description TEXT")
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
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)  // Tambahkan semua migrasi di sini
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


