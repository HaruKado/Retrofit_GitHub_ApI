package com.example.kadohiraharuki.testapi.Retrofit_Difine


import com.example.kadohiraharuki.testapi.Data_File.Repository
import com.example.kadohiraharuki.testapi.Data_File.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitInterface {

    @GET("search/users")
    fun listUser(@Query("q") username: String): Call<UserItem>

    @GET("users/{user}/repos")
    fun listRepository(@Path("user") username: String): Call<ArrayList<Repository>>

}


