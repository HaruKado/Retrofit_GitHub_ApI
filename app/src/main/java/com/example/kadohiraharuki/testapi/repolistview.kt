package com.example.kadohiraharuki.testapi

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_repolistview.*

class repolistview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repolistview)

        val intent = getIntent()
        val reponame = intent.extras.getStringArray("RepoName")
        val repourl = intent.extras.getStringArray("RepoUrl")
        val stararr = intent.extras.getStringArray("RepoStar")
        val forkarr = intent.extras.getStringArray("RepoFork")
        val langarr = intent.extras.getStringArray("RepoLang")
        val owner = intent.extras.getString("RepoOwner")

        Log.d("lang", stararr[0].toString())
        Log.d("ccccckk", owner.toString())

        data class ReposData(val repname: String?, val star:String, val fork: String, val lang: String, val starpic: Int, val forkpic: Int, val langpic: Int)
        val starpic= listOf(R.drawable.star)
        val forkpic = listOf(R.drawable.fork)
        val langpic = listOf(R.drawable.language)

        val repos = List(reponame.size) { i -> ReposData(reponame[i], stararr[i], forkarr[i], langarr[i], starpic[0], forkpic[0], langpic[0]) }

        data class ViewHolder(val RepoTextView: TextView, val starTextView: TextView, val forkTextView: TextView, val langTextView: TextView, val starpicView: ImageView, val forkpicView: ImageView, val langpicView: ImageView)

        class ReposAdapter(context: Context, repos: List<ReposData>) : ArrayAdapter<ReposData>(context, 0, repos) {
            private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                var view = convertView
                var holder: ViewHolder
                if (view == null) {
                    view = layoutInflater.inflate(R.layout.repos_item, parent, false)
                    holder = ViewHolder(
                            view.findViewById(R.id.RepoTextView),
                            view.findViewById(R.id.starTextView),
                            view.findViewById(R.id.forkTextView),
                            view.findViewById(R.id.langTextView),
                            view.findViewById(R.id.starpicView),
                            view.findViewById(R.id.forkpicView),
                            view.findViewById(R.id.langpicView)
                    )
                    view.tag = holder
                } else {
                    holder = view.tag as ViewHolder
                }

                val reposp = getItem(position) as ReposData
                holder.RepoTextView.text = reposp.repname
                holder.starTextView.text = reposp.star
                holder.forkTextView.text = reposp.fork
                holder.langTextView.text = reposp.lang
                holder.starpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, starpic[0]))
                holder.forkpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, forkpic[0]))
                holder.langpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, langpic[0]))

                return view
            }
        }

        val array3Adapter = ReposAdapter(applicationContext, repos)
        val listViewRepo: ListView = findViewById(R.id.repoList)
        listViewRepo.adapter = array3Adapter
        val messageView: TextView = findViewById(R.id.ownername)
        messageView.text = owner.toString() + "'s repository"

        listViewRepo.setOnItemClickListener { parent, view, position, id ->
            val repurl = repourl[position]
            val intent = Intent(applicationContext, webview::class.java)
            intent.putExtra("webrepurl", repurl)
            startActivity(intent)
        }
    }
}
