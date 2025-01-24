package com.aplikasi.myapplication

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

interface RetrofitService {
    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<Response>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Response>
}
