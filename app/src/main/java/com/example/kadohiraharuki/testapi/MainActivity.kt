package com.example.kadohiraharuki.testapi
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback


class MainActivity: AppCompatActivity(), RepositoryContract.View{

    val etUsername: EditText by lazy {
        findViewById<EditText>(R.id.search)
    }

    var users:ArrayList<UsersData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search.setOnClickListener {
            val SearchUsername = etUsername.text.toString().trim()

            loadUsernameInfo.ApiClientManager.apiClient.listRepos(SearchUsername).enqueue(object: Callback<Result> {
                override fun onResponse(call: Call<Result>, response: retrofit2.Response<Result>) {
                    if (response.isSuccessful) {
                        Log.d("mmmmmm", "call response=${response.body()}")


                    }
                }
                override fun onFailure(call: Call<Result>, t: Throwable) {

                }
            })
        }
    }

    override fun onReposAvailable(users: Array<UsersData>){
        val handler = Handler(Looper.getMainLooper())
        handler.post({
        recycleView.adapter = RepoAdapter(users)
        })
    }
}


        /*val handler = Handler()

        // メインスレッドでネットワーク通信を行うことができない

                val response = loadUsernameInfo()
                // とりあえず1つだけ表示させてみる
                val firstRepos = response.body()!![0]

                // 別スレッドからUI操作ができないのでhandlerを使用する
                handler.post(Runnable {
                    name_text.text = firstRepos.name
                    description_text.text = firstRepos.description
                    language_text.text = firstRepos.language
                    url_text.text = firstRepos.url
                })

                Log.d("retrofit", "リポジトリのID" + response.body())
            } catch (e: Exception) {
                Log.w("retrofit", "fetchReposList :" + e)

            }

        }

    }*//*
}*/








