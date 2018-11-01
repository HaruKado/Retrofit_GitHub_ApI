package com.example.kadohiraharuki.testapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.kadohiraharuki.testapi.Adapter.ReposAdapter
import com.example.kadohiraharuki.testapi.Data_File.ReposData
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_repolistview.*
import org.json.JSONArray

//TIファイル名とクラス名が一致していなので良くない
//TI Activityであるのにfuelを使ってサーチしているのも良くない
//TI RepositoryActivity
class RepositoryActivity : AppCompatActivity() {

    lateinit var repourl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repolistview)

        val intent = getIntent()
        repourl = intent.extras.getString("RepoUrl")
        Log.d("repourl", repourl)


        fun requestGithubSearchAPI() {

            repourl.httpGet().responseString { request, response, either ->
                val (data,error) = either

                if (error == null) {
                    val json = JSONArray(data)
                    val RepoJobj = json.getJSONObject(0)

                    //リポジトリのオーナーネームを取得
                    val repository_ownername = RepoJobj.getJSONObject("owner").getString("login")

                    var repos = arrayListOf<ReposData>()

                    for(repository_count in 0..(json.length() - 1)){

                        val user_Items = json.getJSONObject(repository_count)
                        println(user_Items.getString("name"))
                        println(user_Items.getString("stargazers_count"))
                        println(user_Items.getString("forks"))
                        println(user_Items.getString("language"))
                        println(user_Items.getString("svn_url"))
                        println("----")

                        var repo = ReposData(
                                user_Items.getString("name"),   //各リポジトリの名前を取得して配列に付け加える（以下同様）
                                user_Items.getString("stargazers_count"), //各リポジトリのスター数
                                user_Items.getString("forks"),  //各リポジトリのフォーク数
                                user_Items.getString("language") ,//各リポジトリの使用言語
                                user_Items.getString("svn_url") //各リポジトリのurl
                        )
                        repos.add(repo)




                    }


                    repoList.adapter = ReposAdapter(this, repos)
                    ownername.text = repository_ownername.toString() + "'s repository"


                    repoList.setOnItemClickListener { parent, view, position, id ->
                        val githubRepository_url = repos[position].svn_url
                        val intent = Intent(applicationContext, WebActivity::class.java)
                        intent.putExtra("WebRepository_url", githubRepository_url)
                        startActivity(intent)
                    }

                } else {
                    Log.e("API", "There is something wrong with API, ${error}")

                }
            }
        }
        requestGithubSearchAPI()

    }
}



