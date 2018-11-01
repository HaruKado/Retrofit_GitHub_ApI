package com.example.kadohiraharuki.testapi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.kadohiraharuki.testapi.Data_File.Repository
import com.example.kadohiraharuki.testapi.R
import com.example.kadohiraharuki.testapi.ViewHolder.RepositoryViewHolder


class RepositoryAdapter(context: Context, repos: ArrayList<Repository>) : ArrayAdapter<Repository>(context, 0, repos) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder: RepositoryViewHolder
        if (view == null) {
            view = layoutInflater.inflate(R.layout.repos_item, parent, false)
            holder = RepositoryViewHolder(
                    view.findViewById(R.id.RepoTextView),
                    view.findViewById(R.id.starTextView),
                    view.findViewById(R.id.forkTextView),
                    view.findViewById(R.id.langTextView)
            )
            view.tag = holder
        } else {
            holder = view.tag as RepositoryViewHolder
        }
        //TI repository_positionはpositionでないため、誤解を招く変数
        val repository = getItem(position) as Repository
        holder.repositoryTextView.text = repository.name
        holder.starTextView.text = repository.stargazers_count
        holder.forkTextView.text = repository.forks_count
        holder.languageTextView.text = repository.language

        return view
    }
}



