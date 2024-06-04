package com.example.herbar

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("api/v1/plants/search")
    suspend fun getSearchResult(@Query("token") apiKey: String,
                                @Query("q") latin:String
    ): Response<SearchResult>

    @GET("api/v1/plants/{plant}")
    suspend fun getBiljka(@Path("plant") plant:String, @Query("token") apiKey: String
    ): Response<BiljkaResult>

    @GET("api/v1/plants/search")
    suspend fun getFlowerColor(@Query("q") query: String, @Query("filter[flower_color]") color:String, @Query("token") apiKey: String
    ): Response<SearchResult>

    @GET("api/v1/plants")
    suspend fun getPage(@Query("q") query: String,@Query("filter[flower_color]") color:String, @Query("page") page:Int, @Query("token") apiKey:String): Response<SearchResult>
}

data class SearchResult(
    @SerializedName("data") val data: List<TrefleBiljka>,
    @SerializedName("links") val links: Links
)

data class TrefleBiljka (
    @SerializedName("common_name") var naziv : String,
    @SerializedName("scientific_name") var latin: String,
    @SerializedName("slug") var slug: String,
    @SerializedName("family") var porodica : String,
    @SerializedName("image_url") var slika: String
)

data class Links(
    @SerializedName("next") val next: String?
)

data class Family(
    @SerializedName("name") val name: String?
)

data class Growth(
    @SerializedName("light") val light: Double?,
    @SerializedName("atmospheric_humidity") val humidity: Double?,
    @SerializedName("soil_texture") val soil: Double?
)

data class Specifications(
    @SerializedName("toxicity") val toxicity: String?,
)

data class Species(
    @SerializedName("edible") val edible: String,
    @SerializedName("specifications") val specifications: Specifications,
    @SerializedName("growth") val growth: Growth
)

data class TrefleSpecies(
    @SerializedName("family") val family: Family,
    @SerializedName("main_species") val species: Species,

)

data class BiljkaResult(
    @SerializedName("data") val data: TrefleSpecies
)

object ApiAdapter {
    val retrofit : Api = Retrofit.Builder()
        .baseUrl("https://trefle.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}