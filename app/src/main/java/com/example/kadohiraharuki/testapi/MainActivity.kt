package com.example.kadohiraharuki.testapi
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search.setOnClickListener {

            //１回目の非同期処理を実行
            HitAPITask().execute("https://api.github.com/search/users?q=" + search.text.trim())
            Log.d("CHECKHitAPITask", HitAPITask().toString())
        }
    }

    inner class HitAPITask : AsyncTask<String, String, Array<ArrayList<String?>>?>() {

        override fun doInBackground(vararg params: String): Array<ArrayList<String?>>? {

            //HTTP固有の機能をサポートするURLConnection
            var connection: HttpURLConnection? = null
            //文字、配列、行をバッファリングすることによって、文字型入力ストリームからテキストを効率良く読み込む
            var reader: BufferedReader? = null
            //Stringクラス同様に宣言した変数に、文字列を格納するために使用
            val buffer: StringBuffer


            try {
                val url = URL(params[0])    //指定された文字列のURLオブジェクトを生成
                connection = url.openConnection() as HttpURLConnection  //HTTP経由でWebページにアクセスするためのオブジェクト生成
                connection.connect()    //URLConnectionクラスのconnectメソッドを使いurlに接続

                //取得した情報をReaderクラスのBufferedReaderオブジェクトにキャストし、データを文字単位で読み込めるようにする
                reader = BufferedReader(InputStreamReader(connection.inputStream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                }
                Log.d("CHECK_buffer", buffer.toString())

                val apiJSONArray = JSONObject(buffer.toString()).getJSONArray("items")
                Log.d("CHECK_apiJSONArray", apiJSONArray.toString())


                var users_Info = Array(3, { arrayListOf<String?>() })

                for (user_number in 0..(apiJSONArray.length() - 1)) {

                    val useritems = apiJSONArray.getJSONObject(user_number)
                    users_Info[0].add(useritems.getString("login")) //各ユーザーネームを取得して保存する
                    users_Info[1].add(useritems.getString("avatar_url")) //各ユーザーのプロフィール画像のurlを取得して保存する
                    users_Info[2].add(useritems.getString("repos_url")) //各ユーザーのリポジトリ情報のurlを取得して保存する
                }

                return users_Info

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断する。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }


        //返ってきたデータをビューに反映させる。
        override fun onPostExecute(users_Info: Array<ArrayList<String?>>?) {
            super.onPostExecute(users_Info)
            if (users_Info == null) {
                return
            }


            //位置情報、リポジトリ数、フォロー数、フォロワー数のリストを用意(これらの要素はapiで情報を取得できなかったので、全て同じ値にする)　
            val location = listOf("Hokkaido")
            val repository = listOf("20")
            val follow = listOf("32")
            val follower = listOf("18")


            val users = List(users_Info[0].size) { i -> UsersData(
                    users_Info[0][i],   //ユーザーの名前情報が入っている
                    users_Info[1][i],   //ユザーのプロフィール画像のurlが入っている
                    location[0],
                    repository[0],
                    follow[0],
                    follower[0]
                   )
            }

            Log.d("CHECKusers", users.toString())

            //リストビューにユーザー情報を反映させる
            listView.adapter = UsersInfo_Adapter(this@MainActivity, users)

            //ユーザーのボタンをタップ時に、そのユーザーのリポジトリのurlをSearch_Reposiotoryクラスで検索するようにする
            listView.setOnItemClickListener { parent, view, position, id ->
                val repository_url = users_Info[2][position]
                Log.d("reprlt", repository_url)
                Search_Reposiotory().execute(repository_url)
            }
        }
    }

    //２回目の非同期処理、ここでは個人のリポジトリの情報があるurlに接続し、言語やスター数などの情報を取得する
    inner class Search_Reposiotory : AsyncTask<String, String, Array<ArrayList<String>>?>() {
        override fun doInBackground(vararg repository_url: String): Array<ArrayList<String>>? {
            var connection: HttpURLConnection? = null

            var reader: BufferedReader? = null

            val buffer: StringBuffer
            try {
                val url = URL(repository_url[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                }

                val repository_Jsonarr = JSONArray(buffer.toString())

                val RepoJobj = JSONArray(buffer.toString()).getJSONObject(0)
                //リポジトリのオーナーネームを取得
                val repository_ownername = RepoJobj.getJSONObject("owner").getString("login")

                //リポジトリの情報を入れるための配列を宣言
                var repository_Info = Array(6, { arrayListOf<String>()})
                repository_Info[0].add(repository_ownername)

                for(repository_count in 0..(repository_Jsonarr.length() - 1)){

                    val user_Items = repository_Jsonarr.getJSONObject(repository_count)

                    repository_Info[1].add(user_Items.getString("name"))   //各リポジトリの名前を取得して配列に付け加える（以下同様）
                    repository_Info[2].add(user_Items.getString("svn_url")) //各リポジトリのurl
                    repository_Info[3].add(user_Items.getString("stargazers_count")) //各リポジトリのスター数
                    repository_Info[4].add(user_Items.getString("forks"))  //各リポジトリのフォーク数
                    repository_Info[5].add(user_Items.getString("language"))   //各リポジトリの使用言語
                }


                return repository_Info

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null

        }

        override fun onPostExecute(result: Array<ArrayList<String>>?) {
            super.onPostExecute(result)
            if (result == null) {
                return
            }

            //intentを使ってrepolistviewにリポジトリの情報をアクテビティ渡す
            val intent = Intent(applicationContext, repolistview::class.java)
            intent.putExtra("RepoOwner", result[0][0])
            intent.putExtra("RepoName",result[1])
            intent.putExtra("RepoUrl",result[2])
            intent.putExtra("RepoStar",result[3])
            intent.putExtra("RepoFork",result[4])
            intent.putExtra("RepoLang",result[5])
            startActivity(intent)

            Log.d("kkkkkkk", result[3].toString())

        }
    }

}





