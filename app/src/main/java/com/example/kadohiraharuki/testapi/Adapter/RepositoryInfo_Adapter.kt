package com.example.kadohiraharuki.testapi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.kadohiraharuki.testapi.Data_File.ReposData
import com.example.kadohiraharuki.testapi.R
import com.example.kadohiraharuki.testapi.ViewHolder.RepositoryInfo_ViewHolder


class ReposAdapter(context: Context, repos: ArrayList<ReposData>) : ArrayAdapter<ReposData>(context, 0, repos) {
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
                    view.findViewById(R.id.langTextView)
            )
            view.tag = holder
        } else {
            holder = view.tag as RepositoryInfo_ViewHolder
        }
        //TI repository_positionはpositionでないため、誤解を招く変数
        val repository_position = getItem(position) as ReposData
        holder.RepoTextView.text = repository_position.repositoryName
        holder.starTextView.text = repository_position.star
        holder.forkTextView.text = repository_position.fork
        holder.langTextView.text = repository_position.lang

        return view
    }
}



