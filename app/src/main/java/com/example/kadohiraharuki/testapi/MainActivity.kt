package com.example.kadohiraharuki.testapi
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            //ボタンがクリックされたらAPIを叩く。

            //val editText = findViewById<EditText>(R.id.search) as EditText

            //val s = search.toString()
            //Log.d("skodjf", s)
            HitAPITask().execute("https://api.github.com/search/users?q=" + search.text.trim())

            Log.d("checkhit", HitAPITask().toString())
            //"+s.trim()+sort:followers")

        }
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


                val parentJsonObj = JSONObject(buffer.toString())
                Log.d("CHECK2", parentJsonObj.toString())
                val parentJSONOArray = parentJsonObj.getJSONArray("items")

                val namearr = arrayListOf<String>()
                val avatararr = arrayListOf<String>()
                val repoarr = arrayListOf<String>()
                for(i in 0..(parentJSONOArray.length() - 1)){

                    val useritems = parentJSONOArray.getJSONObject(i)
                    val name = useritems.getString("login")
                    val avatar = useritems.getString("avatar_url")
                    val repo= useritems.getString("repos_url")
                    namearr.add(name)
                    avatararr.add(avatar)
                    repoarr.add(repo)


                }

                Log.d("nameconf", namearr[15])
                Log.d("avatarconf", avatararr[13])
                Log.d("repoconf", repoarr[11])

                var arrayd = Array(3, { arrayOfNulls<String>(namearr.size) })

                arrayd[0] = Array(namearr.size) { i -> namearr[i] }
                arrayd[1] = Array(avatararr.size) { i -> avatararr[i] }
                arrayd[2] = Array(repoarr.size) { i -> repoarr[i] }

                Log.d("sizzz" , namearr.size.toString())
                Log.d("arrayd", arrayd[2][1].toString())

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
            Log.d("CHECKresu", result[2][1])

            val location = listOf("Hokkaido")
            val repository = listOf("20")
            val follow = listOf("32")
            val follower = listOf("18")
            val placepic =  listOf(R.drawable.place)
            val repopic = listOf(R.drawable.repository)
            val followpic = listOf(R.drawable.follow)
            val followerpic = listOf(R.drawable.follower)



            data class UsersData(val name: String?, val userImgView: String?, val loc: String, val repository: String, val follow: String, val follower: String, val placepic: Int, val repopic:Int, val follwpic:Int, val follwerpic:Int)

            val users = List(result[0].size) { i -> UsersData(result[0][i], result[1][i], location[0], repository[0], follow[0], follower[0], placepic[0], repopic[0], followpic[0], followerpic[0]) }
            Log.d("CHECKuser", users.toString())

            data class ViewHolder(val nameTextView: TextView, val UserImgView: ImageView, val locTextView: TextView, val repositoryTextView: TextView, val followTextView: TextView, val follwerTextView: TextView, val placepicView:ImageView, val repopicView:ImageView, val followpicView:ImageView, val followerpicView:ImageView)

            class UserAdapter(context: Context, users: List<UsersData>) : ArrayAdapter<UsersData>(context, 0, users) {
                private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                    var view = convertView
                    var holder: ViewHolder

                    if (view == null) {
                        view = layoutInflater.inflate(R.layout.list_item, parent, false)
                        holder = ViewHolder(
                                view.findViewById(R.id.nameTextView),
                                view.findViewById(R.id.userImgView),
                                view.findViewById(R.id.locTextView),
                                view.findViewById(R.id.repositoryTextView),
                                view.findViewById(R.id.followTextView),
                                view.findViewById(R.id.followerTextView),
                                view.findViewById(R.id.placepicView),
                                view.findViewById(R.id.repopicView),
                                view.findViewById(R.id.followpicView),
                                view.findViewById(R.id.followerpicView)
                        )
                        view.tag = holder
                    } else {
                        holder = view.tag as ViewHolder
                    }

                    val usersp = getItem(position) as UsersData
                    holder.nameTextView.text = usersp.name
                    Picasso.with(context).load(usersp.userImgView).into(holder.UserImgView)
                    holder.locTextView.text = usersp.loc
                    holder.repositoryTextView.text = usersp.repository
                    holder.followTextView.text = usersp.follow
                    holder.follwerTextView.text = usersp.follower
                    holder.placepicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, placepic[0]))
                    holder.repopicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, repopic[0]))
                    holder.followpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, followpic[0]))
                    holder.followerpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, followerpic[0]))

                    return view

                }
            }

            val array2Adapter = UserAdapter(applicationContext, users)
            val listView: ListView = findViewById(R.id.listView)
            listView.adapter = array2Adapter


            listView.setOnItemClickListener { parent, view, position, id ->
                val rep = result[2][position]
                Log.d("reprlt", rep)
                HitRep().execute(rep)

            }

        }
    }

    inner class HitRep : AsyncTask<String, String, Array<Array<String?>>?>() {
        override fun doInBackground(vararg params: String): Array<Array<String?>>? {
            var connection: HttpURLConnection? = null
            //文字、配列、行をバッファリングすることによって、文字型入力ストリームからテキストを効率良く読み込む
            var reader: BufferedReader? = null
            //Stringクラス同様に宣言した変数に、文字列を格納するために使用
            val buffer: StringBuffer
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
                Log.d("CHECKREPO", buffer.toString())

                var arrayrepo = Array(2, { arrayOfNulls<String>(4) })

                val RepoJsonarr = JSONArray(buffer.toString())
                val reponamearr = arrayListOf<String>()
                val repourlarr = arrayListOf<String>()
                for(i in 0..(RepoJsonarr.length() - 1)){

                    val useritems = RepoJsonarr.getJSONObject(i)
                    val reponame = useritems.getString("name")
                    val repourl= useritems.getString("svn_url")
                    reponamearr.add(reponame)
                    repourlarr.add(repourl)
                }
                arrayrepo[0] = Array(reponamearr.size) { i -> reponamearr[i] }
                arrayrepo[1] = Array(repourlarr.size) { i -> repourlarr[i] }

                return arrayrepo

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
            return null

        }

        override fun onPostExecute(result: Array<Array<String?>>?) {
            super.onPostExecute(result)
            if (result == null) {
                return
            }
            Log.d("CHECKrepopo", result[0].toString())


            val intent = Intent(applicationContext, repolistview::class.java)
            intent.putExtra(EXTRA_MESSAGE, result[0])
            intent.putExtra("Repourl",result[1])
            startActivity(intent)

        }
    }


}





