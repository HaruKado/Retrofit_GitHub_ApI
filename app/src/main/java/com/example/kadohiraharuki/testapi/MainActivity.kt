package com.example.kadohiraharuki.testapi
import android.app.Application
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            //ボタンがクリックされたらAPIを叩く。

            //val editText = findViewById<EditText>(R.id.search) as EditText

            //val s = search.toString()
            //Log.d("skodjf", s)
            HitAPITask().execute("https://api.github.com/search/users?q="+search.text.trim())

            Log.d("checkhit", HitAPITask().toString())
            //"+s.trim()+sort:followers")





        }
        listView.setOnItemClickListener { parent, view, position, id ->
            /*val url: String = "https://github.com"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)*/
            val intent = Intent(this, webview::class.java)
            startActivity(intent)

        }


        //試しリスト
        val location = listOf(
                "Hokkaido",
                "Tokyo",
                "Aizu",
                "Kobe")
        val repository = listOf(
                "20",
                "17",
                "3",
                "32"
        )
        val follow = listOf(
                "32",
                "42",
                "14",
                "15"
        )
        val follower = listOf(
                "18",
                "82",
                "0",
                "10"
        )


        data class UserData(val loc: String, val repository: String, val follow: String, val follower: String)

        val users = List(location.size) { i -> UserData(location[i], repository[i], follow[i], follower[i]) }
        //リストが上手くusersに格納されているかの確認
        Log.d("data", users.toString())
        data class ViewHolder(val locTextView: TextView, val repositoryTextView: TextView, val followTextView: TextView, val follwerTextView: TextView)

        class UserListAdapter(context: Context, users: List<UserData>) : ArrayAdapter<UserData>(context, 0, users) {
            private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                var view = convertView
                var holder: ViewHolder

                if (view == null) {
                    view = layoutInflater.inflate(R.layout.list_item, parent, false)
                    holder = ViewHolder(
                            view.findViewById(R.id.locTextView),
                            view.findViewById(R.id.repositoryTextView),
                            view.findViewById(R.id.followTextView),
                            view.findViewById(R.id.followerTextView)
                    )
                    view.tag = holder
                } else {
                    holder = view.tag as ViewHolder
                }

                val user = getItem(position) as UserData
                holder.locTextView.text = user.loc
                holder.repositoryTextView.text = user.repository
                holder.followTextView.text = user.follow
                holder.follwerTextView.text = user.follower


                return view
            }
        }


        //listViewにUserListAdapterをセット
        val arrayAdapter = UserListAdapter(this, users)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = arrayAdapter


    }


    inner class HitAPITask : AsyncTask<String, String, Array<Array<String?>>?>() {

        override fun doInBackground(vararg params: String): Array<Array<String?>>? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            //HTTP固有の機能をサポートするURLConnection
            var connection: HttpURLConnection? = null
            //文字、配列、行をバッファリングすることによって、文字型入力ストリームからテキストを効率良く読み込む
            var reader: BufferedReader? = null
            //Stringクラス同様に宣言した変数に、文字列を格納するために使用
            val buffer: StringBuffer

            Log.d("para", params[0].toString())

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                //ここで指定したAPIを叩いてみてます。


                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
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
                Log.d("CHECK", buffer.toString())


                val jsonText = buffer.toString()


                val parentJsonObj = JSONObject(jsonText)
                Log.d("CHECK2", parentJsonObj.toString())

                val parentJSONOArray = parentJsonObj.getJSONArray("items")

                var arrayd = Array(2, { arrayOfNulls<String>(4) })

                val firstJsonObj = parentJSONOArray.getJSONObject(0)
                val loginname: ArrayList<String> = arrayListOf(firstJsonObj.getString("login"))
                val avatar: ArrayList<String> = arrayListOf(firstJsonObj.getString("avatar_url"))
                Log.d("CHECKlo1", loginname.toString())
                Log.d("CHECKav1", avatar.toString())

                val secondJsonObj = parentJSONOArray.getJSONObject(1)
                loginname.add(secondJsonObj.getString("login"))
                avatar.add(secondJsonObj.getString("avatar_url"))

                val thirdJsonObj = parentJSONOArray.getJSONObject(2)
                loginname.add(thirdJsonObj.getString("login"))
                avatar.add(thirdJsonObj.getString("avatar_url"))

                val fourthJsonObj = parentJSONOArray.getJSONObject(3)
                loginname.add(fourthJsonObj.getString("login"))
                avatar.add(fourthJsonObj.getString("avatar_url"))

                arrayd[0] = Array(loginname.size) { i -> loginname[i] }
                arrayd[1] = Array(avatar.size) { i -> avatar[i] }

                Log.d("Checkarr", arrayd[1][3])


                //Stringでreturnしてあげましょう。
                return arrayd

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //textView.text =("失敗")
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null

        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: Array<Array<String?>>?) {
            super.onPostExecute(result)
            if (result == null) {
                return
            }
            Log.d("CHECKresu", result[1][3])


            data class UsersData(val name: String?, val userImgView: String?)

            val users = List(result[0].size) { i -> UsersData(result[0][i], result[1][i]) }
            Log.d("CHECKuser", users.toString())

            data class ViewHolder(val nameTextView: TextView, val UserImgView: ImageView)

            class UserAdapter(context: Context, users: List<UsersData>) : ArrayAdapter<UsersData>(context, 0, users) {
                private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                    var view = convertView
                    var holder: ViewHolder

                    if (view == null) {
                        view = layoutInflater.inflate(R.layout.list_item, parent, false)
                        holder = ViewHolder(
                                view.findViewById(R.id.nameTextView),
                                view.findViewById(R.id.userImgView)
                        )
                        view.tag = holder
                    } else {
                        holder = view.tag as ViewHolder
                    }

                    val usersp = getItem(position) as UsersData
                    holder.nameTextView.text = usersp.name
                    Picasso.with(context).load(usersp.userImgView).into(holder.UserImgView)


                    return view

                }
            }
            val array2Adapter = UserAdapter(applicationContext, users)
            val listView2: ListView = findViewById(R.id.listView)
            listView2.adapter = array2Adapter

        }

    }
}



