package com.example.kadohiraharuki.testapi

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_repolistview.*



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

        val repository_position = getItem(position) as ReposData
        holder.RepoTextView.text = repository_position.repositoryName
        holder.starTextView.text = repository_position.star
        holder.forkTextView.text = repository_position.fork
        holder.langTextView.text = repository_position.lang
        holder.starpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.star))
        holder.forkpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.fork))
        holder.langpicView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.language))

        return view
    }
}


       /* repoList.adapter = ReposAdapter(this, repos)
        ownername.text = owner.toString() + "'s repository"

        repoList.setOnItemClickListener { parent, view, position, id ->
            val githubRepository_url = repository_url[position]
            val intent = Intent(applicationContext, webview::class.java)
            intent.putExtra("WebRepository_url", githubRepository_url)
            startActivity(intent)
        }
    }
}
*/
