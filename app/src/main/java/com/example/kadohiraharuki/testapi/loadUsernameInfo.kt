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

   /*fun loadWithRetrofit(username: String) {

       //val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val retrofitService = retrofit.create(RetrofitInterface::class.java)

        val retrofitCall = retrofitService.listRepos(username)



        retrofitCall.enqueue(object : Callback<Array<Repository>> {
            override fun onFailure(call: Call<Array<Repository>>?, t: Throwable?) {

                if (!true) {
                    Log.d("sssss","sucsess")
                    return
                }
                else if (true){
                    Log.d("ddddd","kkkk")
                }

            }

            override fun onResponse(call: Call<Array<Repository>>?, response:
            Response<Array<Repository>>?) {
                if (!true) {

                    Log.v("kkkkkk", "err")
                    return
                }

                if (response?.body() != null) {
                   val result = response.body()
                    Log.v("cccccccc", result.toString())

                } else {

                }
            }

        })
    }*/


