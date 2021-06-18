package com.example.bottommenudialog

import com.example.bottommenudialog.login.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Routes {
    @POST("login")
    fun login(@Body userData: User): Call<User>

    @Multipart
    @POST("upload")
    fun uploadAudio(
        @Part audio: MultipartBody.Part,
        @Part("Name") Name: String
    ): Call<MultipartBody>

    companion object{
        operator fun invoke(): Routes {
            return Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Routes:: class.java)
        }
    }
}