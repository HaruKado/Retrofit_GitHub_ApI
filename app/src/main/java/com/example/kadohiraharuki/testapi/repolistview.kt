package com.example.kadohiraharuki.testapi

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class repolistview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repolistview)

        val intent = getIntent()
        val message = intent.extras.getStringArray(EXTRA_MESSAGE)

        data class ReposData(val repname: String?)

        Log.d("mesrep", message.toString())

        val repos = List(message.size) { i -> ReposData(message[i]) }

        data class ViewHolder(val RepoTextView: TextView)

        class ReposAdapter(context: Context, repos: List<ReposData>) : ArrayAdapter<ReposData>(context, 0, repos) {
            private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
                var view = convertView
                var holder: ViewHolder
                if (view == null) {
                    view = layoutInflater.inflate(R.layout.repos_item, parent, false)
                    holder = ViewHolder(
                            view.findViewById(R.id.RepoTextView)
                    )
                    view.tag = holder
                } else {
                    holder = view.tag as ViewHolder
                }

                val reposp = getItem(position) as ReposData
                holder.RepoTextView.text = reposp.repname

                return view
            }
        }
        val array3Adapter = ReposAdapter(applicationContext, repos)
        val listView3: ListView = findViewById(R.id.repoList)
        listView3.adapter = array3Adapter
    }
}
