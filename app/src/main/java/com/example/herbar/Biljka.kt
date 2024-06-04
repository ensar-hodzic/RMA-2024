package com.example.herbar

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Biljka (
    var naziv : String,
    var porodica : String,
    var medicinskoUpozorenje : String,
    var medicinskeKoristi : List<MedicinskaKorist>,
    var profilOkusa : ProfilOkusaBiljke,
    var jela : List<String>,
    var klimatskiTipovi : List<KlimatskiTip>,
    var zemljisniTipovi : List<Zemljiste>,
    var slika: String
)


