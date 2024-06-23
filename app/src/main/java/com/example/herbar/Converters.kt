package com.example.herbar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class MedicinskaKoristAdapter {
    @TypeConverter
    fun storedStringListLineType(value: String): List<MedicinskaKorist> {
        val dbValues = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
        val enums: MutableList<MedicinskaKorist> = ArrayList()

        for (s in dbValues)
            enums.add(MedicinskaKorist.valueOf(s))
        return enums
    }

    @TypeConverter
    fun listLineTypeToStoredString(listLineTypes: List<MedicinskaKorist>): String {
        var value = ""

        for (lineType in listLineTypes)
            value += lineType.name + ","

        return value
    }
}

class ProfilOkusaAdapter {
    @TypeConverter
    fun storedStringListLineType(value: String): List<ProfilOkusaBiljke> {
        val dbValues = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
        val enums: MutableList<ProfilOkusaBiljke> = ArrayList()

        for (s in dbValues)
            enums.add(ProfilOkusaBiljke.valueOf(s))
        return enums
    }

    @TypeConverter
    fun listLineTypeToStoredString(listLineTypes: List<ProfilOkusaBiljke>): String {
        var value = ""

        for (lineType in listLineTypes)
            value += lineType.name + ","

        return value
    }
}

class KlimatskiTipAdapter {
    @TypeConverter
    fun storedStringListLineType(value: String): List<KlimatskiTip> {
        val dbValues = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
        val enums: MutableList<KlimatskiTip> = ArrayList()

        for (s in dbValues)
            enums.add(KlimatskiTip.valueOf(s))
        return enums
    }

    @TypeConverter
    fun listLineTypeToStoredString(listLineTypes: List<KlimatskiTip>): String {
        var value = ""

        for (lineType in listLineTypes)
            value += lineType.name + ","

        return value
    }
}

class JelaAdapter {
    @TypeConverter
    fun storedStringListLineType(value: String): List<String> {
        val dbValues = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
        val enums: MutableList<String> = ArrayList()

        for (s in dbValues)
            enums.add(s)
        return enums
    }

    @TypeConverter
    fun listLineTypeToStoredString(listLineTypes: List<String>): String {
        var value = ""

        for (lineType in listLineTypes)
            value += lineType + ","

        return value
    }
}

class ZemljisteAdapter {
    @TypeConverter
    fun storedStringListLineType(value: String): List<Zemljiste> {
        val dbValues = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
        val enums: MutableList<Zemljiste> = ArrayList()

        for (s in dbValues)
            enums.add(Zemljiste.valueOf(s))
        return enums
    }

    @TypeConverter
    fun listLineTypeToStoredString(listLineTypes: List<Zemljiste>): String {
        var value = ""

        for (lineType in listLineTypes)
            value += lineType.name + ","

        return value
    }
}

class BitmapConverter {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(encodedString: String): Bitmap {
        val byteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}