package com.example.herbar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var biljkaDao: BiljkaDao
    private lateinit var db: BiljkaDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BiljkaDatabase::class.java).build()
        biljkaDao = db.biljkaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addBiljkaAndGetAllBiljkas() = runBlocking {
        val biljka = Biljka(
            naziv = "Nova biljka",
            porodica= "Nova porodica",
            medicinskoUpozorenje = "Upozorenje",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
            klimatskiTipovi= listOf(KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA),
            jela = listOf("Jelo"),
            profilOkusa = ProfilOkusaBiljke.GORKO)
        val result = biljkaDao.saveBiljka(biljka)
        val biljkas = biljkaDao.getAllBiljkas()
        assertEquals(true, result)
        assertEquals(1, biljkas.size)
        assertEquals(biljka.naziv, biljkas[0].naziv)
    }

    @Test
    fun clearDataAndCheckIfDatabaseIsEmpty() = runBlocking {
        val biljka = Biljka(
            naziv = "Nova biljka",
            porodica= "Nova porodica",
            medicinskoUpozorenje = "Upozorenje",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
            klimatskiTipovi= listOf(KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA),
            jela = listOf("Jelo"),
            profilOkusa = ProfilOkusaBiljke.GORKO)
        biljkaDao.saveBiljka(biljka)
        biljkaDao.clearData()
        val biljkas = biljkaDao.getAllBiljkas()
        assertEquals(0, biljkas.size)
    }

}