package com.example.kadohiraharuki.testapi

import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class loadUsernameInfo: AppCompatActivity(){

    open class ApiClientManager {
        companion object {
            private const val ENDPOINT = "https://api.github.com/"

            val apiClient: RetrofitInterface
                get() = Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create(Gson()))
                        .build()
                        .create(RetrofitInterface::class.java)

        }
    }


}