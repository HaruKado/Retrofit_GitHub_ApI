package com.example.kadohiraharuki.testapi

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_repolistview.*

class repolistview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repolistview)

        val intent = getIntent()
        val owner = intent.extras.getString("RepoOwner")
        val repository_name = intent.extras.getStringArrayList("RepoName")
        val repository_url = intent.extras.getStringArrayList("RepoUrl")
        val repository_star = intent.extras.getStringArrayList("RepoStar")
        val repository_fork = intent.extras.getStringArrayList("RepoFork")
        val repository_lang = intent.extras.getStringArrayList("RepoLang")


        val repos = List(repository_name.size) { i -> ReposData(
                repository_name[i],
                repository_star[i],
                repository_fork[i],
                repository_lang[i]
            )
        }

        class ReposAdapter(context: Context, repos: List<ReposData>) : ArrayAdapter<ReposData>(context, 0, repos) {
            private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                var view = convertView
                var holder: RepositoryInfo_ViewHolder
                if (view == null) {
                    view = layoutInflater.inflate(R.layout.repos_item, parent, false)
                    holder = RepositoryInfo_ViewHolder(
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
                    holder = view.tag as RepositoryInfo_ViewHolder
                }

                val reposp = getItem(position) as ReposData
                holder.RepoTextView.text = reposp.repname
                holder.starTextView.text = reposp.star
                holder.forkTextView.text = reposp.fork
                holder.langTextView.text = reposp.lang
                holder.starpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.star))
                holder.forkpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.fork))
                holder.langpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.language))

                return view
            }
        }


        repoList.adapter = ReposAdapter(this, repos)
        ownername.text = owner.toString() + "'s repository"

        repoList.setOnItemClickListener { parent, view, position, id ->
            val githubRepository_url = repository_url[position]
            val intent = Intent(applicationContext, webview::class.java)
            intent.putExtra("WebRepository_url", githubRepository_url)
            startActivity(intent)
        }
    }
}
