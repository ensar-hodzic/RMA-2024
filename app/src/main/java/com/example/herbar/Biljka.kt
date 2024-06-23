package com.example.herbar

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Biljka")
data class Biljka (
    @ColumnInfo("naziv")
    var naziv : String,
    @ColumnInfo("family")
    var porodica : String,
    @ColumnInfo("medicinskoUpozorenje")
    var medicinskoUpozorenje : String,
    @TypeConverters(MedicinskaKoristAdapter::class)
    @ColumnInfo("medicinskeKoristi")
    var medicinskeKoristi : List<MedicinskaKorist>,
    @TypeConverters(ProfilOkusaAdapter::class)
    @ColumnInfo("profilOkusa")
    var profilOkusa : ProfilOkusaBiljke,
    @TypeConverters(JelaAdapter::class)
    @ColumnInfo("jela")
    var jela : List<String>,
    @TypeConverters(KlimatskiTipAdapter::class)
    @ColumnInfo("klimatskiTipovi")
    var klimatskiTipovi : List<KlimatskiTip>,
    @TypeConverters(ZemljisteAdapter::class)
    @ColumnInfo("zemljisniTipovi")
    var zemljisniTipovi : List<Zemljiste>,
    @ColumnInfo("slika")
    var slika: String = "",
    @ColumnInfo("onlineChecked")
    var onlineChecked:Boolean = false,
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)

@Entity(tableName = "BiljkaBitmap",
    foreignKeys = [ForeignKey(
        entity = Biljka::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idBiljke"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class BiljkaBitmap(
    @PrimaryKey
    var idBiljke: Int,
    @TypeConverters(BitmapConverter::class)
    @ColumnInfo("bitmap")
    var bitmap: Bitmap

)


