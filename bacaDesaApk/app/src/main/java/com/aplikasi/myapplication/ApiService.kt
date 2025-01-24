package com.aplikasi.myapplication

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<UserResponse>

    companion object {
        val instance: ApiService by lazy {
            RetrofitClient.instance.create(ApiService::class.java)
        }
    }

    @GET("buku.php")
    fun getDaftarBuku(@Query("action") action: String = "getDaftarBuku"): Call<List<BukuResponse>>

    @GET("buku.php")
    fun getLatestBook(@Query("action") action: String = "getLatestBook"): Call<BukuResponse>

    @GET("buku.php")
    fun getBookRecommendations(@Query("action") action: String = "getBookRecommendations"): Call<List<BukuResponse>>

    @GET("buku.php")
    fun getBookDetails(@Query("action") action: String = "getBookDetails", @Query("book_id") bookId: String): Call<BukuResponse>

    @GET("buku.php")
    fun getBukuByQuery(@Query("action") action: String = "getBukuByQuery", @Query("query") query: String): Call<List<BukuResponse>>

    @GET("buku.php")
    fun getBukuByKategori(@Query("action") action: String = "getBukuByKategori", @Query("kategori") kategori: String): Call<List<BukuResponse>>

    @GET("buku.php")
    fun getKategoriBuku(@Query("action") action: String = "getKategoriBuku"): Call<List<String>> // Menambahkan endpoint untuk kategori
}
