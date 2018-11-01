package com.example.kadohiraharuki.testapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.kadohiraharuki.testapi.Adapter.RepositoryAdapter
import com.example.kadohiraharuki.testapi.Data_File.Repository
import com.example.kadohiraharuki.testapi.Retrofit_Difine.RetrofitSearch
import kotlinx.android.synthetic.main.activity_repolistview.*
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.repos_item.*
import retrofit2.Call
import retrofit2.Callback

//TIファイル名とクラス名が一致していなので良くない
//TI Activityであるのにfuelを使ってサーチしているのも良くない
//TI RepositoryActivity
class RepositoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repolistview)

        val intent = getIntent()
        val userName = intent.extras.getString("userName")

        ownername.text = userName

        RetrofitSearch.ApiClientManager.apiClient.listRepository(userName).enqueue(object : Callback<ArrayList<Repository>>{
            override fun onResponse(call: Call<ArrayList<Repository>>, response: retrofit2.Response<ArrayList<Repository>>) {
                if (response.isSuccessful) {
                    Log.d("check_repository", "call response=${response.body()}")

                    val repository = response.body()!!
                    repoList.adapter = RepositoryAdapter(this@RepositoryActivity, repository)

                    repoList.setOnItemClickListener { parent, view, position, id ->
                        val repository = response.body()!![position]
                        val repositoryUrl = repository.svn_url

                        val intent = Intent(applicationContext, WebActivity::class.java)
                        intent.putExtra("WebRepository_url", repositoryUrl)
                        startActivity(intent)
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<Repository>>, t: Throwable) {

            }
        })
    }
}



