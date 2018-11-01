package com.example.kadohiraharuki.testapi.Retrofit_Difine

import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TI クラス名の始まりは基本的に大文字から
//TI Usernameと限定されているが、実際他の値も使うので良くない
class RetrofitSearch: AppCompatActivity(){

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