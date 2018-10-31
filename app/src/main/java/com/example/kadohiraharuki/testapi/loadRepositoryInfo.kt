package com.example.kadohiraharuki.testapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_repolistview.*
import org.json.JSONArray


class repolistview : AppCompatActivity() {

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

                //do something when error is null
                if (error == null) {
                    val json = JSONArray(data)
                    val RepoJobj = json.getJSONObject(0)
                    //リポジトリのオーナーネームを取得
                    val repository_ownername = RepoJobj.getJSONObject("owner").getString("login")

                    var repository_Info = Array(6, { arrayListOf<String>()})
                    for(repository_count in 0..(json.length() - 1)){

                        val user_Items = json.getJSONObject(repository_count)

                        repository_Info[0].add(user_Items.getString("name"))   //各リポジトリの名前を取得して配列に付け加える（以下同様）
                        repository_Info[1].add(user_Items.getString("stargazers_count")) //各リポジトリのスター数
                        repository_Info[2].add(user_Items.getString("forks"))  //各リポジトリのフォーク数
                        repository_Info[3].add(user_Items.getString("language"))   //各リポジトリの使用言語
                        repository_Info[4].add(user_Items.getString("svn_url")) //各リポジトリのurl
                    }
                    val repos = List(repository_Info[1].size) { i -> ReposData(
                            repository_Info[0][i],
                            repository_Info[1][i],
                            repository_Info[2][i],
                            repository_Info[3][i]
                    )
                    }

                    repoList.adapter = ReposAdapter(this, repos)
                    ownername.text = repository_ownername.toString() + "'s repository"
                    repoList.setOnItemClickListener { parent, view, position, id ->
                        val githubRepository_url = repository_Info[4][position]
                        val intent = Intent(applicationContext, webview::class.java)
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



