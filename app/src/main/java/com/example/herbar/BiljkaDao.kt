package com.example.herbar

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Dao
interface BiljkaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBiljka(biljka: Biljka): Long

    @Query("SELECT * FROM Biljka")
    fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    fun clearBiljkas()

    @Query("DELETE FROM BiljkaBitmap")
    fun clearBiljkaBitmaps()

    @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
    fun getOfflineBiljkas(): List<Biljka>

    @Update
    fun updateBiljka(biljka: Biljka)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBiljkaBitmap(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :idBiljke")
    fun getBiljkaBitmap(idBiljke: Int): BiljkaBitmap?

    @Transaction
    fun saveBiljka(biljka: Biljka): Boolean {
        return try {
            insertBiljka(biljka) != -1L
        } catch (e:  Exception) {
            false
        }
    }

    @Transaction
    fun fixOfflineBiljka(): Int {
        val biljke = getOfflineBiljkas()
        var count = 0
        for (biljka in biljke) {
            var temp = biljka.copy()
            CoroutineScope(Dispatchers.Main).launch {
                temp = TrefleDAO().fixData(biljka)
            }
            if (temp != biljka) {
                updateBiljka(temp)
                count++
            }
        }
        return count
    }

    @Transaction
    fun addImage(idBiljke: Int, bitmap: Bitmap): Boolean {
        val biljka = getAllBiljkas().find { it.id == idBiljke } ?: return false
        val existingBitmap = getBiljkaBitmap(idBiljke)
        return if (existingBitmap == null) {
            insertBiljkaBitmap(BiljkaBitmap(idBiljke, bitmap)) != -1L
        } else {
            false
        }
    }

    @Transaction
    fun clearData() {
        clearBiljkas()
        clearBiljkaBitmaps()
    }
}