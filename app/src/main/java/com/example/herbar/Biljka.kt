package com.example.herbar

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Biljka (
    @SerializedName("common-name") var naziv : String,
    @SerializedName("family") var porodica : String,
    var medicinskoUpozorenje : String,
    var medicinskeKoristi : List<MedicinskaKorist>,
    var profilOkusa : ProfilOkusaBiljke,
    var jela : List<String>,
    var klimatskiTipovi : List<KlimatskiTip>,
    var zemljisniTipovi : List<Zemljiste>,
    @SerializedName("image_url") var slika: String
)


