package com.example.herbar

import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.graphics.Bitmap
import android.os.NetworkOnMainThreadException
import android.util.Log
import org.json.JSONException
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


fun extractStringInsideParentheses(input: String): String? {
    val regex = "\\(([^)]+)\\)".toRegex()
    val matchResult = regex.find(input)
    return matchResult?.groups?.get(1)?.value
}

fun tipZemljista(soil: Double): Zemljiste?{
    if(soil==9.0) return Zemljiste.SLJUNOVITO
    else if(soil==10.0) return Zemljiste.KRECNJACKO
    else if(soil in 1.0..2.0) return Zemljiste.GLINENO
    else if(soil in 3.0..4.0) return Zemljiste.PJESKOVITO
    else if(soil in 5.0..6.0) return Zemljiste.ILOVACA
    else if(soil in 7.0..8.0) return Zemljiste.CRNICA
    else return null
}


class TrefleDAO {
    private val api_key= "INSERT KEY"


    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(Dispatchers.IO){
            val bit_url=URL("https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1665px-No-Image-Placeholder.svg.png").openStream()
            val defaultBitmap: Bitmap? = BitmapFactory.decodeStream(
                bit_url
            )
            try {
                //extractStringInsideParentheses(biljka.naziv)?.let { Log.d("Belaj", it) }
                var response = extractStringInsideParentheses(biljka.naziv)?.let {
                    ApiAdapter.retrofit.getSearchResult(api_key,
                        it
                    )
                }
                val imageUrl = response?.body()?.data?.firstOrNull()?.slika
                //Log.d("Belaj",imageUrl+"")
                if (imageUrl != null) {
                    val url = URL(imageUrl)
                    return@withContext BitmapFactory.decodeStream(
                        url.openConnection().getInputStream()
                    )
                } else {
                    //Log.d("Belaj","sto null")
                    return@withContext defaultBitmap!!
                }
            } catch (e: ExceptionInInitializerError) {
                //Log.d("Belaj", "Onaj exceptionInInitializer")
                return@withContext defaultBitmap!!
            }
            catch (e: MalformedURLException) {
                //Log.d("Belaj", "URL loš")
                return@withContext defaultBitmap!!
            }
            catch (e: NetworkOnMainThreadException) {
                //Log.d("Belaj", "Šta je ovo")
                return@withContext defaultBitmap!!
            } catch (e: IOException) {
                //Log.d("Belaj", "IOExc")
                return@withContext defaultBitmap!!
            } catch (e: JSONException) {
                //Log.d("Belaj", "JSONExc")
                return@withContext defaultBitmap!!
            }
        }
    }

    suspend fun fixData(biljka: Biljka): Biljka{
        return withContext(Dispatchers.IO){
            try {
                var response = extractStringInsideParentheses(biljka.naziv)?.let {
                    ApiAdapter.retrofit.getBiljka(
                        it.lowercase().replace(" ","-"),
                        api_key
                        )
                }

                val trefleBiljka = response?.body()?.data ?: return@withContext biljka
                val family = trefleBiljka?.family?.name
                val edible = trefleBiljka?.species?.edible
                val toxicity = trefleBiljka?.species?.specifications?.toxicity
                val light = trefleBiljka?.species?.growth?.light
                val humidity = trefleBiljka?.species?.growth?.humidity
                val soil = trefleBiljka?.species?.growth?.soil

                var novaBiljka=biljka
                if(family != null && family != novaBiljka.porodica){
                    novaBiljka.porodica=family
                }
                if(edible != null && edible.equals("false")){
                    novaBiljka.jela=listOf()
                    novaBiljka.medicinskoUpozorenje += " (NIJE JESTIVO) "
                }
                if(toxicity!=null && !toxicity.equals("none")){
                    if(!novaBiljka.medicinskoUpozorenje.contains("TOKSIČNO")){
                        novaBiljka.medicinskoUpozorenje+=" (TOKSIČNO) "
                    }
                }
                if (soil != null) {
                    /*novaBiljka.zemljisniTipovi=listOf()
                    for(zemljiste in biljka.zemljisniTipovi){
                        if(zemljiste == tipZemljista(soil.toDouble())){
                            novaBiljka.zemljisniTipovi.append(zemljiste)
                        }
                    }*/
                    val temp =tipZemljista(soil.toDouble())
                    if(temp!=null){
                        novaBiljka.zemljisniTipovi= listOf(temp)
                    }
                    else{
                        novaBiljka.zemljisniTipovi= listOf()
                    }
                }

                if(light!=null && humidity!=null){
                    var klime= mutableListOf<KlimatskiTip>()
                    if(light>=6.0 && light<=9.0 && (humidity>=1.0 && humidity<=5.0)) klime.add(KlimatskiTip.SREDOZEMNA)
                    if (light>=8.0 && light<=10.0 && (humidity>=7.0 && humidity<=10.0)) klime.add(KlimatskiTip.TROPSKA)
                    if (light>=6.0 && light<=9.0 && (humidity>=5.0 && humidity<=8.0)) klime.add(KlimatskiTip.SUBTROPSKA)
                    if (light>=4.0 && light<=7.0 && (humidity>=3.0 && humidity<=7.0)) klime.add(KlimatskiTip.UMJERENA)
                    if (light>=7.0 && light<=9.0 && (humidity>=1.0 && humidity<=2.0)) klime.add(KlimatskiTip.SUHA)
                    if (light>=0.0 && light<=5.0 && (humidity>=3.0 && humidity<=7.0)) klime.add(KlimatskiTip.PLANINSKA)
                    novaBiljka.klimatskiTipovi=klime
                }

                return@withContext novaBiljka
            } catch (e: ExceptionInInitializerError) {
                //Log.d("Belaj", "Onaj exceptionInInitializer")
                return@withContext biljka
            }
            catch (e: MalformedURLException) {
                //Log.d("Belaj", "URL loš")
                return@withContext biljka
            } catch (e: IOException) {
                ////Log.d("Belaj", "IOExc")
                return@withContext biljka
            } catch (e: JSONException) {
                ////Log.d("Belaj", "JSONExc")
                return@withContext biljka
            }
        }
    }

    suspend fun getPlantswithFlowerColor(flower_color: String, substr: String): List<Biljka>{
        return withContext(Dispatchers.IO){
            try {
                var search_result = mutableListOf<TrefleBiljka>()
                var response = ApiAdapter.retrofit.getFlowerColor(substr,flower_color,api_key)
                if(response.body()!=null) search_result+= response.body()!!.data
                /*while(response.body()?.links != null && response.body()?.links?.next !=null){
                    var next = response.body()?.links?.next
                    if (next != null) {
                        if(next.substringAfter("page=",).toInt()>10) break
                        response= ApiAdapter.retrofit.getPage(substr,flower_color,next.substringAfter("page=").toInt(),api_key)
                    }
                    if(response.body()!=null) search_result+= response.body()!!.data
                }*/
                var rez_biljaka = mutableListOf<Biljka>()
                search_result.forEach {
                    if(it.naziv!=null && it.latin!=null) {
                        val name = it.naziv + " (" + it.latin + ")"
                        var temp = Biljka(
                            name,
                            it.porodica,
                            "",
                            listOf(),
                            ProfilOkusaBiljke.KORIJENASTO,
                            listOf(),
                            listOf(),
                            listOf(),
                            ""
                        )
                        rez_biljaka.add(
                            fixData(temp)
                        )
                    }
                }
                //Log.d("ima ih", rez_biljaka.size.toString())
                return@withContext rez_biljaka
            } catch (e: ExceptionInInitializerError) {
                //Log.d("Belaj", "Onaj exceptionInInitializer")
                return@withContext listOf()
            }
            catch (e: MalformedURLException) {
                //Log.d("Belaj", "URL loš")
                return@withContext listOf()
            } catch (e: IOException) {
                //Log.d("Belaj", "IOExc")
                return@withContext listOf()
            } catch (e: JSONException) {
                //Log.d("Belaj", "JSONExc")
                return@withContext listOf()
            }
        }
    }
}
