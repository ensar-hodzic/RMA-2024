package com.example.herbar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(BitmapConverter::class, JelaAdapter::class, ProfilOkusaAdapter::class, MedicinskaKoristAdapter::class, ZemljisteAdapter::class, KlimatskiTipAdapter::class)
abstract class BiljkaDatabase : RoomDatabase() {
    abstract fun biljkaDao(): BiljkaDao

    companion object {
        @Volatile
        private var INSTANCE: BiljkaDatabase? = null

        fun getInstance(context: Context): BiljkaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BiljkaDatabase::class.java,
                    "biljke-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
