package com.example.kadohiraharuki.testapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("search/users")
    fun listRepos(@Query("q") username: String): Call<Result>

}