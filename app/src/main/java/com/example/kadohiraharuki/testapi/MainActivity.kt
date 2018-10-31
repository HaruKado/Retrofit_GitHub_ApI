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


class MainActivity: AppCompatActivity(){

    val etUsername: EditText by lazy {
        findViewById<EditText>(R.id.search)
    }
    lateinit var repo: RepositoryContract

    lateinit var repourl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search.setOnClickListener {
            val SearchUsername = etUsername.text.toString().trim()

            loadUsernameInfo.ApiClientManager.apiClient.listRepos(SearchUsername).enqueue(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: retrofit2.Response<Result>) {
                    if (response.isSuccessful) {
                        Log.d("check_response", "call response=${response.body()}")

                        val users = response!!.body()!!.items
                        listview.adapter = UsersInfo_Adapter(this@MainActivity, users)

                        listview.setOnItemClickListener { parent, view, position, id ->
                            val users_position = response!!.body()!!.items[position]
                            repourl = users_position.repos_url
                            Log.d("checkposition", repourl)


                            val intent = Intent(applicationContext, repolistview::class.java)
                            intent.putExtra("RepoUrl", repourl)
                            startActivity(intent)


                           // repo.onReposAvailable(repourl)
                        }

                    }
                }
                override fun onFailure(call: Call<Result>, t: Throwable) {

                }
            })
        }

    }
}

